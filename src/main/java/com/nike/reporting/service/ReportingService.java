/**
 * 
 */
package com.nike.reporting.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;

import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.DashboardSearch;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.model.SearchProblemManagement;

/**
 * @author Sachin_Ainapure
 * 
 */
public interface ReportingService {
	public void createOutageCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchOutage so, String fileName);

	public void createMTBFPerDaysChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchOutage searchOutage, String fileName);

	public void createProblemManagementCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchProblemManagement spm, String fileName);

	public void createAssignGroupWiseMTTRPIEChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName);

	// public JFreeChart createPIEChartAssignGroupWise(List<ExcelMTTRDTO>
	// excelMTTRDTOList);

	public void createPIEChartPriorityWise(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName);

	public void createPIEChartReasonWise(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName);

	public void createMTTRCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName, List<MTTRData> mttrData);

	public String createDashBoardPPTX(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, Map userImageMap, DashboardSearch dos) throws NikeException;

}
