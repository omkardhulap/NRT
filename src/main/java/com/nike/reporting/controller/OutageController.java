package com.nike.reporting.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.model.converter.OutageConverter;
import com.nike.reporting.model.dto.OutageDTO;
import com.nike.reporting.service.OutageService;
import com.nike.reporting.ui.PlannedOutageUI;
import com.nike.reporting.ui.UnplannedOutageUI;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.FileDownloadUtil;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.OutageUtil;
import com.nike.reporting.util.ReportingConstants;

@Controller
public class OutageController {

	private static final Logger logger = LoggerFactory.getLogger(OutageController.class);

	private OutageService outageService;

	@Autowired(required = true)
	@Qualifier(value = "outageService")
	public void setOutageService(OutageService os) {
		this.outageService = os;
	}

	/*
	 * @RequestMapping(value = "/home", method = RequestMethod.GET) public
	 * String returnHome(Model model) {
	 * 
	 * return "home"; }
	 */

	@RequestMapping(value = "/plannedOutage", method = RequestMethod.GET)
	public String plannedOutage(Model model) {
		logger.info("Entering method plannedOutage");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		PlannedOutageUI plannedOutageUI = new PlannedOutageUI();
		plannedOutageUI.setApplications(new ArrayList<String>(CommonsUtil.applicationsList.size()));
		model.addAttribute("plannedOutageUI", plannedOutageUI);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("reportLookupList", CommonsUtil.reportLookupList);

		logger.info("Exiting method plannedOutage");
		return "plannedOutage";
	}

	@RequestMapping(value = "/unplannedOutage", method = RequestMethod.GET)
	public String unplannedOutage(Model model) {
		logger.info("Entering method unplannedOutage");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		UnplannedOutageUI unplannedOutageUI = new UnplannedOutageUI();
		unplannedOutageUI.setApplications(new ArrayList<String>(CommonsUtil.applicationsList.size()));
		model.addAttribute("unplannedOutageUI", unplannedOutageUI);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("pointOfFailureList", CommonsUtil.pointOfFailureList);
		model.addAttribute("severityList", CommonsUtil.severityList);

		logger.info("Exiting method unplannedOutage");
		return "unplannedOutage";
	}

	@RequestMapping(value = "/searchOutage", method = RequestMethod.GET)
	public String searchOutage(Model model) {
		logger.info("Entering method searchOutage");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		SearchOutage so = new SearchOutage();
		so.setApplications(new ArrayList<String>(CommonsUtil.applicationsList.size()));
		so.setTypeOfOutage(new char[] { ReportingConstants.PLANNED_OUTAGE_TYPE_CHAR, ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR });
		model.addAttribute("searchOutage", so);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("capabilityList", CommonsUtil.capabilityList);

		logger.info("Exiting method searchOutage");
		return "searchOutage";
	}

	@RequestMapping(value = "/plannedOutage/add", method = RequestMethod.POST)
	public String addPlannedOutage(@ModelAttribute("plannedOutageUI") PlannedOutageUI plannedOutageUI, BindingResult result, Model model) {
		logger.info("Entering method addPlannedOutage");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Outage o = null;
		try {
			if (plannedOutageUI.getId() != null && plannedOutageUI.getId() != 0) {
				o = this.outageService.getOutageById(plannedOutageUI.getId());
			}
			o = OutageUtil.populatePlannedOutage(plannedOutageUI, o);

			if (o.getId() == null || o.getId() == 0) {
				this.outageService.addOutage(o);
				model.addAttribute("successMessage", String.format("Planned outage id %d added successfully", o.getId()));
			} else {
				this.outageService.updateOutage(o);
				model.addAttribute("applicationList", CommonsUtil.applicationsList);

				PlannedOutageUI pUI = OutageUtil.populatePlannedOutageForUI(o);
				model.addAttribute("plannedOutageUI", pUI);
				model.addAttribute("reportLookupList", CommonsUtil.reportLookupList);
				model.addAttribute("successMessage", String.format("Planned outage id %d updated successfully", o.getId()));
				return "plannedOutage";
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			if (o.getId() == null || o.getId() == 0) {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			} else {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			}
		}
		logger.info("Exiting method addPlannedOutage");
		return plannedOutage(model);
	}

	@RequestMapping(value = "/unplannedOutage/add", method = RequestMethod.POST)
	public String addUnplannedOutage(@ModelAttribute("unplannedOutageUI") UnplannedOutageUI unplannedOutageUI, BindingResult result, Model model) {
		logger.info("Entering method addUnplannedOutage");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Outage o = null;
		try {
			if (unplannedOutageUI.getId() != null && unplannedOutageUI.getId() != 0) {
				o = this.outageService.getOutageById(unplannedOutageUI.getId());
			}
			o = OutageUtil.populateUnplannedOutage(unplannedOutageUI, o);

			if (o.getId() == null || o.getId() == 0) {
				this.outageService.addOutage(o);
				model.addAttribute("successMessage", String.format("Unplanned outage id %d added successfully", o.getId()));
			} else {
				this.outageService.updateOutage(o);
				model.addAttribute("successMessage", String.format("Unplanned outage id %d updated successfully", o.getId()));

				UnplannedOutageUI upUI = OutageUtil.populateUnplannedOutageForUI(o);
				model.addAttribute("unplannedOutageUI", upUI);
				model.addAttribute("priorityList", CommonsUtil.priorityList);
				model.addAttribute("applicationList", CommonsUtil.applicationsList);
				model.addAttribute("severityList", CommonsUtil.severityList);
				model.addAttribute("pointOfFailureList", CommonsUtil.pointOfFailureList);
				return "unplannedOutage";
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			if (o.getId() == null || o.getId() == 0) {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			} else {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			}
		}
		logger.info("Exiting method addUnplannedOutage");
		return unplannedOutage(model);
	}

	@RequestMapping(value = "/searchOutage/search", method = RequestMethod.GET)
	public String searchOutageSubmit(@ModelAttribute("searchOutage") SearchOutage so, Model model) {

		logger.info("Entering method searchOutageSubmit");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		List<Outage> outageList = null;
		try {
			outageList = outageService.getOutageBySearchCriteria(so);

			// outageList = outageService.getOutageBySearchCriteria(so);
			OutageConverter outageConverter = new OutageConverter();
			model.addAttribute("searchOutage", so);
			model.addAttribute("outageDTO", new OutageDTO());
			model.addAttribute("listOutages", outageConverter.convertFromObjectToDTOList(outageList));
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("capabilityList", CommonsUtil.capabilityList);

			logger.debug("OutageList size is {}.", outageList.size());

		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}

		logger.info("Exiting method searchOutageSubmit");
		return "searchOutage";
	}

	@RequestMapping("/removeOutage/{id}")
	public String removeOutage(@PathVariable("id") int id) {

		logger.info("Entering method removeOutage");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		this.outageService.removeOutage(id);

		logger.info("Exiting method removeOutage");
		return "redirect:/outages";

	}

	@RequestMapping("/editOutage/{id}")
	public String editOutage(@PathVariable("id") int id, Model model) {

		logger.info("Entering method editOutage");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Outage o = this.outageService.getOutageById(id);

		model.addAttribute("applicationList", CommonsUtil.applicationsList);

		if (o.getOutageType() == ReportingConstants.PLANNED_OUTAGE_TYPE_CHAR) {
			PlannedOutageUI pUI = OutageUtil.populatePlannedOutageForUI(o);
			model.addAttribute("plannedOutageUI", pUI);
			model.addAttribute("reportLookupList", CommonsUtil.reportLookupList);
			return "plannedOutage";
		}
		UnplannedOutageUI upUI = OutageUtil.populateUnplannedOutageForUI(o);
		model.addAttribute("unplannedOutageUI", upUI);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("pointOfFailureList", CommonsUtil.pointOfFailureList);
		model.addAttribute("severityList", CommonsUtil.severityList);

		logger.info("Exiting method editOutage");
		return "unplannedOutage";

	}

	@RequestMapping("/createEOSPptx/{id}")
	public void createEOSPptx(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id, Model model) {

		logger.info("Entering method createEOSPptx");
		// Create Session
		LoginUtil.setValidSession();

		String fileName = "";
		try {
			Outage o = this.outageService.getOutageById(id);
			OutageConverter outageConverter = new OutageConverter();
			OutageDTO outageDTO = outageConverter.convertFromObjectToDTO(o);
			fileName = this.outageService.createEOSPptx(request, response, outageDTO);
			FileDownloadUtil.doDownload(request, response, fileName, false);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg() + ": " + fileName));
		}

		logger.info("Exiting method createEOSPptx");
		// return "home";

	}

	@RequestMapping("/updateAAR/{id}")
	public String updateAAR(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id, Model model) {

		logger.info("Entering method updateAAR");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		// TODO: if Outage has AAR id , fetch its details from AAR table and
		// populate AAR page else open blank form

		logger.info("Exiting method updateAAR");

		return "updateAAR";
	}

}
