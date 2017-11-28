/**
 * 
 */
package com.nike.reporting.service;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.ApplicationDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Application;
import com.nike.reporting.model.DashboardSearch;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.model.SearchProblemManagement;
import com.nike.reporting.model.converter.MTTRConverter;
import com.nike.reporting.model.converter.OutageConverter;
import com.nike.reporting.model.converter.ProblemManagementConverter;
import com.nike.reporting.model.dto.ExcelMTTRDTO;
import com.nike.reporting.model.dto.MTBFPerDayDTO;
import com.nike.reporting.model.dto.OutageDTO;
import com.nike.reporting.model.dto.ProblemManagementDTO;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.GraphUtils;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */

// TODO: Standardize fonts and create common methods for all types of graphs
public class ReportingServiceImpl implements ReportingService {

	private static final Logger logger = LoggerFactory.getLogger(ReportingServiceImpl.class);
	private OutageService outageService;
	private ApplicationDAO applicationDAO;
	private ProblemManagementService problemManagementService;
	private MTTRService mttrService;

	@Autowired(required = true)
	@Qualifier(value = "outageService")
	public void setOutageService(OutageService outageService) {
		this.outageService = outageService;
	}

	@Autowired(required = true)
	@Qualifier(value = "applicationDAO")
	public void setApplicationDAO(ApplicationDAO applicationDAO) {
		this.applicationDAO = applicationDAO;
	}

	@Autowired(required = true)
	@Qualifier(value = "problemManagementService")
	public void setProblemManagementService(ProblemManagementService problemManagementService) {
		this.problemManagementService = problemManagementService;
	}

	@Autowired(required = true)
	@Qualifier(value = "mttrService")
	public void setMTTRService(MTTRService mttrService) {
		this.mttrService = mttrService;
	}

	@Override
	@Transactional
	public void createMTBFPerDaysChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchOutage searchOutage, String fileName) {
		List<Outage> outageList = null;
		JFreeChart barChart = null;
		try {
			outageList = outageService.getOutageBySearchCriteria(searchOutage);
			barChart = GraphUtils.createBARChart(ReportingConstants.MTBF_PER_DAYS_CHART_TITLE, ReportingConstants.MTBF_DOMAIN_X_AXIS_LABEL, ReportingConstants.MTBF_RANGE_Y_AXIS_LABEL,
					PlotOrientation.VERTICAL, createMTBFDataSet(outageList, searchOutage), true, true, false, Color.green, Color.red);
			final ChartRenderingInfo chartRenderingInfo = new ChartRenderingInfo(new StandardEntityCollection());
			final File barChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(barChartFile, barChart, 750, 400, chartRenderingInfo);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}
	}

	private DefaultCategoryDataset createMTBFDataSet(List<Outage> outageList, SearchOutage searchOutage) {
		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Map<String, MTBFPerDayDTO> mtbfUnplannedMap = new HashMap<String, MTBFPerDayDTO>();

		// 1. Find number of days in the selected duration -> A
		long diffDays = GraphUtils.getDaysBetweenDates(searchOutage.getFromDate(), searchOutage.getToDate());

		// 3. Calculate Total Time as: A * 24 -> C
		double totalTime = diffDays * 24;

		// get list of all unique applications and populate the map with empty
		// mtbfdto object
		List<Application> appList = applicationDAO.listApplications();
		for (Application application : appList) {
			MTBFPerDayDTO mtbfDto = new MTBFPerDayDTO();
			mtbfDto.setApplicationName(application.getAppName());
			mtbfUnplannedMap.put(application.getAppName(), mtbfDto);
		}

		for (Outage outage : outageList) {
			String strApps = "";
			StringBuffer sbApps = new StringBuffer(strApps);
			if (outage.getApplications() != null) {
				for (Application application : outage.getApplications()) {
					sbApps.append(application.getAppName() + ",");

				}
			}
			for (Application application : appList) {
				if (sbApps.toString().contains(application.getAppName())) {
					if (outage.getOutageType() == ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR) {

						// 7. Find Total unplanned outages per application for
						// the selected duration -> G
						MTBFPerDayDTO mtbfPerDayDTO = mtbfUnplannedMap.get(application.getAppName());
						mtbfPerDayDTO.setTotalUnplannedOutages(mtbfPerDayDTO.getTotalUnplannedOutages() + 1);

						// 2. Find Total downtime per application in hours -> B
						double diff = outage.getDeploymentEndDate().getTime() - outage.getDeploymentStartDate().getTime();
						double diffHrs = diff / (60 * 60 * 1000);
						mtbfPerDayDTO.setTotalDownTime(mtbfPerDayDTO.getTotalDownTime() + diffHrs);

						// at last put it in the map for checking in next
						// iteration
						mtbfUnplannedMap.put(application.getAppName(), mtbfPerDayDTO);
					}
				}
			}
		}

		for (String key : mtbfUnplannedMap.keySet()) {
			MTBFPerDayDTO mtbfPerDayDTO = mtbfUnplannedMap.get(key);
			// 4. Calculate Total Up Time as: C - B -> D
			mtbfPerDayDTO.setTotalUpTime(totalTime - mtbfPerDayDTO.getTotalDownTime());

			// 5. Total Uptime (%) as: (D *100) / C -> E (is in %)
			mtbfPerDayDTO.setTotalUpTimePercent((mtbfPerDayDTO.getTotalUpTime() * 100) / totalTime);

			// 6. Total Downtime (%) as: 100 - E -> F (is in %)
			mtbfPerDayDTO.setTotalDownTimePercent(100 - mtbfPerDayDTO.getTotalUpTimePercent());

			// 8. Calculate MTBF as: D/G -> H
			if (mtbfPerDayDTO.getTotalUnplannedOutages() > 0) {
				mtbfPerDayDTO.setMtbf(mtbfPerDayDTO.getTotalUpTime() / mtbfPerDayDTO.getTotalUnplannedOutages());
			} else {
				mtbfPerDayDTO.setMtbf(mtbfPerDayDTO.getTotalUpTime());
			}

			// 9. Calculate MTBF Days as: H/24 -> I
			mtbfPerDayDTO.setMtbfDays((int) (mtbfPerDayDTO.getMtbf() / 24));

			// 10. Calculate Average Downtime (hr) as: B / G -> J
			mtbfPerDayDTO.setAverageDownTimeHours(mtbfPerDayDTO.getTotalDownTime() / mtbfPerDayDTO.getTotalUnplannedOutages());

			// at last put it in the map for checking in next
			// iteration
			mtbfUnplannedMap.put(key, mtbfPerDayDTO);
		}

		for (String appName : mtbfUnplannedMap.keySet()) {
			dataset.addValue(mtbfUnplannedMap.get(appName).getMtbfDays(), ReportingConstants.MTBF, appName);
		}

		return dataset;
	}

	@Override
	@Transactional
	public void createOutageCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchOutage so, String fileName) {

		List<Outage> outageList = null;
		try {
			outageList = outageService.getOutageBySearchCriteria(so);
			OutageConverter outageConverter = new OutageConverter();

			Map<String, Integer> countUnplannedMap = new HashMap<String, Integer>();
			Map<String, Integer> countPlannedMap = new HashMap<String, Integer>();

			List<Application> appList = applicationDAO.listApplications();

			for (Application application : appList) {
				countPlannedMap.put(application.getAppName(), 0);
				countUnplannedMap.put(application.getAppName(), 0);
			}

			List<OutageDTO> outageDTOList = outageConverter.convertFromObjectToDTOList(outageList);

			for (OutageDTO outageDTO : outageDTOList) {
				for (Application application : appList) {
					if (outageDTO.getApplications().contains(application.getAppName())) {
						if (outageDTO.getOutageType().equalsIgnoreCase(ReportingConstants.UNPLANNED_OUTAGE_STRING)) {
							int countUnplanned = countUnplannedMap.get(application.getAppName());
							countUnplannedMap.put(application.getAppName(), ++countUnplanned);
						} else if (outageDTO.getOutageType().equalsIgnoreCase(ReportingConstants.PLANNED_OUTAGE_STRING)) {
							int countPlanned = countPlannedMap.get(application.getAppName());
							countPlannedMap.put(application.getAppName(), ++countPlanned);
						}
					}
				}
			}

			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String appName : countPlannedMap.keySet()) {
				if (countPlannedMap.get(appName) > 0) {
					dataset.addValue(countPlannedMap.get(appName), ReportingConstants.PLANNED_OUTAGE_STRING, appName);
				}
			}
			for (String appName : countUnplannedMap.keySet()) {
				if (countUnplannedMap.get(appName) > 0) {
					dataset.addValue(countUnplannedMap.get(appName), ReportingConstants.UNPLANNED_OUTAGE_STRING, appName);
				}
			}

			// create the chart...
			final JFreeChart barChart = GraphUtils.createBARChart(ReportingConstants.OUTAGE_COUNT_BAR_CHART_TITLE, "Applications", "Outage Count", PlotOrientation.VERTICAL, dataset, true, true,
					false, Color.green, Color.red);

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File barChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(barChartFile, barChart, 750, 400, info);

		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void createProblemManagementCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchProblemManagement spm, String fileName) {
		List<ProblemManagement> problemManagementList = null;
		try {
			problemManagementList = problemManagementService.getProblemManagementBySearchCriteria(spm);
			ProblemManagementConverter problemManagementConverter = new ProblemManagementConverter();

			List<ProblemManagementDTO> problemManagementDTOList = problemManagementConverter.convertFromObjectToDTOList(problemManagementList);
			Map<String, Integer> countInProgressPMMap = new LinkedHashMap<String, Integer>();
			Map<String, Integer> countStablePMMap = new LinkedHashMap<String, Integer>();

			List<Application> appList = applicationDAO.listApplications();

			for (Application application : appList) {
				countInProgressPMMap.put(application.getAppName(), 0);
				countStablePMMap.put(application.getAppName(), 0);
			}

			for (ProblemManagementDTO problemManagementDTO : problemManagementDTOList) {
				if (ReportingConstants.IN_PROGRESS_STATUSES.contains(problemManagementDTO.getStatus())) {
					int inProgressCounter = countInProgressPMMap.get(problemManagementDTO.getApplication()) + 1;
					countInProgressPMMap.put(problemManagementDTO.getApplication(), inProgressCounter);
				} else if (ReportingConstants.STABLE_STATUSES.contains(problemManagementDTO.getStatus())) {
					int stableCounter = countStablePMMap.get(problemManagementDTO.getApplication()) + 1;
					countStablePMMap.put(problemManagementDTO.getApplication(), stableCounter);
				}
			}

			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (String appName : countInProgressPMMap.keySet()) {
				dataset.addValue(countInProgressPMMap.get(appName), ReportingConstants.IN_PROGRESS, appName);
				dataset.addValue(countStablePMMap.get(appName), ReportingConstants.COMPLETE, appName);
			}

			// create the chart...
			final JFreeChart barChart = GraphUtils.createBARChart(ReportingConstants.PROBLEM_MANAGEMENT_COUNT_BAR_CHART_TITLE, "Application", "Problem Management Count", PlotOrientation.VERTICAL,
					dataset, true, true, false, Color.green, Color.red);

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File barChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(barChartFile, barChart, 750, 400, info);

		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void createAssignGroupWiseMTTRPIEChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName) {
		List<MTTRData> mttrDataList = null;
		try {
			mttrDataList = mttrService.getMTTRBySearchCriteria(searchMTTR);
			MTTRConverter mttrConverter = new MTTRConverter();
			Map<String, Integer> assignGroupWiseMap = new HashMap<String, Integer>();
			DefaultPieDataset result = new DefaultPieDataset();
			List<ExcelMTTRDTO> excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrDataList);
			for (ExcelMTTRDTO excelMTTRDTO : excelMTTRDTOList) {
				if (assignGroupWiseMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
					int value = Integer.parseInt(assignGroupWiseMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
					assignGroupWiseMap.put(excelMTTRDTO.getAssignmentGroup(), value);
				} else {
					assignGroupWiseMap.put(excelMTTRDTO.getAssignmentGroup(), Integer.parseInt("1"));
				}
			}

			/* Display content using Iterator */
			Set set = assignGroupWiseMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				result.setValue(mentry.getKey().toString().replace("Frontline - ", ""), Integer.parseInt(mentry.getValue().toString()));
			}
			JFreeChart pieChart = GraphUtils.createPIEChart(ReportingConstants.MTTR_ASSIGN_GROUP_PIE_CHART_TITLE, result, false, true, false);

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File pieChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(pieChartFile, pieChart, 600, 400, info);

		} catch (ParseException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}

	}

	@Override
	@Transactional
	public void createPIEChartPriorityWise(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName) {
		List<MTTRData> mttrDataList = null;
		try {
			mttrDataList = mttrService.getMTTRBySearchCriteria(searchMTTR);
			MTTRConverter mttrConverter = new MTTRConverter();
			Map<String, Integer> priorityWiseMap = new HashMap<String, Integer>();
			DefaultPieDataset result = new DefaultPieDataset();
			List<ExcelMTTRDTO> excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrDataList);
			for (ExcelMTTRDTO excelMTTRDTO : excelMTTRDTOList) {
				if (priorityWiseMap.containsKey(excelMTTRDTO.getPriority())) {
					int value = Integer.parseInt(priorityWiseMap.get(excelMTTRDTO.getPriority()).toString()) + 1;
					priorityWiseMap.put(excelMTTRDTO.getPriority(), value);
				} else {
					priorityWiseMap.put(excelMTTRDTO.getPriority(), Integer.parseInt("1"));
				}
			}

			/* Display content using Iterator */
			Set set = priorityWiseMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				String key = mentry.getKey().toString();
				key = key.replace(ReportingConstants.P1, "P1");
				key = key.replace(ReportingConstants.P2, "P2");
				key = key.replace(ReportingConstants.P3, "P3");
				key = key.replace(ReportingConstants.P4, "P4");
				result.setValue(key, Integer.parseInt(mentry.getValue().toString()));
			}

			JFreeChart pieChart = GraphUtils.createPIEChart(ReportingConstants.MTTR_PRIORITY_PIE_CHART_TITLE, result, false, true, false);

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File pieChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(pieChartFile, pieChart, 600, 400, info);

		} catch (ParseException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void createPIEChartReasonWise(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName) {
		List<MTTRData> mttrDataList = null;
		try {
			mttrDataList = mttrService.getMTTRBySearchCriteria(searchMTTR);
			MTTRConverter mttrConverter = new MTTRConverter();
			Map<String, Integer> reasonWiseMap = new HashMap<String, Integer>();
			DefaultPieDataset result = new DefaultPieDataset();
			List<ExcelMTTRDTO> excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrDataList);
			for (ExcelMTTRDTO excelMTTRDTO : excelMTTRDTOList) {
				if (reasonWiseMap.containsKey(excelMTTRDTO.getMttrBreachReason())) {
					int value = Integer.parseInt(reasonWiseMap.get(excelMTTRDTO.getMttrBreachReason()).toString()) + 1;
					reasonWiseMap.put(excelMTTRDTO.getMttrBreachReason(), value);
				} else {
					reasonWiseMap.put(excelMTTRDTO.getMttrBreachReason(), Integer.parseInt("1"));
				}
			}

			/* Display content using Iterator */
			Set set = reasonWiseMap.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				String key = mentry.getKey().toString();
				result.setValue(key, Integer.parseInt(mentry.getValue().toString()));
			}
			JFreeChart pieChart = GraphUtils.createPIEChart(ReportingConstants.MTTR_REASON_PIE_CHART_TITLE, result, false, true, false);

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File pieChartFile = GraphUtils.getChartFile(request, fileName);
			ChartUtilities.saveChartAsPNG(pieChartFile, pieChart, 600, 400, info);

		} catch (ParseException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (NikeException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("ERROR: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void createMTTRCountBarChart(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, SearchMTTR searchMTTR, String fileName, List<MTTRData> mttrData) {
		MTTRConverter mttrConverter = new MTTRConverter();
		DefaultCategoryDataset dataset;
		try {

			final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			final File barChartFile = GraphUtils.getChartFile(request, fileName);
			final String fileNameNoUserId = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.length());
			if (mttrData == null) {
				mttrData = mttrService.getMTTRBySearchCriteria(searchMTTR);
			}
			List<ExcelMTTRDTO> excelMTTRDTOList = mttrConverter.convertToExcelMTTRDTOObject(mttrData);
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_ASSIGN_GROUP_PIE_PNG_FILENAME)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_CLOSED_AND_HIGH_ASSIGN_GROUP_PIE_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createBARChart(ReportingConstants.MTTR_CLOSED_AND_HIGH_ASSIGN_GROUP_PIE_CHART_TITLE, "Assignment Group", "Incident Count",
						PlotOrientation.VERTICAL, dataset, true, true, false, Color.green, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_P1_HRS_BAR_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_P1_HRS_BAR_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createBARChart(ReportingConstants.MTTR_P1_HRS_BAR_CHART_TITLE, "Assignment Group", "P1 Average Hrs.", PlotOrientation.VERTICAL, dataset, true,
						true, false, Color.blue, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_P2_HRS_BAR_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_P2_HRS_BAR_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createBARChart(ReportingConstants.MTTR_P2_HRS_BAR_CHART_TITLE, "Assignment Group", "P2 Average Hrs.", PlotOrientation.VERTICAL, dataset, true,
						true, false, Color.magenta, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_P3_HRS_BAR_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_P3_HRS_BAR_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createBARChart(ReportingConstants.MTTR_P3_HRS_BAR_CHART_TITLE, "Assignment Group", "P3 Average Hrs.", PlotOrientation.VERTICAL, dataset, true,
						true, false, Color.yellow, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_P4_HRS_BAR_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_P4_HRS_BAR_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createBARChart(ReportingConstants.MTTR_P4_HRS_BAR_CHART_TITLE, "Assignment Group", "P4 Average Hrs.", PlotOrientation.VERTICAL, dataset, true,
						true, false, Color.cyan, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createLineChart(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE, "Priority", "Average Hrs.", PlotOrientation.VERTICAL, dataset,
						true, true, false, Color.cyan, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}
			if (fileNameNoUserId.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_PNG)) {
				// create the chart...
				dataset = mttrConverter.getDefaultCategoryDatasetFromMttrDataList(excelMTTRDTOList, ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE);
				final JFreeChart chart = GraphUtils.createLineChart(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE, "Priority", "Average Hrs.", PlotOrientation.VERTICAL, dataset,
						true, true, false, Color.cyan, Color.red);
				ChartUtilities.saveChartAsPNG(barChartFile, chart, 750, 400, info);
			}

		} catch (DateParsingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public String createDashBoardPPTX(HttpServletRequest request, HttpServletResponse response, UserDetails userDetails, Map userImageMap, DashboardSearch dos) throws NikeException {
		FileInputStream is = null;
		FileOutputStream out = null;
		String fileName = null;
		XMLSlideShow ppt = null;

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();

		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);

		try {
			is = new FileInputStream(appPath + "\\resources\\templates\\PES_MTTR_Reduction_Strategy.pptx");
			ppt = new XMLSlideShow(is);
			is.close();
			List userFileName = (List) userImageMap.get(LoginUtil.getLoggedInUser());
			for (int i = 0; i < userFileName.size(); i++) {
				String graphName = (String) userFileName.get(i);
				System.out.println("### : graphName: " + graphName);
				// reading an image
				File image = new File(appPath + "\\resources\\downloads\\" + graphName);

				// converting it into a byte array
				byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

				// adding the image to the presentation
				int idx = ppt.addPicture(picture, XSLFPictureData.PICTURE_TYPE_PNG);

				// creating a slide in it
				XSLFSlide slide = ppt.createSlide();

				// creating a slide with given picture on it
				XSLFPictureShape pic = slide.createPicture(idx);

				pic.setAnchor(new Rectangle(50, 50, 600, 350));

			}

		} catch (Exception e) {
			throw new NikeException(e, ErrorMessages.CREATE_OUTAGE_PPT_FILE_NOT_EXCEPTION);
		}

		DateFormat oldFormatter = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		DateFormat newFormatter = new SimpleDateFormat("MMMdd");

		try {
			String fromDate = newFormatter.format(oldFormatter.parse(dos.getFromDate()));
			String toDate = newFormatter.format(oldFormatter.parse(dos.getToDate()));
			fileName = "PES_MTTR_Reduction_Strategy_" + fromDate + "-" + toDate + ".pptx";
		} catch (ParseException e1) {
			fileName = "PES_MTTR_Reduction_Strategy.pptx";
		}

		// fileName = "PES_MTTR_Reduction_Strategy.pptx";
		try {
			out = new FileOutputStream(appPath + "\\resources\\downloads\\" + fileName);
			ppt.write(out);
			out.close();
		} catch (IOException e) {
			throw new NikeException(e, ErrorMessages.CREATE_OUTAGE_PPT_FILE_NOT_EXCEPTION);
		}
		return fileName;
	}
}
