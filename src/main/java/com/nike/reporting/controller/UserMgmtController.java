package com.nike.reporting.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.DashboardSearch;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.Nrt_user;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.service.HighlightService;
import com.nike.reporting.service.MTTRService;
import com.nike.reporting.service.ReportingService;
import com.nike.reporting.service.UserService;
import com.nike.reporting.util.FileDownloadUtil;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

@Controller
@SessionAttributes(value = { "AuthenticatedUser", "userObj" })
public class UserMgmtController {

	private static final Logger logger = LoggerFactory.getLogger(UserMgmtController.class);
	private static Map<String, List<String>> userImageMap = new HashMap<String, List<String>>();

	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}

	private ReportingService reportingService;

	@Autowired(required = true)
	@Qualifier(value = "reportingService")
	public void setReportingService(ReportingService reportingService) {
		this.reportingService = reportingService;
	}

	private MTTRService mttrService;

	@Autowired(required = true)
	@Qualifier(value = "mttrService")
	public void setMTTRService(MTTRService mttrService) {
		this.mttrService = mttrService;
	}

	private HighlightService highlightService;

	@Autowired(required = true)
	@Qualifier(value = "highlightService")
	public void setHighlightService(HighlightService highlightService) {
		this.highlightService = highlightService;
	}

	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public String listusers(Model model) {
		model.addAttribute("Nrt_user", new Nrt_user());
		return "listusers";
	}

	@RequestMapping(value = "/fetchUsers", method = RequestMethod.GET)
	public @ResponseBody
	List<Nrt_user> fetchUsers() {

		logger.info("Entering method listusers");

		List<Nrt_user> userList = new ArrayList<>();

		// Create Session
		LoginUtil.setValidSession();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		userList = this.userService.listUsers(userDetails.getUsername());

		logger.debug("User List Size : " + userList.size());

		logger.info("Exiting method listusers");
		return userList;
	}

	@RequestMapping(value = "/AddUser", method = RequestMethod.GET)
	public String addUsers(Model model) {

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Nrt_user nrtUser = new Nrt_user();
		model.addAttribute("Nrt_user", nrtUser);
		return "addUser";

	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupUser(Model model) {
		return "signup";
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Reporting Tool");
		model.setViewName("index");
		return model;

	}

	@RequestMapping(value = { "/generic_error" }, method = RequestMethod.GET)
	public ModelAndView errorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("generic_error");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			logger.error("<< Login Error >>");
			model.addObject("error", "Invalid username and password!");
		}

		model.setViewName("index");

		return model;

	}

	@RequestMapping(value = "/signoff", method = RequestMethod.GET)
	public ModelAndView logout(@RequestParam(value = "logout", required = false) String logout) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();

		// logout from session.
		if (session != null) {
			session.invalidate();
		}

		ModelAndView model = new ModelAndView();

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("index");

		return model;

	}

	@RequestMapping(value = "/authenticateUser", method = RequestMethod.GET)
	public String autheticateUser(@ModelAttribute("Nrt_user") Nrt_user up, BindingResult result, Model model) {
		if (!(this.userService.authenticateUser(up))) {
			model.addAttribute("message", "Please check username or password");
		}
		return "home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String returnHome(@ModelAttribute("dashboardSearch") DashboardSearch dos, Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method returnHome");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userName = auth.getName(); // get logged in username
		model.addAttribute("message", "User Name : " + userName);

		// Set default criteria for dashboard

		SearchOutage so = new SearchOutage();
		List<String> userGraphsList = new ArrayList<String>();
		try {

			DateFormat dateFormat = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

			String strToDate = (String) request.getSession().getAttribute("toDate");
			if (strToDate == null || strToDate.trim().length() == 0) {
				strToDate = dateFormat.format(cal.getTime());
			}
			// String strToDate = dateFormat.format(cal.getTime());

			// Subtract DASHBOAD_DATE_DIFF from current date
			// cal.add(Calendar.DATE, ReportingConstants.DASHBOAD_DATE_DIFF *
			// -1);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 00, 00, 00);

			String strFromDate = (String) request.getSession().getAttribute("fromDate");
			if (strFromDate == null || strFromDate.trim().length() == 0) {
				strFromDate = dateFormat.format(cal.getTime());
			}
			// String strFromDate = dateFormat.format(cal.getTime());

			so.setToDate(strToDate);
			so.setFromDate(strFromDate);

			dos.setFromDate(strFromDate);
			dos.setToDate(strToDate);

			model.addAttribute("dashboardSearch", dos);

			// Create MTBF chart
			String mtbfFileName = userName + "_" + ReportingConstants.MTBF_PER_DAYS_PNG_FILENAME;
			String mtbfFilePath = "\\NikeReportingTool\\resources\\downloads\\" + mtbfFileName;
			reportingService.createMTBFPerDaysChart(request, response, userDetails, so, mtbfFileName);
			model.addAttribute("mtbfFilePath", mtbfFilePath);
			// do not add in mttr ppt
			// userGraphsList.add(mtbfFileName);

			// Create outage count bar chart
			String outageCountFileName = userName + "_" + ReportingConstants.OUTAGE_COUNT_BAR_CHART;
			String outageCountFilePath = "\\NikeReportingTool\\resources\\downloads\\" + outageCountFileName;
			reportingService.createOutageCountBarChart(request, response, userDetails, so, outageCountFileName);
			model.addAttribute("outageCountFilePath", outageCountFilePath);
			model.addAttribute("timeStamp", System.currentTimeMillis());
			// do not add in mttr ppt
			// userGraphsList.add(outageCountFileName);

			// ###### MTTR GRAPHS--START ######
			// Create MTTR Pie chart [MTTRPerAssignmentGroup]
			SearchMTTR sm = new SearchMTTR();
			sm.setFromDate(strFromDate);
			sm.setToDate(strToDate);
			sm.setIsMTTRBreached(true);

			createMTTRChars(request, response, model, userDetails, sm, userGraphsList);

			// Fetch Highlights
			// List<HighlightDTO> highlightDtoList = new ArrayList<>();
			// SearchHighlight searchHighlight = new SearchHighlight();
			// searchHighlight.setFromDate(dos.getFromDate());
			// searchHighlight.setToDate(dos.getToDate());
			// highlightDtoList =
			// highlightService.getHighlightBySearchCriteria(searchHighlight);
			// logger.debug("highlightList size is {}.",
			// highlightDtoList.size());
			// model.addAttribute("highlightDtoList", highlightDtoList);

			// Create problem management count bar chart
			// SearchProblemManagement spm = new SearchProblemManagement();
			// spm.setToDate(strToDate);
			// spm.setFromDate(strFromDate);
			// spm.setFetchForLastModified(true);
			// String pmCountFileName = name + "_" +
			// ReportingConstants.PROBLEM_MANAGEMENT_COUNT_BAR_CHART;
			// String pmCountFilePath =
			// "\\NikeReportingTool\\resources\\downloads\\" + pmCountFileName;
			// reportingService.createProblemManagementCountBarChart(request,
			// response, userDetails, spm, pmCountFileName);
			// model.addAttribute("pmCountFilePath", pmCountFilePath);
			// model.addAttribute("dashboardSearch", dos);

			// Put all image list in a map against the username. No need to put
			// it again on searchDashboardSubmit()
			userImageMap.put(userName, userGraphsList);
			request.getSession().setAttribute("userImageMap", userImageMap);
			// put below again on searchDashboardSubmit() as date may change
			request.getSession().setAttribute("fromDate", strFromDate);
			request.getSession().setAttribute("toDate", strToDate);
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", e.getMessage());
		}

		logger.info("Exiting method returnHome");
		return "home";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("Nrt_user") Nrt_user up, BindingResult result, Model model) {

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String user_id = userDetails.getUsername();
		Nrt_user u = null;
		try {

			if (up.getId() != null && up.getId() != 0) {
				u = this.userService.getUserById(up.getId());
			}

			/*
			 * Add audit info for the User.
			 */
			AuditInfo auditInfo = null;
			auditInfo = new AuditInfo();
			if (u == null) {
				auditInfo.setCreatedBy(user_id);
				auditInfo.setUpdatedBy(user_id);
				auditInfo.setCreatedDate(new Date());
				auditInfo.setUpdatedDate(new Date());
			} else {
				auditInfo = u.getAuditInfo();
				if (auditInfo == null) {
					auditInfo = new AuditInfo();
				}
			}
			auditInfo.setUpdatedBy(user_id);
			auditInfo.setUpdatedDate(new Date());
			up.setAuditInfo(auditInfo);

			if (up.getId() == null || up.getId() == 0) {
				this.userService.addUser(up);
				model.addAttribute("message", "User " + up.getNikeid() + " added successfully");

			} else {
				// existing person, call update
				if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) && !(LoginUtil.getLoggedInUser().equalsIgnoreCase(up.getNikeid()))) {
					model.addAttribute("errorMessage", "Not Authorized to view this user details!");
					model.addAttribute("Nrt_user", this.userService.getUserByName(LoginUtil.getLoggedInUser()));
					return "addUser";
				}
				this.userService.updateUser(up);
				up.setPassword("");
				model.addAttribute("message", "User " + up.getNikeid() + " updated successfully");
				model.addAttribute("Nrt_user", up);
				return "addUser";
			}
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			if (up.getId() == null || up.getId() == 0) {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			} else {
				model.addAttribute("errorMessage", String.format(e.getErrMsg()));
			}
		}
		return addUsers(model);

	}

	@RequestMapping(value = "/user/checkUserAvailibility", method = RequestMethod.GET)
	public @ResponseBody
	String checkUserAvailibility(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method checkUserAvailibility");
		Nrt_user up = null;
		String tempUserId = "";
		try {
			@SuppressWarnings("unchecked")
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			if (null != json.get("nikeid") && json.get("nikeid").toString().trim().length() > 0) {
				tempUserId = (String) json.get("nikeid");
			}

			// check if user id exists in DB, if yes throw error.
			up = this.userService.getUserByName(tempUserId);

			if (null != up) {
				return "errorMessage|";
			} else {
				return "successMessage|";
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			return "Exception|" + String.format(e.getMessage());
		} finally {
			logger.info("Exiting method checkUserAvailibility");
		}
	}

	@RequestMapping(value = "/user/signUp", method = RequestMethod.GET)
	public @ResponseBody
	String signUp(Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method signUp");

		Nrt_user up = null;
		String tempUserId = "";
		try {
			JSONObject json = (JSONObject) new JSONParser().parse((String) Collections.list(request.getParameterNames()).get(0));

			if (null != json.get("nikeid") && json.get("nikeid").toString().trim().length() > 0) {
				tempUserId = (String) json.get("nikeid");
			}

			// check if user id exists in DB, if yes throw error.
			up = this.userService.getUserByName(tempUserId);

			if (null != up) {
				return "errorMessage|" + String.format("Active user %S already exists!!! Please try different user name or contact admin to reset the password.", tempUserId);
			}

			// If not
			up = new Nrt_user();

			// Add audit info for the User.
			AuditInfo auditInfo = new AuditInfo(up.getNikeid(), new Date(), up.getNikeid(), new Date());
			up.setAuditInfo(auditInfo);

			// Add user Info
			up.setNikeid(tempUserId);
			up.setInfyid(tempUserId);
			up.setFname((String) json.get("fname"));
			up.setLname((String) json.get("lname"));
			up.setPassword((String) json.get("password"));
			up.setRole((String) json.get("role"));
			up.setStatus((String) json.get("status"));
			up.setNikeEmail((String) json.get("nikeEmail"));
			up.setInfyEmail((String) json.get("infyEmail"));

			this.userService.addUser(up);
			return "successMessage|" + String.format("%S You have been successfully registered!!! Please login with your credentials.", tempUserId);

		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			return "errorMessage|" + String.format(e.getMessage());
		} finally {
			logger.info("Exiting method signUp");
		}

	}

	@RequestMapping("/deleteUser/{id}")
	public String removeUser(@PathVariable("id") int id) {

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		this.userService.deleteUser(id);
		return "redirect:/listUsers";
	}

	@RequestMapping("/updateUser/{id}")
	public String editUser(@PathVariable("id") int id, Model model) {
		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}
		Nrt_user nu;
		try {
			nu = this.userService.getUserById(id);
			// To remove password from UI display
			nu.setPassword("");
			// existing person, call update
			if (!(LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN)) && !(LoginUtil.getLoggedInUser().equalsIgnoreCase(nu.getNikeid()))) {
				model.addAttribute("errorMessage", "Not Authorized to view this user details!");
				nu = this.userService.getUserByName(LoginUtil.getLoggedInUser());
				nu.setPassword("");
				model.addAttribute("Nrt_user", nu);
				return "addUser";
			}
			model.addAttribute("Nrt_user", nu);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", String.format(e.getErrMsg()));
		}

		return "addUser";
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();
		// Create Session
		if (!LoginUtil.setValidSession()) {
			model.setViewName("index");
			return model;
		}

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			logger.info(userDetail.toString());
			model.addObject("username", userDetail.getUsername());
		} else {
			model.setViewName("403");
		}
		return model;
	}

	@RequestMapping(value = "/dashboard/search", method = RequestMethod.GET)
	public String searchDashboardSubmit(@ModelAttribute("dashboardSearch") DashboardSearch dos, Model model, HttpServletRequest request, HttpServletResponse response) {

		logger.info("Entering method searchOutageSubmit");

		// Create Session
		if (!LoginUtil.setValidSession()) {
			return "index";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String name = auth.getName(); // get logged in username
		List<String> userGraphsList = new ArrayList<String>();
		try {
			SearchOutage so = new SearchOutage();
			// System.out.println(dos.getFromDate());
			// System.out.println(dos.getToDate());
			so.setFromDate(dos.getFromDate());
			so.setToDate(dos.getToDate());
			model.addAttribute("dashboardSearch", dos);

			// Create MTBF chart
			String mtbfFileName = name + "_" + ReportingConstants.MTBF_PER_DAYS_PNG_FILENAME;
			String mtbfFilePath = "\\NikeReportingTool\\resources\\downloads\\" + mtbfFileName;
			reportingService.createMTBFPerDaysChart(request, response, userDetails, so, mtbfFileName);
			model.addAttribute("mtbfFilePath", mtbfFilePath);

			// Create outage count bar chart
			String outageCountFileName = name + "_" + ReportingConstants.OUTAGE_COUNT_BAR_CHART;
			String outageCountFilePath = "\\NikeReportingTool\\resources\\downloads\\" + outageCountFileName;
			reportingService.createOutageCountBarChart(request, response, userDetails, so, outageCountFileName);
			model.addAttribute("outageCountFilePath", outageCountFilePath);
			model.addAttribute("timeStamp", System.currentTimeMillis());

			// Create MTTR Pie chart [MTTRPerAssignmentGroup]
			SearchMTTR sm = new SearchMTTR();
			sm.setFromDate(dos.getFromDate());
			sm.setToDate(dos.getToDate());
			sm.setIsMTTRBreached(true);

			createMTTRChars(request, response, model, userDetails, sm, userGraphsList);

			request.getSession().setAttribute("fromDate", dos.getFromDate());
			request.getSession().setAttribute("toDate", dos.getToDate());

			// Fetch Highlights
			// List<HighlightDTO> highlightDtoList = new ArrayList<>();
			// SearchHighlight searchHighlight = new SearchHighlight();
			// searchHighlight.setFromDate(dos.getFromDate());
			// searchHighlight.setToDate(dos.getToDate());
			// highlightDtoList =
			// highlightService.getHighlightBySearchCriteria(searchHighlight);
			// logger.debug("highlightList size is {}.",
			// highlightDtoList.size());
			// model.addAttribute("highlightDtoList", highlightDtoList);

			// Create problem management count bar chart
			// SearchProblemManagement spm = new SearchProblemManagement();
			// spm.setFromDate(dos.getFromDate());
			// spm.setToDate(dos.getToDate());
			// spm.setFetchForLastModified(true);
			// String pmCountFileName = name + "_" +
			// ReportingConstants.PROBLEM_MANAGEMENT_COUNT_BAR_CHART;
			// String pmCountFilePath =
			// "\\NikeReportingTool\\resources\\downloads\\" + pmCountFileName;
			// reportingService.createProblemManagementCountBarChart(request,
			// response, userDetails, spm , pmCountFileName);
			// model.addAttribute("pmCountFilePath", pmCountFilePath);
			// model.addAttribute("dashboardSearch", dos);
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", e.getMessage());
		}

		logger.info("Exiting method searchOutageSubmit");
		return "home";
	}

	@RequestMapping(value = "/dashboard/createPPTX", method = RequestMethod.GET)
	public void dashboardCreatePPTX(@ModelAttribute("dashboardSearch") DashboardSearch dos, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering method dashboardCreatePPTX");
		String fromDate = (String) request.getSession().getAttribute("fromDate");
		String toDate = (String) request.getSession().getAttribute("toDate");
		dos.setFromDate(fromDate);
		dos.setToDate(toDate);

		// Create Session
		LoginUtil.setValidSession();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String name = auth.getName(); // get logged in username
		try {
			Map userImageMap = (Map) request.getSession().getAttribute("userImageMap");
			String pptxFileName = reportingService.createDashBoardPPTX(request, response, userDetails, userImageMap, dos);
			FileDownloadUtil.doDownload(request, response, pptxFileName, false);
			/*
			 * do not remove userImageMap from session
			 * request.getSession().removeAttribute("userImageMap");
			 */
			// request.getSession().removeAttribute("fromDate");
			// request.getSession().removeAttribute("toDate");
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage(), e);
			model.addAttribute("errorMessage", e.getMessage());
		}
		logger.info("Exiting method dashboardCreatePPTX");
		// return "home";
	}

	private void createMTTRChars(HttpServletRequest request, HttpServletResponse response, Model model, UserDetails userDetails, SearchMTTR sm, List<String> userGraphsList)
			throws DateParsingException {

		String userName = LoginUtil.getLoggedInUser();

		String mttrPIEChartFileName = userName + "_" + ReportingConstants.MTTR_PER_ASSIGN_GROUP_PNG_FILENAME;
		String mttrPIEChartFilePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrPIEChartFileName;
		reportingService.createAssignGroupWiseMTTRPIEChart(request, response, userDetails, sm, mttrPIEChartFileName);
		model.addAttribute("mttrPIEChartFilePath", mttrPIEChartFilePath);

		userGraphsList.add(mttrPIEChartFileName);

		// Create MTTR Pie chart [MTTRPriorityWise]
		String mttrPriorityWisePIEChartFileName = userName + "_" + ReportingConstants.MTTR_PRIORITY_WISE_PNG_FILENAME;
		String mttrPriorityWisePIEChartFilePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrPriorityWisePIEChartFileName;
		reportingService.createPIEChartPriorityWise(request, response, userDetails, sm, mttrPriorityWisePIEChartFileName);
		model.addAttribute("mttrPriorityWisePIEChartFilePath", mttrPriorityWisePIEChartFilePath);

		userGraphsList.add(mttrPriorityWisePIEChartFileName);

		// Create MTTR Pie chart [MTTRReasonWise]
		String mttrReasonWisePIEChartFileName = userName + "_" + ReportingConstants.MTTR_REASON_WISE_PNG_FILENAME;
		String mttrReasonWisePIEChartFilePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrReasonWisePIEChartFileName;
		reportingService.createPIEChartReasonWise(request, response, userDetails, sm, mttrReasonWisePIEChartFileName);
		model.addAttribute("mttrReasonWisePIEChartFilePath", mttrReasonWisePIEChartFilePath);

		userGraphsList.add(mttrReasonWisePIEChartFileName);

		sm.setIsMTTRBreached(null);
		List<MTTRData> mttrData = mttrService.getMTTRBySearchCriteria(sm);

		// Create MTTR Incident count bar chart
		String mttrIncidentCountFileName = userName + "_" + ReportingConstants.MTTR_ASSIGN_GROUP_PIE_PNG_FILENAME;
		String mttrIncidentCountFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrIncidentCountFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrIncidentCountFileName, mttrData);
		model.addAttribute("mttrIncidentCountFileNamePath", mttrIncidentCountFileNamePath);

		userGraphsList.add(mttrIncidentCountFileName);

		// Create MTTR P1, P2, P3 & P4 Incident count bar chart
		sm.setIsMTTRBreached(null);
		String mttrP1IncidentCountFileName = userName + "_" + ReportingConstants.MTTR_P1_HRS_BAR_CHART_PNG;
		String mttrP1IncidentCountFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrP1IncidentCountFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrP1IncidentCountFileName, mttrData);
		model.addAttribute("mttrP1IncidentCountFileNamePath", mttrP1IncidentCountFileNamePath);

		userGraphsList.add(mttrP1IncidentCountFileName);

		String mttrP2IncidentCountFileName = userName + "_" + ReportingConstants.MTTR_P2_HRS_BAR_CHART_PNG;
		String mttrP2IncidentCountFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrP2IncidentCountFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrP2IncidentCountFileName, mttrData);
		model.addAttribute("mttrP2IncidentCountFileNamePath", mttrP2IncidentCountFileNamePath);

		userGraphsList.add(mttrP2IncidentCountFileName);

		String mttrP3IncidentCountFileName = userName + "_" + ReportingConstants.MTTR_P3_HRS_BAR_CHART_PNG;
		String mttrP3IncidentCountFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrP3IncidentCountFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrP3IncidentCountFileName, mttrData);
		model.addAttribute("mttrP3IncidentCountFileNamePath", mttrP3IncidentCountFileNamePath);

		userGraphsList.add(mttrP3IncidentCountFileName);

		String mttrP4IncidentCountFileName = userName + "_" + ReportingConstants.MTTR_P4_HRS_BAR_CHART_PNG;
		String mttrP4IncidentCountFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrP4IncidentCountFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrP4IncidentCountFileName, mttrData);
		model.addAttribute("mttrP4IncidentCountFileNamePath", mttrP4IncidentCountFileNamePath);
		model.addAttribute("timeStamp", System.currentTimeMillis());

		userGraphsList.add(mttrP4IncidentCountFileName);

		sm.setFromDate(null);
		sm.setToDate(null);
		mttrData = mttrService.getMTTRBySearchCriteria(sm);

		String mttrWeeklyPriorityHrsChartFileName = userName + "_" + ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_PNG;
		String mttrWeeklyPriorityHrsChartFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrWeeklyPriorityHrsChartFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrWeeklyPriorityHrsChartFileName, mttrData);
		model.addAttribute("mttrWeeklyPriorityHrsChartFileNamePath", mttrWeeklyPriorityHrsChartFileNamePath);
		model.addAttribute("timeStamp", System.currentTimeMillis());

		userGraphsList.add(mttrWeeklyPriorityHrsChartFileName);

		String mttrMonthlyPriorityHrsChartFileName = userName + "_" + ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_PNG;
		String mttrMonthlyPriorityHrsChartFileNamePath = "\\NikeReportingTool\\resources\\downloads\\" + mttrMonthlyPriorityHrsChartFileName;
		reportingService.createMTTRCountBarChart(request, response, userDetails, sm, mttrMonthlyPriorityHrsChartFileName, mttrData);
		model.addAttribute("mttrMonthlyPriorityHrsChartFileNamePath", mttrMonthlyPriorityHrsChartFileNamePath);
		model.addAttribute("timeStamp", System.currentTimeMillis());

		userGraphsList.add(mttrMonthlyPriorityHrsChartFileName);

	}

}
