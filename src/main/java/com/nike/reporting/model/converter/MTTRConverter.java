package com.nike.reporting.model.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.data.category.DefaultCategoryDataset;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.MTTRAnalysis;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.dto.ExcelMTTRDTO;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.MapDateWiseComparator;
import com.nike.reporting.util.MapMonthYearWiseComparator;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */

public class MTTRConverter {

	public MTTRData convertFromExcelDTOToObject(ExcelMTTRDTO excelMTTRDTO) throws DateParsingException {
		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		SimpleDateFormat sdf_notime = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);

		MTTRData mttrData = new MTTRData();

		try {
			double startDate = (sdf.parse(sdf.format(excelMTTRDTO.getStartDate()))).getTime();
			double createdDate = (sdf.parse(sdf.format(excelMTTRDTO.getCreatedDate()))).getTime();
			double endDate = (sdf.parse(sdf.format(excelMTTRDTO.getEndDate()))).getTime();
			double createToResolveDuration = (startDate - createdDate) / (24 * 60 * 60 * 1000);
			double createToCloseCompleteDuration = (createdDate - endDate) / (24 * 60 * 60 * 1000);

			mttrData.setAssignedTo(excelMTTRDTO.getAssignedTo());
			mttrData.setAssignmentGroup(excelMTTRDTO.getAssignmentGroup());
			mttrData.setCategory(excelMTTRDTO.getCategory());
			mttrData.setClosedDate(sdf.parse(sdf.format(excelMTTRDTO.getClosedDate())));
			mttrData.setCreatedDate(sdf.parse(sdf.format(excelMTTRDTO.getCreatedDate())));
			mttrData.setCreateToCloseCompleteDuration(createToCloseCompleteDuration);
			mttrData.setCreateToCloseCompleteInHours(createToCloseCompleteDuration * 24);
			mttrData.setCreateToResolveDuration(createToResolveDuration);
			mttrData.setCreateToResolveInHours(createToResolveDuration * 24);
			mttrData.setDefinition(excelMTTRDTO.getDefinition());
			mttrData.setDuration(excelMTTRDTO.getDuration());
			mttrData.setEndDate(sdf.parse(sdf.format(excelMTTRDTO.getEndDate())));
			mttrData.setEscalation(excelMTTRDTO.getEscalation());
			mttrData.setPriority(excelMTTRDTO.getPriority());
			mttrData.setSnowId(excelMTTRDTO.getSnowId());
			mttrData.setStartDate(sdf.parse(sdf.format(excelMTTRDTO.getStartDate())));
			mttrData.setValue(excelMTTRDTO.getValue());
			// calculate next Sunday as weekend and store[if current is Sunday
			// then it will be this Sunday only]
			mttrData.setWeekEnding(CommonsUtil.getNextSundayAsDate(excelMTTRDTO.getClosedDate()));

			if (mttrData.getPriority().equalsIgnoreCase(ReportingConstants.P1)) {
				mttrData.setIsMTTRBreached(new Boolean(mttrData.getCreateToResolveInHours() > ReportingConstants.P1_MTTR_VALUE));
			} else if (mttrData.getPriority().equalsIgnoreCase(ReportingConstants.P2)) {
				mttrData.setIsMTTRBreached(new Boolean(mttrData.getCreateToResolveInHours() > ReportingConstants.P2_MTTR_VALUE));
			} else if (mttrData.getPriority().equalsIgnoreCase(ReportingConstants.P3)) {
				mttrData.setIsMTTRBreached(new Boolean(mttrData.getCreateToResolveInHours() > ReportingConstants.P3_MTTR_VALUE));
			} else if (mttrData.getPriority().equalsIgnoreCase(ReportingConstants.P4)) {
				mttrData.setIsMTTRBreached(new Boolean(mttrData.getCreateToResolveInHours() > ReportingConstants.P4_MTTR_VALUE));
			} else {
				mttrData.setIsMTTRBreached(new Boolean(false));
			}

			mttrData.setResolutionCode(excelMTTRDTO.getResolutionCode());
			mttrData.setResolutionNotes(excelMTTRDTO.getResolutionNotes());
			mttrData.setConfigurationItem(excelMTTRDTO.getConfigurationItem());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DateParsingException(e, "Upload Failed: " + e.getMessage());
		}

		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(excelMTTRDTO.getUploadedBy());
		auditInfo.setCreatedDate(excelMTTRDTO.getUploadedDate());
		auditInfo.setUpdatedBy(excelMTTRDTO.getEditedBy());
		auditInfo.setUpdatedDate(excelMTTRDTO.getEditedDate());

		mttrData.setAuditInfo(auditInfo);

		return mttrData;
	}

	public List<MTTRData> convertFromExcelDTOToObjectList(List<ExcelMTTRDTO> excelMTTRDTOList) throws DateParsingException {
		List<MTTRData> mttrDataList = new ArrayList<MTTRData>();
		if (excelMTTRDTOList != null && !excelMTTRDTOList.isEmpty()) {
			for (ExcelMTTRDTO excelMTTRDTO : excelMTTRDTOList) {
				mttrDataList.add(convertFromExcelDTOToObject(excelMTTRDTO));
			}
		}
		return mttrDataList;
	}

	public List<MTTRAnalysis> convertToMTTRAnalysisObject(List mttrDataFromSQL) {
		List<MTTRAnalysis> mttrAnalysisList = new ArrayList<MTTRAnalysis>();
		for (Object object : mttrDataFromSQL) {
			Map row = (Map) object;
			MTTRAnalysis mttrAnalysis = new MTTRAnalysis();
			mttrAnalysis.setAssignmentgroup(row.get("assignmentgroup").toString());

			float averageHrs = Float.parseFloat(row.get("average_hrs").toString());
			if (averageHrs > 0) {
				averageHrs = new BigDecimal(Float.toString(averageHrs)).setScale(2, RoundingMode.HALF_UP).floatValue();
			}

			mttrAnalysis.setAverage_hrs(averageHrs);
			// mttrAnalysis.setEffortId(Integer.parseInt(row.get("effort_id").toString()));
			mttrAnalysis.setGt_sla(Float.parseFloat(row.get("gt_sla").toString()));
			mttrAnalysis.setLt_sla(Float.parseFloat(row.get("lt_sla").toString()));
			mttrAnalysis.setTotal_incidents(Integer.parseInt(row.get("total_incidents").toString()));
			float perSLA = ((100 * mttrAnalysis.getLt_sla()) / mttrAnalysis.getTotal_incidents());
			if (perSLA > 0) {
				perSLA = new BigDecimal(Float.toString(perSLA)).setScale(2, RoundingMode.HALF_UP).floatValue();
			}
			mttrAnalysis.setPercentSLA_achieved(perSLA);
			mttrAnalysis.setPes_sla(Integer.parseInt(row.get("pes_sla").toString()));
			mttrAnalysis.setPriority(row.get("priority").toString());

			mttrAnalysisList.add(mttrAnalysis);
		}
		return mttrAnalysisList;
	}

	// TODO: Make it common to leverage for other charts
	public DefaultCategoryDataset getDefaultCategoryDatasetFromMttrDataList(List<ExcelMTTRDTO> excelMTTRDTOList, String chartName) throws ParseException {
		SimpleDateFormat sdf_noTime = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
		List<String> assignmentGroupList = new ArrayList<String>();
		Map<String, Integer> totalClosedMap = new HashMap<String, Integer>();
		Map<String, Integer> totalMTTRBreachMap = new HashMap<String, Integer>();

		Map<String, Double> totalP1HrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP2HrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP3HrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP4HrsMap = new HashMap<String, Double>();

		Map<String, Integer> totalP1CountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP2CountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP3CountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP4CountMap = new HashMap<String, Integer>();

		Map<String, Integer> totalP1WeeklyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP2WeeklyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP3WeeklyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP4WeeklyCountMap = new HashMap<String, Integer>();

		Map<String, Double> totalP1WeeklyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP2WeeklyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP3WeeklyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP4WeeklyHrsMap = new HashMap<String, Double>();

		Map<String, Integer> totalP1MonthlyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP2MonthlyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP3MonthlyCountMap = new HashMap<String, Integer>();
		Map<String, Integer> totalP4MonthlyCountMap = new HashMap<String, Integer>();

		Map<String, Double> totalP1MonthlyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP2MonthlyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP3MonthlyHrsMap = new HashMap<String, Double>();
		Map<String, Double> totalP4MonthlyHrsMap = new HashMap<String, Double>();

		// For weekly trend show data weekly for last 3 months only
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);// less 3 months
		Date beforeDate = c.getTime();

		List<String> allSundays = CommonsUtil.getAllSundaysBetweenDates(beforeDate, currentDate);
		for (Object element : allSundays) {
			String string = (String) element;
			totalP1WeeklyHrsMap.put(string, 0.00);
			totalP2WeeklyHrsMap.put(string, 0.00);
			totalP3WeeklyHrsMap.put(string, 0.00);
			totalP4WeeklyHrsMap.put(string, 0.00);
			totalP1WeeklyCountMap.put(string, 0);
			totalP2WeeklyCountMap.put(string, 0);
			totalP3WeeklyCountMap.put(string, 0);
			totalP4WeeklyCountMap.put(string, 0);
		}

		// For Monthly trend show data monthly for last 6 months only
		c = Calendar.getInstance();
		c.add(Calendar.MONTH, -6);// less 6 months
		beforeDate = c.getTime();
		List<String> allMonths = CommonsUtil.getAllMonthsBetweenDates(beforeDate, currentDate);
		for (Object element : allMonths) {
			String string = (String) element;
			totalP1MonthlyHrsMap.put(string, 0.00);
			totalP2MonthlyHrsMap.put(string, 0.00);
			totalP3MonthlyHrsMap.put(string, 0.00);
			totalP4MonthlyHrsMap.put(string, 0.00);
			totalP1MonthlyCountMap.put(string, 0);
			totalP2MonthlyCountMap.put(string, 0);
			totalP3MonthlyCountMap.put(string, 0);
			totalP4MonthlyCountMap.put(string, 0);
		}

		for (ExcelMTTRDTO excelMTTRDTO : excelMTTRDTOList) {
			if (totalClosedMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
				int value = Integer.parseInt(totalClosedMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
				totalClosedMap.put(excelMTTRDTO.getAssignmentGroup(), value);
			} else {
				totalClosedMap.put(excelMTTRDTO.getAssignmentGroup(), Integer.parseInt("1"));
				// populate assignmentGroupList here only
				assignmentGroupList.add(excelMTTRDTO.getAssignmentGroup());
			}

			if (excelMTTRDTO.getIsMTTRBreached()) {
				if (totalMTTRBreachMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
					int value = Integer.parseInt(totalMTTRBreachMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
					totalMTTRBreachMap.put(excelMTTRDTO.getAssignmentGroup(), value);
				} else {
					totalMTTRBreachMap.put(excelMTTRDTO.getAssignmentGroup(), Integer.parseInt("1"));
				}
			}

			if (excelMTTRDTO.getPriority().equalsIgnoreCase(ReportingConstants.P1)) {

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P1_HRS_BAR_CHART_TITLE)) {
					if (totalP1HrsMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
						double value = totalP1HrsMap.get(excelMTTRDTO.getAssignmentGroup()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP1HrsMap.put(excelMTTRDTO.getAssignmentGroup(), value);

						int val = Integer.parseInt(totalP1CountMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
						totalP1CountMap.put(excelMTTRDTO.getAssignmentGroup(), val);
					} else {
						totalP1HrsMap.put(excelMTTRDTO.getAssignmentGroup(), excelMTTRDTO.getCreateToResolveInHours());
						totalP1CountMap.put(excelMTTRDTO.getAssignmentGroup(), 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					if (totalP1WeeklyHrsMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						double value = totalP1WeeklyHrsMap.get(excelMTTRDTO.getWeekEndingString()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP1WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(), value);
					} else {
						// totalP1WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(),
						// excelMTTRDTO.getCreateToResolveInHours());
					}
					if (totalP1WeeklyCountMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						int weeklyVal = Integer.parseInt(totalP1WeeklyCountMap.get(excelMTTRDTO.getWeekEndingString()).toString()) + 1;
						totalP1WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(), weeklyVal);
					} else {
						// totalP1WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(),
						// 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					String monthYear = CommonsUtil.getMonthYear(excelMTTRDTO.getWeekEndingString(), sdf_noTime);
					if (totalP1MonthlyCountMap.containsKey(monthYear)) {
						int monthlyVal = Integer.parseInt(totalP1MonthlyCountMap.get(monthYear).toString()) + 1;
						totalP1MonthlyCountMap.put(monthYear, monthlyVal);
					} else {
						// totalP1MonthlyCountMap.put(monthYear, 1);
					}
					if (totalP1MonthlyHrsMap.containsKey(monthYear)) {
						double value = totalP1MonthlyHrsMap.get(monthYear) + excelMTTRDTO.getCreateToResolveInHours();
						totalP1MonthlyHrsMap.put(monthYear, value);
					} else {
						// totalP1MonthlyHrsMap.put(monthYear,
						// excelMTTRDTO.getCreateToResolveInHours());
					}
				}

			} else if (excelMTTRDTO.getPriority().equalsIgnoreCase(ReportingConstants.P2)) {

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P2_HRS_BAR_CHART_TITLE)) {
					if (totalP2HrsMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
						double value = totalP2HrsMap.get(excelMTTRDTO.getAssignmentGroup()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP2HrsMap.put(excelMTTRDTO.getAssignmentGroup(), value);

						int val = Integer.parseInt(totalP2CountMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
						totalP2CountMap.put(excelMTTRDTO.getAssignmentGroup(), val);
					} else {
						totalP2HrsMap.put(excelMTTRDTO.getAssignmentGroup(), excelMTTRDTO.getCreateToResolveInHours());
						totalP2CountMap.put(excelMTTRDTO.getAssignmentGroup(), 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					if (totalP2WeeklyHrsMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						double value = totalP2WeeklyHrsMap.get(excelMTTRDTO.getWeekEndingString()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP2WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(), value);
					} else {
						// totalP2WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(),
						// excelMTTRDTO.getCreateToResolveInHours());
					}

					if (totalP2WeeklyCountMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						int weeklyVal = Integer.parseInt(totalP2WeeklyCountMap.get(excelMTTRDTO.getWeekEndingString()).toString()) + 1;
						totalP2WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(), weeklyVal);
					} else {
						// totalP2WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(),
						// 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					String monthYear = CommonsUtil.getMonthYear(excelMTTRDTO.getWeekEndingString(), sdf_noTime);
					if (totalP2MonthlyCountMap.containsKey(monthYear)) {
						int monthlyVal = Integer.parseInt(totalP2MonthlyCountMap.get(monthYear).toString()) + 1;
						totalP2MonthlyCountMap.put(monthYear, monthlyVal);
					} else {
						// totalP2MonthlyCountMap.put(monthYear, 1);
					}
					if (totalP2MonthlyHrsMap.containsKey(monthYear)) {
						double value = totalP2MonthlyHrsMap.get(monthYear) + excelMTTRDTO.getCreateToResolveInHours();
						totalP2MonthlyHrsMap.put(monthYear, value);
					} else {
						// totalP2MonthlyHrsMap.put(monthYear,
						// excelMTTRDTO.getCreateToResolveInHours());
					}
				}

			} else if (excelMTTRDTO.getPriority().equalsIgnoreCase(ReportingConstants.P3)) {

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P3_HRS_BAR_CHART_TITLE)) {
					if (totalP3HrsMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
						double value = totalP3HrsMap.get(excelMTTRDTO.getAssignmentGroup()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP3HrsMap.put(excelMTTRDTO.getAssignmentGroup(), value);

						int val = Integer.parseInt(totalP3CountMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
						totalP3CountMap.put(excelMTTRDTO.getAssignmentGroup(), val);
					} else {
						totalP3HrsMap.put(excelMTTRDTO.getAssignmentGroup(), excelMTTRDTO.getCreateToResolveInHours());
						totalP3CountMap.put(excelMTTRDTO.getAssignmentGroup(), 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					if (totalP3WeeklyHrsMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						double value = totalP3WeeklyHrsMap.get(excelMTTRDTO.getWeekEndingString()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP3WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(), value);
					} else {
						// totalP3WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(),
						// excelMTTRDTO.getCreateToResolveInHours());
					}

					if (totalP3WeeklyCountMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						int weeklyVal = Integer.parseInt(totalP3WeeklyCountMap.get(excelMTTRDTO.getWeekEndingString()).toString()) + 1;
						totalP3WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(), weeklyVal);
					} else {
						// totalP3WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(),
						// 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					String monthYear = CommonsUtil.getMonthYear(excelMTTRDTO.getWeekEndingString(), sdf_noTime);
					if (totalP3MonthlyCountMap.containsKey(monthYear)) {
						int monthlyVal = Integer.parseInt(totalP3MonthlyCountMap.get(monthYear).toString()) + 1;
						totalP3MonthlyCountMap.put(monthYear, monthlyVal);
					} else {
						// totalP3MonthlyCountMap.put(monthYear, 1);
					}
					if (totalP3MonthlyHrsMap.containsKey(monthYear)) {
						double value = totalP3MonthlyHrsMap.get(monthYear) + excelMTTRDTO.getCreateToResolveInHours();
						totalP3MonthlyHrsMap.put(monthYear, value);
					} else {
						// totalP3MonthlyHrsMap.put(monthYear,
						// excelMTTRDTO.getCreateToResolveInHours());
					}
				}

			} else if (excelMTTRDTO.getPriority().equalsIgnoreCase(ReportingConstants.P4)) {

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P4_HRS_BAR_CHART_TITLE)) {
					if (totalP4HrsMap.containsKey(excelMTTRDTO.getAssignmentGroup())) {
						double value = totalP4HrsMap.get(excelMTTRDTO.getAssignmentGroup()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP4HrsMap.put(excelMTTRDTO.getAssignmentGroup(), value);

						int val = Integer.parseInt(totalP4CountMap.get(excelMTTRDTO.getAssignmentGroup()).toString()) + 1;
						totalP4CountMap.put(excelMTTRDTO.getAssignmentGroup(), val);
					} else {
						totalP4HrsMap.put(excelMTTRDTO.getAssignmentGroup(), excelMTTRDTO.getCreateToResolveInHours());
						totalP4CountMap.put(excelMTTRDTO.getAssignmentGroup(), 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					if (totalP4WeeklyHrsMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						double value = totalP4WeeklyHrsMap.get(excelMTTRDTO.getWeekEndingString()) + excelMTTRDTO.getCreateToResolveInHours();
						totalP4WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(), value);
					} else {
						// totalP4WeeklyHrsMap.put(excelMTTRDTO.getWeekEndingString(),
						// excelMTTRDTO.getCreateToResolveInHours());
					}

					if (totalP4WeeklyCountMap.containsKey(excelMTTRDTO.getWeekEndingString())) {
						int weeklyVal = Integer.parseInt(totalP4WeeklyCountMap.get(excelMTTRDTO.getWeekEndingString()).toString()) + 1;
						totalP4WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(), weeklyVal);
					} else {
						// totalP4WeeklyCountMap.put(excelMTTRDTO.getWeekEndingString(),
						// 1);
					}
				}

				if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
					String monthYear = CommonsUtil.getMonthYear(excelMTTRDTO.getWeekEndingString(), sdf_noTime);
					if (totalP4MonthlyCountMap.containsKey(monthYear)) {
						int monthlyVal = Integer.parseInt(totalP4MonthlyCountMap.get(monthYear).toString()) + 1;
						totalP4MonthlyCountMap.put(monthYear, monthlyVal);
					} else {
						// totalP4MonthlyCountMap.put(monthYear, 1);
					}
					if (totalP4MonthlyHrsMap.containsKey(monthYear)) {
						double value = totalP4MonthlyHrsMap.get(monthYear) + excelMTTRDTO.getCreateToResolveInHours();
						totalP4MonthlyHrsMap.put(monthYear, value);
					} else {
						// totalP4MonthlyHrsMap.put(monthYear,
						// excelMTTRDTO.getCreateToResolveInHours());
					}
				}
			}
		}

		// ----
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_CLOSED_AND_HIGH_ASSIGN_GROUP_PIE_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String assignmentGroupName : totalClosedMap.keySet()) {
				dataset.addValue(totalClosedMap.get(assignmentGroupName), "Total Closed", assignmentGroupName.replace("Frontline - ", ""));
			}
			for (String assignmentGroupName : totalMTTRBreachMap.keySet()) {
				dataset.addValue(totalMTTRBreachMap.get(assignmentGroupName), "Total High MTTR", assignmentGroupName.replace("Frontline - ", ""));
				float percent = (totalMTTRBreachMap.get(assignmentGroupName) * 100) / totalClosedMap.get(assignmentGroupName);
				dataset.addValue(percent, "% High MTTR", assignmentGroupName.replace("Frontline - ", ""));
			}
			// ---
			return dataset;
		}
		// ----
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P1_HRS_BAR_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String assignmentGroupName : totalP1HrsMap.keySet()) {
				dataset.addValue(totalP1HrsMap.get(assignmentGroupName) / totalP1CountMap.get(assignmentGroupName), "P1 MTTR Hrs.", assignmentGroupName.replace("Frontline - ", ""));
			}
			return dataset;
		}
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P2_HRS_BAR_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String assignmentGroupName : totalP2HrsMap.keySet()) {
				dataset.addValue(totalP2HrsMap.get(assignmentGroupName) / totalP2CountMap.get(assignmentGroupName), "P2 MTTR Hrs.", assignmentGroupName.replace("Frontline - ", ""));
			}
			return dataset;
		}
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P3_HRS_BAR_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String assignmentGroupName : totalP3HrsMap.keySet()) {
				dataset.addValue(totalP3HrsMap.get(assignmentGroupName) / totalP3CountMap.get(assignmentGroupName), "P3 MTTR Hrs.", assignmentGroupName.replace("Frontline - ", ""));
			}
			return dataset;
		}
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_P4_HRS_BAR_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (String assignmentGroupName : totalP4HrsMap.keySet()) {
				// System.out.println("### " + assignmentGroupName + ": " +
				// totalP4HrsMap.get(assignmentGroupName) + " - " +
				// totalP4CountMap.get(assignmentGroupName));
				dataset.addValue(totalP4HrsMap.get(assignmentGroupName) / totalP4CountMap.get(assignmentGroupName), "P4 MTTR Hrs.", assignmentGroupName.replace("Frontline - ", ""));
			}
			return dataset;
		}
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			Map treeMap = new TreeMap(new MapDateWiseComparator(new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME)));

			treeMap.putAll(totalP1WeeklyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP1WeeklyCountMap.get(key), "P1", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP2WeeklyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP2WeeklyCountMap.get(key), "P2", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP3WeeklyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP3WeeklyCountMap.get(key), "P3", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP4WeeklyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP4WeeklyCountMap.get(key), "P4", key);
			}

			return dataset;
		}
		if (chartName.equalsIgnoreCase(ReportingConstants.MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE)) {
			// create the dataset...
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			Map treeMap = new TreeMap(new MapMonthYearWiseComparator(new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_MONTH_YEAR)));

			treeMap.putAll(totalP1MonthlyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP1MonthlyCountMap.get(key), "P1", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP2MonthlyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP2MonthlyCountMap.get(key), "P2", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP3MonthlyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP3MonthlyCountMap.get(key), "P3", key);
			}

			treeMap.clear();
			treeMap.putAll(totalP4MonthlyHrsMap);
			for (Iterator i = treeMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				Double value = (Double) treeMap.get(key);
				// System.out.println("@@@: " + key + " = " + value);
				dataset.addValue(value / totalP4MonthlyCountMap.get(key), "P4", key);
			}

			return dataset;
		}
		// ----
		return new DefaultCategoryDataset();
	}

	public List<ExcelMTTRDTO> convertToExcelMTTRDTOObject(List<MTTRData> mttrDataList) throws ParseException {
		List<ExcelMTTRDTO> excelMTTRDTOList = new ArrayList<ExcelMTTRDTO>();
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		SimpleDateFormat sdf_notime = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
		if (mttrDataList != null && !mttrDataList.isEmpty()) {
			for (MTTRData mttrData : mttrDataList) {
				ExcelMTTRDTO excelMTTRDTO = new ExcelMTTRDTO();

				excelMTTRDTO.setAssignedTo(mttrData.getAssignedTo());
				excelMTTRDTO.setAssignmentGroup(mttrData.getAssignmentGroup());
				excelMTTRDTO.setCategory(mttrData.getCategory());
				excelMTTRDTO.setClosedDate(mttrData.getClosedDate());
				excelMTTRDTO.setCreatedDate(mttrData.getCreatedDate());
				excelMTTRDTO.setCreateToCloseCompleteDuration(mttrData.getCreateToCloseCompleteDuration());
				excelMTTRDTO.setCreateToCloseCompleteInHours(mttrData.getCreateToCloseCompleteInHours());
				excelMTTRDTO.setCreateToResolveDuration(mttrData.getCreateToResolveDuration());
				double createToResolveinHrs = mttrData.getCreateToResolveInHours();
				if (createToResolveinHrs > 0) {
					createToResolveinHrs = new BigDecimal(Double.toString(createToResolveinHrs)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
				excelMTTRDTO.setCreateToResolveInHours(createToResolveinHrs);
				excelMTTRDTO.setDefinition(mttrData.getDefinition());
				excelMTTRDTO.setDuration(mttrData.getDuration());
				excelMTTRDTO.setEditedBy(mttrData.getAuditInfo().getUpdatedBy());
				excelMTTRDTO.setEditedDate(mttrData.getAuditInfo().getUpdatedDate());
				excelMTTRDTO.setEndDate(mttrData.getEndDate());
				excelMTTRDTO.setEscalation(mttrData.getEscalation());
				excelMTTRDTO.setIsMTTRBreached(mttrData.getIsMTTRBreached());
				if (null == mttrData.getMttrBreachReason() || mttrData.getMttrBreachReason().trim().length() == 0) {
					excelMTTRDTO.setMttrBreachReason(ReportingConstants.MTTR_REASON_NOT_DEFINED);
				} else {
					excelMTTRDTO.setMttrBreachReason(mttrData.getMttrBreachReason());
				}

				excelMTTRDTO.setMttrId(mttrData.getId());
				// double perSLA = Math.round(((100 * mttrAnalysis.getLt_sla())
				// /
				// mttrAnalysis.getTotal_incidents()) * 100.0) / 100.0;
				// excelMTTRDTO.setPercentMttrSlaAchieved();
				excelMTTRDTO.setPriority(mttrData.getPriority());
				excelMTTRDTO.setSnowId(mttrData.getSnowId());
				excelMTTRDTO.setStartDate(mttrData.getStartDate());
				excelMTTRDTO.setUploadedBy(mttrData.getAuditInfo().getCreatedBy());
				excelMTTRDTO.setUploadedDate(mttrData.getAuditInfo().getCreatedDate());
				excelMTTRDTO.setValue(mttrData.getValue());
				excelMTTRDTO.setRemarks(mttrData.getRemarks());

				// excelMTTRDTO.setWeekEnding(mttrData.getWeekEnding());
				excelMTTRDTO.setWeekEndingString(CommonsUtil.getNextSunday(mttrData.getClosedDate()));

				excelMTTRDTO.setResolutionCode(mttrData.getResolutionCode());
				excelMTTRDTO.setResolutionNotes(mttrData.getResolutionNotes());
				excelMTTRDTO.setConfigurationItem(mttrData.getConfigurationItem());

				excelMTTRDTOList.add(excelMTTRDTO);
			}
		}
		return excelMTTRDTOList;
	}

	public ExcelMTTRDTO populateMTTRForUI(MTTRData mttrData) {
		ExcelMTTRDTO excelMTTRDTO = new ExcelMTTRDTO();
		if (mttrData != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			excelMTTRDTO.setMttrId(mttrData.getId());
			excelMTTRDTO.setSnowId(mttrData.getSnowId());
			excelMTTRDTO.setPriority(mttrData.getPriority());
			excelMTTRDTO.setAssignmentGroup(mttrData.getAssignmentGroup());
			excelMTTRDTO.setAssignedTo(mttrData.getAssignedTo());
			double createToResolveinHrs = mttrData.getCreateToResolveInHours();
			if (createToResolveinHrs > 0) {
				createToResolveinHrs = new BigDecimal(Double.toString(createToResolveinHrs)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}
			excelMTTRDTO.setCreateToResolveInHours(createToResolveinHrs);

			if (mttrData.getMttrBreachReason() == null || mttrData.getMttrBreachReason().trim().length() == 0) {
				excelMTTRDTO.setMttrBreachReason(ReportingConstants.MTTR_REASON_NOT_DEFINED);
			} else {
				excelMTTRDTO.setMttrBreachReason(mttrData.getMttrBreachReason());
			}

			excelMTTRDTO.setRemarks(mttrData.getRemarks());

			excelMTTRDTO.setResolutionCode(mttrData.getResolutionCode());
			excelMTTRDTO.setResolutionNotes(mttrData.getResolutionNotes());
			excelMTTRDTO.setConfigurationItem(mttrData.getConfigurationItem());

			if (mttrData.getAuditInfo() != null) {
				excelMTTRDTO.setEditedBy(mttrData.getAuditInfo().getUpdatedBy());
				excelMTTRDTO.setEditedDate(mttrData.getAuditInfo().getUpdatedDate());
				excelMTTRDTO.setUploadedBy(mttrData.getAuditInfo().getCreatedBy());
				excelMTTRDTO.setUploadedDate(mttrData.getAuditInfo().getCreatedDate());
			}
			return excelMTTRDTO;
		}
		return null;
	}

	private static void printMap1(Map<String, Double> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	private static void printMap2(Map<String, Integer> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	private static void printMap3(Map<Date, Double> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}
}
