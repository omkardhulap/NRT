package com.nike.reporting.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.MTTRAnalysis;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.converter.MTTRConverter;
import com.nike.reporting.model.dto.ExcelMTTRDTO;
import com.nike.reporting.service.MTTRService;
import com.nike.reporting.service.UserService;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.FileDownloadUtil;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */

@Controller
public class MTTRController {

	private static final Logger logger = LoggerFactory.getLogger(MTTRController.class);

	private MTTRService mttrService;
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "mttrService")
	public void setEffortService(MTTRService mttrService) {
		this.mttrService = mttrService;
	}

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}

	@RequestMapping(value = "/uploadMTTR", method = RequestMethod.GET)
	public String uploadMTTR(Model model) {
		logger.info("Entering method uploadMTTR");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		logger.info("Exiting method uploadMTTR");
		return "uploadMTTR";
	}

	@RequestMapping(value = "/mttr/uploadMTTR", method = RequestMethod.POST)
	public String uploadMTTR(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("uploadedFile") MultipartFile file, Model model) {
		logger.info("Entering method uploadMTTR");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		String user_id = LoginUtil.getLoggedInUser();

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			if (file == null || file.getOriginalFilename().trim().length() == 0) {
				throw new NikeException("MTTR_FILE_NOT_FOUND_EXCEPTION", ErrorMessages.MTTR_FILE_NOT_FOUND_EXCEPTION);
			}
			String fileName = file.getOriginalFilename();
			// get absolute path of the application
			ServletContext context = request.getSession().getServletContext();

			String appPath = context.getRealPath("");
			System.out.println("appPath = " + appPath);

			inputStream = file.getInputStream();

			File newFile = new File(appPath + "\\resources\\downloads\\" + user_id + "_" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			List<ExcelMTTRDTO> excelMTTRDTOList = mttrService.getMTTRDataFromExcel(newFile, user_id);

			MTTRConverter mttrConverter = new MTTRConverter();
			List<MTTRData> mttrDataList = new ArrayList<MTTRData>();
			mttrDataList = mttrConverter.convertFromExcelDTOToObjectList(excelMTTRDTOList);

			mttrService.addMTTRs(mttrDataList);
			model.addAttribute("successMessage", "Data uploaded successfully.");
		} catch (HibernateException he) {
			logger.error("ERROR: " + he.getMessage(), he);
			model.addAttribute("errorMessage", String.format(he.getMessage()));
		} catch (DateParsingException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage()));
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage()));
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}
		logger.info("Exiting method uploadMTTR");
		return uploadMTTR(model);
		// return "redirect:/searchEffort";
	}

	@RequestMapping("/downloadMTTRTemplate")
	public void downloadMTTRTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Entering method downloadMTTRTemplate");
		// Create Session
		LoginUtil.setValidSession();
		try {
			FileDownloadUtil.doDownload(request, response, ReportingConstants.MTTR_RAW_SAMPLE_TEMPLATE_IMAGE, true);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + ReportingConstants.EFFORT_TRACKER_TEMPLATE_NAME));
		}
		logger.info("Exiting method downloadMTTRTemplate");
		// return "home";
	}

	@RequestMapping(value = "/searchMTTR/searchExport", method = RequestMethod.GET)
	public void exportMTTRAnalysis(@ModelAttribute("searchMTTR") SearchMTTR searchMTTR, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method exportMTTRAnalysis");

		// Create Session
		LoginUtil.setValidSession();
		String userId = LoginUtil.getLoggedInUser();
		String fileName = userId + "_" + ReportingConstants.MTTR_ANALYSIS_REPORT;
		List<MTTRAnalysis> mttrAnalysisList = new ArrayList<>();
		try {
			// System.out.println("****: " +
			// Collections.list(request.getParameterNames()).get(0));
			try {
				JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));
				searchMTTR.setFromDate((String) json.get("fromDate"));
				searchMTTR.setToDate((String) json.get("toDate"));
			} catch (Exception e) {
				searchMTTR.setFromDate(request.getParameter("fromDate"));
				searchMTTR.setToDate(request.getParameter("toDate"));
			}
			// TODO: Null check
			System.out.println(searchMTTR.getFromDate());
			System.out.println(searchMTTR.getToDate());

			List mttrList = mttrService.createMTTRAnalysisTable(ReportingConstants.MTTR_ANALYSIS_QUERY_BETWN_DATE, searchMTTR);
			MTTRConverter mttrConverter = new MTTRConverter();
			mttrAnalysisList = mttrConverter.convertToMTTRAnalysisObject(mttrList);

			boolean fileCreated = mttrService.exportMTTRAnalysisToExcel(mttrAnalysisList, request, response, ReportingConstants.MTTR_ANALYSIS_REPORT);
			if (fileCreated) {
				FileDownloadUtil.doDownload(request, response, fileName, false);
			} else {
				logger.error("Problem in creating " + fileName);
				model.addAttribute("errorMessage", "Problem in creating " + fileName + ". Try again or contact System Administrator.");
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage() + ": " + fileName));
		}
		logger.info("Exiting method exportMTTRAnalysis");
	}

	@RequestMapping(value = "/searchMTTR/search", method = RequestMethod.GET)
	public @ResponseBody
	List<MTTRAnalysis> searchMTTRSubmit(@ModelAttribute("searchMTTR") SearchMTTR searchMTTR, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method searchMTTRSubmit");
		// Create Session
		LoginUtil.setValidSession();
		List<MTTRAnalysis> mttrAnalysisList = new ArrayList<>();
		try {
			System.out.println("@@@@: " + Collections.list(request.getParameterNames()).get(0));
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			searchMTTR.setFromDate((String) json.get("fromDate"));
			searchMTTR.setToDate((String) json.get("toDate"));

			List mttrList = mttrService.createMTTRAnalysisTable(ReportingConstants.MTTR_ANALYSIS_QUERY_BETWN_DATE, searchMTTR);
			MTTRConverter mttrConverter = new MTTRConverter();
			mttrAnalysisList = mttrConverter.convertToMTTRAnalysisObject(mttrList);
			model.addAttribute("searchMTTR", searchMTTR);
			// model.addAttribute("mttrAnalysisList",
			// mttrConverter.convertToMTTRAnalysisObject(mttrList))
			logger.debug("MTTR List size is {}.", mttrList.size());
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage()));
		}
		logger.info("Exiting method searchMTTRSubmit");
		return mttrAnalysisList;
	}

	@RequestMapping(value = "/searchMTTR", method = RequestMethod.GET)
	public String searchMTTR(Model model) {
		logger.info("Entering method searchMTTR");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		DateFormat dateFormat = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		String strToDate = dateFormat.format(cal.getTime());
		// Subtract DASHBOAD_DATE_DIFF from current date
		// cal.add(Calendar.DATE, ReportingConstants.DASHBOAD_DATE_DIFF * -1);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 00, 00, 00);
		String strFromDate = dateFormat.format(cal.getTime());
		SearchMTTR searchMTTR = new SearchMTTR();
		searchMTTR.setFromDate(strFromDate);
		searchMTTR.setToDate(strToDate);
		model.addAttribute("searchMTTR", searchMTTR);

		logger.info("Exiting method searchMTTR");
		return "searchMTTR";
	}

	@RequestMapping(value = "/searchMTTRIncidents", method = RequestMethod.GET)
	public String searchMTTRIncidents(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method searchMTTRIncidents");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		String strFromDate = (String) request.getSession().getAttribute("mttrIncidentFromDate");
		String strToDate = (String) request.getSession().getAttribute("mttrIncidentToDate");
		DateFormat dateFormat = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		if (strToDate == null) {
			strToDate = dateFormat.format(cal.getTime());
		}
		// Subtract DASHBOAD_DATE_DIFF from current date
		// cal.add(Calendar.DATE, ReportingConstants.DASHBOAD_DATE_DIFF * -1);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 00, 00, 00);
		if (strFromDate == null) {
			strFromDate = dateFormat.format(cal.getTime());
		}
		SearchMTTR searchMTTR = new SearchMTTR();
		searchMTTR.setFromDate(strFromDate);
		searchMTTR.setToDate(strToDate);
		model.addAttribute("searchMTTR", searchMTTR);

		logger.info("Exiting method searchMTTRIncidents");
		return "searchMTTRIncidents";
	}

	@RequestMapping(value = "/searchMTTRIncidents/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ExcelMTTRDTO> searchMTTRIncidentsSubmit(@ModelAttribute("searchMTTRIncidents") SearchMTTR searchMTTR, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method searchMTTRIncidentsSubmit");
		// Create Session
		LoginUtil.setValidSession();
		List<ExcelMTTRDTO> excelMTTRDTOList = new ArrayList<>();
		try {
			// List mttrList =
			//
			// System.out.println("####: MTTR_ANALYSIS_QUERY_BETWN_DATE: " +
			// ReportingConstants.MTTR_ANALYSIS_QUERY_BETWN_DATE);
			// mttrService.createMTTRAnalysisTable(ReportingConstants.MTTR_ANALYSIS_QUERY_BETWN_DATE,
			// searchMTTR);
			// System.out.println("####: " +
			// Collections.list(request.getParameterNames()).get(0));

			// JSONObject json = (JSONObject) new JSONParser().parse((String)
			// Collections.list(request.getParameterNames()).get(0));
			// searchMTTR.setFromDate((String) json.get("fromDate"));
			// searchMTTR.setToDate((String) json.get("toDate"));
			try {
				JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));
				searchMTTR.setFromDate((String) json.get("fromDate"));
				searchMTTR.setToDate((String) json.get("toDate"));
			} catch (Exception e) {
				searchMTTR.setFromDate(request.getParameter("fromDate"));
				searchMTTR.setToDate(request.getParameter("toDate"));
			}
			searchMTTR.setIsMTTRBreached(Boolean.TRUE);

			MTTRConverter mttrConverter = new MTTRConverter();
			excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrService.getMTTRBySearchCriteria(searchMTTR));
			model.addAttribute("searchMTTR", searchMTTR);
			// model.addAttribute("excelMTTRDTOList", excelMTTRDTOList);
			request.getSession().setAttribute("mttrIncidentFromDate", searchMTTR.getFromDate());
			request.getSession().setAttribute("mttrIncidentToDate", searchMTTR.getToDate());

			logger.debug("MTTR List size is {}.", excelMTTRDTOList.size());
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage()));
		}
		logger.info("Exiting method searchMTTRIncidentsSubmit");
		return excelMTTRDTOList;
	}

	@RequestMapping(value = "/searchMTTRIncidents/searchExport", method = RequestMethod.GET)
	public void exportMTTRIncidents(@ModelAttribute("searchMTTRIncidents") SearchMTTR searchMTTR, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method exportMTTRIncidents");

		// Create Session
		LoginUtil.setValidSession();
		List<ExcelMTTRDTO> excelMTTRDTOList = new ArrayList<>();
		String userId = LoginUtil.getLoggedInUser();
		String fileName = userId + "_" + ReportingConstants.MTTR_INCIDENTS_REPORT;
		List<MTTRAnalysis> mttrAnalysisList = new ArrayList<>();
		try {
			// System.out.println("****: " +
			// Collections.list(request.getParameterNames()).get(0));
			try {
				JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));
				searchMTTR.setFromDate((String) json.get("fromDate"));
				searchMTTR.setToDate((String) json.get("toDate"));
			} catch (Exception e) {
				searchMTTR.setFromDate(request.getParameter("fromDate"));
				searchMTTR.setToDate(request.getParameter("toDate"));
			}
			// TODO: Null check
			System.out.println(searchMTTR.getFromDate());
			System.out.println(searchMTTR.getToDate());
			searchMTTR.setIsMTTRBreached(Boolean.TRUE);

			MTTRConverter mttrConverter = new MTTRConverter();
			excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrService.getMTTRBySearchCriteria(searchMTTR));

			boolean fileCreated = mttrService.exportMTTRIncidentsToExcel(excelMTTRDTOList, request, response, ReportingConstants.MTTR_INCIDENTS_REPORT);
			if (fileCreated) {
				FileDownloadUtil.doDownload(request, response, fileName, false);
			} else {
				logger.error("Problem in creating " + fileName);
				model.addAttribute("errorMessage", "Problem in creating " + fileName + ". Try again or contact System Administrator.");
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage() + ": " + fileName));
		}
		logger.info("Exiting method exportMTTRIncidents");
	}

	@RequestMapping(value = "/searchMTTRIncidents/update", method = RequestMethod.POST)
	public String editMTTRIncident(@ModelAttribute("excelMTTRDTO") ExcelMTTRDTO excelMTTRDTO, Model model) {
		logger.info("Entering method editMTTRIncident");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		MTTRData mttrData = this.mttrService.getMTTRById(excelMTTRDTO.getMttrId());
		mttrData.setMttrBreachReason(excelMTTRDTO.getMttrBreachReason());
		mttrData.setRemarks(excelMTTRDTO.getRemarks());
		MTTRConverter mttrConverter = new MTTRConverter();

		/*
		 * Add audit info for the MTTRData.
		 */
		String user_id = LoginUtil.getLoggedInUser();
		AuditInfo auditInfo = null;
		auditInfo = new AuditInfo();
		if (mttrData != null) {
			auditInfo = mttrData.getAuditInfo();
			if (auditInfo == null) {
				auditInfo = new AuditInfo();
			}
		}

		auditInfo.setUpdatedBy(user_id);
		auditInfo.setUpdatedDate(new Date());
		mttrData.setAuditInfo(auditInfo);
		this.mttrService.updateMTTR(mttrData);
		model.addAttribute("excelMTTRDTO", mttrConverter.populateMTTRForUI(mttrData));
		model.addAttribute("mttrBreachLookupList", CommonsUtil.mttrBreachLookupList);
		model.addAttribute("successMessage", String.format("MTTR with id %d updated successfully", mttrData.getId()));

		logger.info("Exiting method editMTTRIncident");
		return "updateMTTRIncident";
	}

	@RequestMapping("/searchMTTRIncident/{id}")
	public String manageMTTR(@PathVariable("id") int id, @ModelAttribute("searchMTTRIncidents") SearchMTTR searchMTTR, Model model, HttpServletRequest request, HttpServletResponse response) {
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		// if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) &&
		// !(LoginUtil.getLoggedInUser().equalsIgnoreCase(mttrData.getAssignedTo())))
		// {
		// if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN))) {
		// searchMTTR(model);
		// }
		// TODO: Role check over here for leads

		MTTRData mttrData = this.mttrService.getMTTRById(id);
		MTTRConverter mttrConverter = new MTTRConverter();

		searchMTTR.setFromDate((String) request.getSession().getAttribute("mttrIncidentFromDate"));
		searchMTTR.setToDate((String) request.getSession().getAttribute("mttrIncidentToDate"));

		model.addAttribute("excelMTTRDTO", mttrConverter.populateMTTRForUI(mttrData));
		model.addAttribute("mttrBreachLookupList", CommonsUtil.mttrBreachLookupList);
		model.addAttribute("searchMTTR", searchMTTR);

		return "updateMTTRIncident";
	}

}
