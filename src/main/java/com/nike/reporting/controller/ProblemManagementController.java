package com.nike.reporting.controller;

import java.util.List;

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
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.SearchProblemManagement;
import com.nike.reporting.model.converter.ProblemManagementConverter;
import com.nike.reporting.service.ProblemManagementService;
import com.nike.reporting.ui.ProblemManagementUI;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ProblemManagementUtil;

@Controller
public class ProblemManagementController {

	private static final Logger logger = LoggerFactory.getLogger(ProblemManagementController.class);

	private ProblemManagementService problemManagementService;

	@Autowired(required = true)
	@Qualifier(value = "problemManagementService")
	public void setProblemManagementService(ProblemManagementService problemManagementService) {
		this.problemManagementService = problemManagementService;
	}

	@RequestMapping(value = "/manageProblem", method = RequestMethod.GET)
	public String manageProblem(Model model) {
		logger.info("Entering method manageProblem");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		ProblemManagementUI problemManagementUI = new ProblemManagementUI();
		model.addAttribute("problemManagementUI", problemManagementUI);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("statusList", CommonsUtil.problemManagementStatusList);
		model.addAttribute("benefitTypeList", CommonsUtil.problemManagementBenefitTypeList);

		logger.info("Exiting method manageProblem");
		return "manageProblem";
	}

	@RequestMapping(value = "/problemManagement/add", method = RequestMethod.POST)
	public String addProblemManagement(@ModelAttribute("problemManagementUI") ProblemManagementUI problemManagementUI, BindingResult result, Model model) {
		logger.info("Entering method addProblemManagement");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		ProblemManagement pm = null;
		boolean isUpdateRequest = false;
		try {
			if (problemManagementUI.getId() != null && problemManagementUI.getId() != 0) {
				pm = this.problemManagementService.getProblemManagementById(problemManagementUI.getId());
				isUpdateRequest = true;
			}

			pm = ProblemManagementUtil.populateProblemManagement(problemManagementUI, pm);

			if (!isUpdateRequest) {
				this.problemManagementService.addProblemManagement(pm);
				model.addAttribute("successMessage", String.format("Problem Management with id %d added successfully", pm.getId()));
			} else {
				this.problemManagementService.updateProblemManagement(pm);
				model.addAttribute("successMessage", String.format("Problem Management with id %d updated successfully", pm.getId()));
				model.addAttribute("problemManagementUI", problemManagementUI);
				model.addAttribute("applicationList", CommonsUtil.applicationsList);
				model.addAttribute("priorityList", CommonsUtil.priorityList);
				model.addAttribute("statusList", CommonsUtil.problemManagementStatusList);
				model.addAttribute("benefitTypeList", CommonsUtil.problemManagementBenefitTypeList);
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}
		logger.info("Exiting method addProblemManagement");

		return isUpdateRequest ? "manageProblem" : manageProblem(model);
	}

	@RequestMapping("/editProblemManagement/{id}")
	public String editProblemManagement(@PathVariable("id") int id, Model model) {

		logger.info("Entering method editProblemManagement");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		ProblemManagement pm = this.problemManagementService.getProblemManagementById(id);

		Object problemManagementUI = ProblemManagementUtil.populateProblemManagementForUI(pm);
		model.addAttribute("problemManagementUI", problemManagementUI);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("priorityList", CommonsUtil.priorityList);
		model.addAttribute("statusList", CommonsUtil.problemManagementStatusList);
		model.addAttribute("benefitTypeList", CommonsUtil.problemManagementBenefitTypeList);

		logger.info("Exiting method editProblemManagement");
		return "manageProblem";
	}

	@RequestMapping(value = "/searchPM", method = RequestMethod.GET)
	public String searchPM(Model model) {
		logger.info("Entering method searchPM");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		SearchProblemManagement spm = new SearchProblemManagement();
		model.addAttribute("searchProblemManagement", spm);
		model.addAttribute("applicationList", CommonsUtil.applicationsList);
		model.addAttribute("statusList", CommonsUtil.problemManagementStatusList);
		model.addAttribute("benefitTypeList", CommonsUtil.problemManagementBenefitTypeList);

		logger.info("Exiting method searchPM");
		return "searchPM";
	}

	@RequestMapping(value = "/searchPM/search", method = RequestMethod.GET)
	public String searchPMSubmit(@ModelAttribute("searchProblemManagement") SearchProblemManagement spm, Model model) {

		logger.info("Entering method searchPMSubmit");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		List<ProblemManagement> problemManagementList = null;
		try {
			problemManagementList = problemManagementService.getProblemManagementBySearchCriteria(spm);

			ProblemManagementConverter pmConverter = new ProblemManagementConverter();
			model.addAttribute("searchProblemManagement", spm);
			model.addAttribute("listProblemManagements", pmConverter.convertFromObjectToDTOList(problemManagementList));
			model.addAttribute("applicationList", CommonsUtil.applicationsList);
			model.addAttribute("statusList", CommonsUtil.problemManagementStatusList);
			model.addAttribute("benefitTypeList", CommonsUtil.problemManagementBenefitTypeList);

			logger.debug("Problem Management List size is {}.", problemManagementList.size());

		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}

		logger.info("Exiting method searchPMSubmit");
		return "searchPM";
	}

}
