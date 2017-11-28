/**
 * 
 */
package com.nike.reporting.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.SearchHighlight;
import com.nike.reporting.model.converter.HighlightConvertor;
import com.nike.reporting.model.dto.HighlightDTO;
import com.nike.reporting.service.HighlightService;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.LoginUtil;

/**
 * @author vishal_lalwani01
 * 
 */
@Controller
public class HighlightController {

	private static final Logger logger = LoggerFactory.getLogger(HighlightController.class);

	private HighlightService highlightService;

	@Autowired(required = true)
	@Qualifier(value = "highlightService")
	public void setHighlightService(HighlightService highlightService) {
		this.highlightService = highlightService;
	}

	private HighlightConvertor highlightConvertor;

	@Autowired(required = true)
	@Qualifier(value = "highlightConvertor")
	public void setHighlightConvertor(HighlightConvertor highlightConvertor) {
		this.highlightConvertor = highlightConvertor;
	}

	@RequestMapping(value = "/manageHighlights", method = RequestMethod.GET)
	public String manageHighlightNavigation(Model model) {
		logger.info("Entering method manageHighlightNavigation");
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		HighlightDTO highlightDTO = new HighlightDTO();

		model.addAttribute("highlightDTO", highlightDTO);
		model.addAttribute("searchHighlight", new SearchHighlight());
		model.addAttribute("capabilityList", CommonsUtil.capabilityList);

		logger.info("Exiting method manageHighlightNavigation");
		return "manageHighlight";
	}

	@RequestMapping(value = "/manageHighlights/add", method = RequestMethod.GET)
	public @ResponseBody
	String addHighlight(Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method addHighlight");

		// Create Session
		LoginUtil.setValidSession();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String user_id = userDetails.getUsername();

		Highlight highlight = new Highlight();
		HighlightDTO highlightDTO = new HighlightDTO();
		int tempHighlightId = 0;
		try {
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			if (null != json.get("highlight_id") && json.get("highlight_id").toString().trim().length() > 0) {
				tempHighlightId = Integer.parseInt((String) json.get("highlight_id"));
			}

			if (tempHighlightId != 0) {
				highlightDTO.setId(tempHighlightId);
			}

			List<String> cap = new ArrayList<String>();
			if (null != json.get("capabilities")) {
				JSONArray c = (JSONArray) json.get("capabilities");
				for (int i = 0; i < c.size(); i++) {
					cap.add(c.get(i).toString());
				}
			}
			highlightDTO.setCapabilities(cap);
			highlightDTO.setFromDate(new Date((String) json.get("fromDate")));
			highlightDTO.setToDate(new Date((String) json.get("toDate")));
			highlightDTO.setDescription((String) json.get("description"));

			highlight = highlightConvertor.convertDtoToEntity(highlightDTO);

			// fetch highlight from db if already present for getting audit info

			Highlight highlightForAuditInfo = null;
			if (tempHighlightId != 0) {
				highlightForAuditInfo = this.highlightService.getHighlightById(tempHighlightId);
			}

			/*
			 * Add audit info for the Highlight.
			 */
			AuditInfo auditInfo = null;
			auditInfo = new AuditInfo();
			if (highlightForAuditInfo == null) {
				auditInfo.setCreatedBy(user_id);
				auditInfo.setUpdatedBy(user_id);
				auditInfo.setCreatedDate(new Date());
				auditInfo.setUpdatedDate(new Date());
			} else {
				auditInfo = highlightForAuditInfo.getAuditInfo();
				if (auditInfo == null) {
					auditInfo = new AuditInfo();
				}
			}
			auditInfo.setUpdatedBy(user_id);
			auditInfo.setUpdatedDate(new Date());
			highlight.setAuditInfo(auditInfo);

			if (highlight.getId() == null || highlight.getId() == 0) {
				this.highlightService.addHighlight(highlight);
				return "successMessage|" + String.format("Highlight id %d added successfully", highlight.getId());
			} else {
				this.highlightService.updateHighlight(highlight);
				return "successMessage|" + String.format("Highlight id %d updated successfully", highlight.getId());
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			return "errorMessage|" + String.format(e.getMessage());
		} finally {
			logger.info("Exiting method addHighlight");
		}
	}

	@RequestMapping(value = "/manageHighlights/search", method = RequestMethod.GET)
	public @ResponseBody
	List<HighlightDTO> searchHighlightSubmit(Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method searchHighlightSubmit");

		// Create Session
		LoginUtil.setValidSession();

		List<HighlightDTO> highlightList = new ArrayList<>();
		SearchHighlight searchHighlight = new SearchHighlight();
		try {
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			List<String> cap = new ArrayList<String>();
			if (null != json.get("capabilities")) {
				JSONArray c = (JSONArray) json.get("capabilities");
				for (int i = 0; i < c.size(); i++) {
					cap.add(c.get(i).toString());
				}
			}
			searchHighlight.setCapabilities(cap);
			searchHighlight.setFromDate((String) json.get("fromDate"));
			searchHighlight.setToDate((String) json.get("toDate"));

			highlightList = highlightService.getHighlightBySearchCriteria(searchHighlight);

			logger.debug("highlightList size is {}.", highlightList.size());

		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}

		logger.info("Exiting method searchHighlightSubmit");
		return highlightList;
	}

	@RequestMapping(value = "/manageHighlights/delete", method = RequestMethod.GET)
	public @ResponseBody
	String deleteHighlights(Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method deleteHighlights");

		// Create Session
		LoginUtil.setValidSession();

		try {
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			List<Integer> highlightIds = new ArrayList<>();
			String highlightIdsStr = "";

			if (null != json.get("highlightIdArray")) {
				JSONArray c = (JSONArray) json.get("highlightIdArray");
				for (int i = 0; i < c.size(); i++) {
					highlightIdsStr += c.get(i).toString() + ",";
					highlightIds.add(Integer.parseInt(c.get(i).toString()));
				}
			}

			highlightIdsStr = highlightIdsStr.substring(0, highlightIdsStr.length() - 1);

			logger.debug("About to delete following highlights >>", highlightIdsStr);

			this.highlightService.deleteHighlights(highlightIds);
			return "successMessage|" + String.format("Highlight Id(s) %s deleted successfully", highlightIdsStr);

		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			return "errorMessage|" + "Error occured while deleting highlight(s)!!! " + String.format(e.getMessage());
		} finally {
			logger.info("Exiting method deleteHighlights");
		}
	}

}
