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
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.SearchEffort;
import com.nike.reporting.model.converter.EffortConverter;
import com.nike.reporting.model.dto.ExcelEffortDTO;
import com.nike.reporting.service.EffortService;
import com.nike.reporting.ui.EffortManagementUI;
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
public class EffortController {

	private static final Logger logger = LoggerFactory.getLogger(EffortController.class);

	private EffortService effortService;

	@Autowired(required = true)
	@Qualifier(value = "effortService")
	public void setEffortService(EffortService effortService) {
		this.effortService = effortService;
	}

	@RequestMapping(value = "/manageEffort", method = RequestMethod.GET)
	public String manageEffort(Model model) {
		logger.info("Entering method manageEffort");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		// Effort effort = new Effort();
		EffortManagementUI effortUI = new EffortManagementUI();
		model.addAttribute("effort", effortUI);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("complexityList", CommonsUtil.complexityList);
		model.addAttribute("categoryList", CommonsUtil.categoryList);

		logger.info("Exiting method manageEffort");
		return "manageEffort";
	}

	@RequestMapping(value = "/manageEffort/add", method = RequestMethod.POST)
	public String addEffort(@ModelAttribute("effort") Effort effort, BindingResult result, Model model) {
		logger.info("Entering method addEffort");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		String user_id = LoginUtil.getLoggedInUser();

		boolean isUpdateRequest = false;
		int tempEffortId = 0;
		try {
			if (effort.getId() != null && effort.getId() != 0) {
				this.effortService.getEffortById(effort.getId());
				tempEffortId = effort.getId();
				isUpdateRequest = true;
			}

			// fetch Effort from db if already present for getting audit info

			Effort effortForAuditInfo = null;
			if (tempEffortId != 0) {
				effortForAuditInfo = this.effortService.getEffortById(tempEffortId);
			}

			/*
			 * Add audit info for the Effort.
			 */
			AuditInfo auditInfo = null;
			auditInfo = new AuditInfo();
			if (effortForAuditInfo == null) {
				auditInfo.setCreatedBy(user_id);
				auditInfo.setUpdatedBy(user_id);
				auditInfo.setCreatedDate(new Date());
				auditInfo.setUpdatedDate(new Date());
			} else {
				auditInfo = effortForAuditInfo.getAuditInfo();
				if (auditInfo == null) {
					auditInfo = new AuditInfo();
				}
			}
			auditInfo.setUpdatedBy(user_id);
			auditInfo.setUpdatedDate(new Date());
			effort.setAuditInfo(auditInfo);

			if (!isUpdateRequest) {
				this.effortService.addEffort(effort);
				model.addAttribute("successMessage", String.format("Effort with id %d added successfully", effort.getId()));
			} else {
				this.effortService.updateEffort(effort);
				model.addAttribute("successMessage", String.format("Effort with id %d updated successfully", effort.getId()));
				EffortConverter effortConverter = new EffortConverter();
				model.addAttribute("effort", effortConverter.populateEffortForUI(effort));
				// model.addAttribute("effort", effort);
				model.addAttribute("applicationList", CommonsUtil.applicationsList);
				model.addAttribute("priorityList", CommonsUtil.priorityList);
				model.addAttribute("complexityList", CommonsUtil.complexityList);
				model.addAttribute("categoryList", CommonsUtil.categoryList);
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getMessage()));
		}
		logger.info("Exiting method addEffort");

		return isUpdateRequest ? "manageEffort" : manageEffort(model);
	}

	@RequestMapping("/editEffort/{id}")
	public String editEffort(@PathVariable("id") int id, Model model) {

		logger.info("Entering method editEffort");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Effort effort = this.effortService.getEffortById(id);

		if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) && !(LoginUtil.getLoggedInUser().equalsIgnoreCase(effort.getAuditInfo().getCreatedBy()))) {
			model.addAttribute("errorMessage", "Not Authorized to view this effort entry!");
			SearchEffort searchEffort = new SearchEffort();
			model.addAttribute("searchEffort", searchEffort);
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("priorityList", CommonsUtil.priorityList);
			model.addAttribute("complexityList", CommonsUtil.complexityList);
			model.addAttribute("categoryList", CommonsUtil.categoryList);
			return "searchEffort";
		}

		EffortConverter effortConverter = new EffortConverter();
		model.addAttribute("effort", effortConverter.populateEffortForUI(effort));
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("complexityList", CommonsUtil.complexityList);
		model.addAttribute("categoryList", CommonsUtil.categoryList);

		logger.info("Exiting method editEffort");
		return "manageEffort";
	}

	@RequestMapping(value = "/deleteEffort/{id}", method = RequestMethod.GET)
	public String deleteEffort(@PathVariable("id") int id, Model model) {

		logger.info("Entering method deleteEffort");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Effort effort = this.effortService.getEffortById(id);

		if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) && !(LoginUtil.getLoggedInUser().equalsIgnoreCase(effort.getAuditInfo().getCreatedBy()))) {
			model.addAttribute("errorMessage", "Not Authorized to delete this effort entry!");
			SearchEffort searchEffort = new SearchEffort();
			model.addAttribute("searchEffort", searchEffort);
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("priorityList", CommonsUtil.priorityList);
			model.addAttribute("complexityList", CommonsUtil.complexityList);
			model.addAttribute("categoryList", CommonsUtil.categoryList);
			return "searchEffort";
		}

		this.effortService.deleteEffort(effort);
		SearchEffort searchEffort = new SearchEffort();
		model.addAttribute("searchEffort", searchEffort);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("complexityList", CommonsUtil.complexityList);
		model.addAttribute("categoryList", CommonsUtil.categoryList);
		model.addAttribute("successMessage", String.format("Effort with id %d deleted successfully", effort.getId()));
		logger.info("Exiting method deleteEffort");
		return "searchEffort";
	}

	@RequestMapping(value = "/searchEffort", method = RequestMethod.GET)
	public String searchEffort(Model model) {
		logger.info("Entering method searchEffort");
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

		SearchEffort searchEffort = new SearchEffort();
		searchEffort.setFromDate(strFromDate);
		searchEffort.setToDate(strToDate);
		searchEffort.setOwner(LoginUtil.getLoggedInUser());

		model.addAttribute("searchEffort", searchEffort);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("complexityList", CommonsUtil.complexityList);
		model.addAttribute("categoryList", CommonsUtil.categoryList);

		logger.info("Exiting method searchEffort");
		return "searchEffort";
	}

	@RequestMapping(value = "/searchEffort/search", method = RequestMethod.GET)
	public String searchEffortSubmit(@ModelAttribute("searchEffort") SearchEffort searchEffort, Model model) {

		logger.info("Entering method searchEffortSubmit");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) && !(LoginUtil.getLoggedInUser().equalsIgnoreCase(searchEffort.getOwner()))) {
			model.addAttribute("errorMessage", "Not Authorized to view this effort entry!");
			model.addAttribute("searchEffort", searchEffort);
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("priorityList", CommonsUtil.priorityList);
			model.addAttribute("complexityList", CommonsUtil.complexityList);
			model.addAttribute("categoryList", CommonsUtil.categoryList);
			return "searchEffort";
		}

		List<Effort> effortList = null;
		try {
			effortList = effortService.getEffortBySearchCriteria(searchEffort);

			EffortConverter effortConverter = new EffortConverter();
			model.addAttribute("searchEffort", searchEffort);
			model.addAttribute("effortList", effortConverter.convertFromObjectToDTOList(effortList));
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("priorityList", CommonsUtil.priorityList);
			model.addAttribute("complexityList", CommonsUtil.complexityList);
			model.addAttribute("categoryList", CommonsUtil.categoryList);

			logger.debug("Effort List size is {}.", effortList.size());

		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}

		logger.info("Exiting method searchEffortSubmit");
		return "searchEffort";
	}

	@RequestMapping(value = "/uploadEffort", method = RequestMethod.GET)
	public String uploadEffort(Model model) {
		logger.info("Entering method uploadEffort");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		logger.info("Exiting method uploadEffort");
		return "uploadEffort";
	}

	@RequestMapping(value = "/manageEffort/uploadEffort", method = RequestMethod.POST)
	public String uploadEfforts(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("uploadedFile") MultipartFile file, Model model) {
		logger.info("Entering method uploadEfforts");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		String user_id = LoginUtil.getLoggedInUser();

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			if (file == null || file.getOriginalFilename().trim().length() == 0) {
				throw new NikeException("EFFORT_FILE_NOT_FOUND_EXCEPTION", ErrorMessages.EFFORT_FILE_NOT_FOUND_EXCEPTION);
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

			List<ExcelEffortDTO> excelEffortDTOList = effortService.getEffortListFromExcel(newFile, user_id);

			EffortConverter effortConverter = new EffortConverter();
			List<Effort> effortList = new ArrayList<Effort>();

			effortList = effortConverter.convertFromExcelDTOToObjectList(excelEffortDTOList);
			effortService.addEfforts(effortList);
			model.addAttribute("successMessage", "Efforts uploaded successfully.");
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
		logger.info("Exiting method uploadEfforts");
		return uploadEffort(model);
		// return "redirect:/searchEffort";
	}

	@RequestMapping("/downloadEffortTemplate")
	public void downloadEffortTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Entering method downloadEffortTemplate");
		// Create Session
		LoginUtil.setValidSession();
		try {
			FileDownloadUtil.doDownload(request, response, ReportingConstants.EFFORT_TRACKER_TEMPLATE_NAME, true);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + ReportingConstants.EFFORT_TRACKER_TEMPLATE_NAME));
		}
		logger.info("Exiting method downloadEffortTemplate");
		// return "home";
	}

	@RequestMapping(value = "/effortReports", method = RequestMethod.GET)
	public String effortReports(Model model) {
		logger.info("Entering method effortReports");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		// EffortReports effortReports = new EffortReports();
		// model.addAttribute("effortReports", effortReports);

		logger.info("Exiting method effortReports");
		return "effortReports";
	}

	@RequestMapping("/downloadWeeklyEffortReport")
	public void downloadWeeklyEffortReport(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Entering method downloadWeeklyEffortReport");
		// Create Session
		LoginUtil.setValidSession();
		String userId = LoginUtil.getLoggedInUser();
		String fileName = userId + "_" + ReportingConstants.WEEKLY_EFFORT_REPORT_NAME;

		try {
			boolean fileCreated = getDataForEffortReport(ReportingConstants.WEEKLY_EFFORT_REPORT_QUERY, request, response, ReportingConstants.WEEKLY_EFFORT_REPORT_NAME);
			if (fileCreated) {
				FileDownloadUtil.doDownload(request, response, fileName, false);
			} else {
				logger.error("Problem in creating " + fileName);
				model.addAttribute("errorMessage", "Problem in creating " + fileName + ". Try again or contact System Administrator.");
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + fileName));
		}
		logger.info("Exiting method downloadWeeklyEffortReport");
		// return "home";
	}

	@RequestMapping("/downloadWeeklyApplicationWiseEffort")
	public void downloadWeeklyApplicationWiseEffort(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Entering method downloadWeeklyApplicationWiseEffort");
		// Create Session
		LoginUtil.setValidSession();
		String userId = LoginUtil.getLoggedInUser();
		String fileName = userId + "_" + ReportingConstants.WEEKLY_APPLICATION_WISE_EFFORT_REPORT_NAME;

		try {
			boolean fileCreated = getDataForEffortReport(ReportingConstants.WEEKLY_EFFORT_REPORT_QUERY, request, response, ReportingConstants.WEEKLY_APPLICATION_WISE_EFFORT_REPORT_NAME);
			if (fileCreated) {
				FileDownloadUtil.doDownload(request, response, fileName, false);
			} else {
				logger.error("Problem in creating " + fileName);
				model.addAttribute("errorMessage", "Problem in creating " + fileName + ". Try again or contact System Administrator.");
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + fileName));
		}
		logger.info("Exiting method downloadWeeklyApplicationWiseEffort");
		// return "home";
	}

	@RequestMapping("/downloadWeeklyCapabilityWiseEffort")
	public void downloadWeeklyCapabilityWiseEffort(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Entering method downloadWeeklyCapabilityWiseEffort");
		// Create Session
		LoginUtil.setValidSession();
		String userId = LoginUtil.getLoggedInUser();
		String fileName = userId + "_" + ReportingConstants.WEEKLY_CAPABILITY_WISE_EFFORT_REPORT_NAME;

		try {
			boolean fileCreated = getDataForEffortReport(ReportingConstants.WEEKLY_EFFORT_REPORT_QUERY, request, response, ReportingConstants.WEEKLY_CAPABILITY_WISE_EFFORT_REPORT_NAME);
			if (fileCreated) {
				FileDownloadUtil.doDownload(request, response, fileName, false);
			} else {
				logger.error("Problem in creating " + fileName);
				model.addAttribute("errorMessage", "Problem in creating " + fileName + ". Try again or contact System Administrator.");
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + fileName));
		}
		logger.info("Exiting method downloadWeeklyCapabilityWiseEffort");
		// return "home";
	}

	private boolean getDataForEffortReport(String sqlQuery, HttpServletRequest request, HttpServletResponse response, String reportName) {
		return effortService.createExcelReportFromList(sqlQuery, request, response, reportName);
	}
}
