/**
 * Constants defined for Nike Reporting Tool
 */
package com.nike.reporting.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sachin_Ainapure
 * 
 */
public final class ReportingConstants {

	public static final Character PLANNED_OUTAGE_TYPE_CHAR = 'P';
	public static final Character UNPLANNED_OUTAGE_TYPE_CHAR = 'U';
	public static final String PLANNED_OUTAGE_STRING = "Planned";
	public static final String UNPLANNED_OUTAGE_STRING = "UnPlanned";
	public static final String OUTAGE_REQUIRED_YES = "Yes";
	public static final String OUTAGE_REQUIRED_NO = "No";
	// DO NOT change value of ROLE_ADMIN
	public static final String ROLE_ADMIN = "Administrator";
	public static final String ROLE_MANAGER = "Manager";

	public static final String INC = "INC";
	public static final String DFCT = "DFCT";
	public static final String ENHC = "ENHC";

	public static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_NO_TIME = "MM/dd/yyyy";
	public static final String DATE_FORMAT_PATTERN_MONTH_YEAR = "MMMyyyy";

	public static final int DASHBOAD_DATE_DIFF = 7;

	// Chart Specific Constants
	public static final String MTBF_PER_DAYS_PNG_FILENAME = "MTBFPerDays.png";
	public static final String MTBF_PER_DAYS_CHART_TITLE = "Outage MTBF / Days";
	public static final String MTBF_DOMAIN_X_AXIS_LABEL = "Applications";
	public static final String MTBF_RANGE_Y_AXIS_LABEL = "MTBF Days";
	public static final String MTBF = "Mean Time Between Failure";

	public static final String OUTAGE_COUNT_BAR_CHART = "OutageCountBarChart.png";
	public static final String OUTAGE_COUNT_BAR_CHART_TITLE = "Outages Distribution - Application Wise";

	public static final List<String> IN_PROGRESS_STATUSES = Arrays.asList("Idea Review", "Estimation", "Development", "QA");
	public static final List<String> STABLE_STATUSES = Arrays.asList("Idea Implemented", "Results Updated");
	public static final String IN_PROGRESS = "In Progress";
	public static final String COMPLETE = "Complete";
	public static final String PROBLEM_MANAGEMENT_COUNT_BAR_CHART = "PMCountBarChart.png";
	public static final String PROBLEM_MANAGEMENT_COUNT_BAR_CHART_TITLE = "Problem Management Initiative Distribution";

	public static final String EFFORT_TRACKER_TEMPLATE_NAME = "New_Effort_Tracker.xlsx";
	public static final String EFFORT_TRACKER_TEMPLATE_ID = "NRT1.0";
	public static final String INCIDENT_MANAGEMENT = "Incident Management";
	public static final String DEFECT_MANAGEMENT = "Defect Management";
	public static final String PRIORITY_REGEX = "P[0-4]";

	public static final String WEEKLY_EFFORT_REPORT_NAME = "WeeklyEffortReport.xlsx";
	public static final String WEEKLY_APPLICATION_WISE_EFFORT_REPORT_NAME = "WeeklyApplicationWiseEffortReport.xlsx";
	public static final String WEEKLY_CAPABILITY_WISE_EFFORT_REPORT_NAME = "WeeklyCapabilityWiseEffortReport.xlsx";
	public static final double WEEKLY_EFFORT_MAX_LIMIT_HOURS = 16;
	public static final String WEEKLY_EFFORT_REPORT_QUERY = "SELECT "
			+ " E.CRTD_BY, WEEK(E.EFFORT_DATE, 3) WEEKNUMBER, SUM(E.EFFORT_HOURS) TOTALHOURS, DATE_ADD(E.EFFORT_DATE, INTERVAL(1-DAYOFWEEK(E.EFFORT_DATE)) DAY) WEEKSTART, DATE_ADD(E.EFFORT_DATE, INTERVAL(7-DAYOFWEEK(E.EFFORT_DATE)) DAY) WEEKEND, A.APP_NAME, C.CAPABILITY_DESC "
			+ " FROM TRN_EFFORT_TRACKER E, APPLICATION A, CAPABILITY C "
			+ " WHERE E.APP_ID = A.APP_ID AND A.CAPABILITY_ID = C.CAPABILITY_ID GROUP BY WEEK(E.EFFORT_DATE, 3), CRTD_BY ORDER BY EFFORT_DATE ";

	public static final String P1 = "1 - Critical";
	public static final String P2 = "2 - High";
	public static final String P3 = "3 - Medium";
	public static final String P4 = "4 - Low";

	public static final int P1_MTTR_VALUE = 8;
	public static final int P2_MTTR_VALUE = 16;
	public static final int P3_MTTR_VALUE = 80;
	public static final int P4_MTTR_VALUE = 160;

	public static final String MTTR_ANALYSIS_QUERY = " SELECT assignmentgroup, priority, " + " CASE " + "   WHEN priority = '1 - Critical' THEN "
			+ P1_MTTR_VALUE
			+ "   WHEN priority = '2 - High' THEN "
			+ P2_MTTR_VALUE
			+ "   WHEN priority = '3 - Medium' THEN "
			+ P3_MTTR_VALUE
			+ "   ELSE "
			+ P4_MTTR_VALUE
			+ " END pes_sla,"
			+ " ROUND(AVG(createToResolveInHours),2) average_hrs, COUNT(snowId) as total_incidents,"
			+ " CASE "
			+ "   WHEN priority = '1 - Critical' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P1_MTTR_VALUE
			+ "))"
			+ "   WHEN priority = '2 - High' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P2_MTTR_VALUE
			+ "))"
			+ "   WHEN priority = '3 - Medium' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P3_MTTR_VALUE
			+ "))"
			+ "   ELSE ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P4_MTTR_VALUE
			+ "))"
			+ " END as lt_sla,"
			+ " CASE "
			+ "   WHEN priority = '1 - Critical' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P1_MTTR_VALUE + "))"
			+ "   WHEN priority = '2 - High' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P2_MTTR_VALUE + "))"
			+ "   WHEN priority = '3 - Medium' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P3_MTTR_VALUE + "))" + "    ELSE ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P4_MTTR_VALUE + "))" + " END gt_sla," + " assignedTo " + " FROM trn_mttrdata a" + " GROUP BY assignmentgroup, priority ORDER BY assignmentgroup, priority ";

	public static final String MTTR_ANALYSIS_QUERY_BETWN_DATE = " SELECT assignmentgroup, priority, " + " CASE " + "   WHEN priority = '1 - Critical' THEN "
			+ P1_MTTR_VALUE
			+ "   WHEN priority = '2 - High' THEN "
			+ P2_MTTR_VALUE
			+ "   WHEN priority = '3 - Medium' THEN "
			+ P3_MTTR_VALUE
			+ "   ELSE "
			+ P4_MTTR_VALUE
			+ " END pes_sla,"
			+ " ROUND(AVG(createToResolveInHours),2) average_hrs, COUNT(snowId) as total_incidents,"
			+ " CASE "
			+ "   WHEN priority = '1 - Critical' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P1_MTTR_VALUE
			+ " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? "
			+ "))"
			+ "   WHEN priority = '2 - High' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P2_MTTR_VALUE
			+ " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? "
			+ "))"
			+ "   WHEN priority = '3 - Medium' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P3_MTTR_VALUE
			+ " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? "
			+ "))"
			+ "   ELSE ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours <= "
			+ P4_MTTR_VALUE
			+ " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? "
			+ "))"
			+ " END as lt_sla,"
			+ " CASE "
			+ "   WHEN priority = '1 - Critical' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P1_MTTR_VALUE + " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? " + "))"
			+ "   WHEN priority = '2 - High' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P2_MTTR_VALUE + " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? " + "))"
			+ "   WHEN priority = '3 - Medium' THEN ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > "
			+ P3_MTTR_VALUE + " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? " + "))"
			+ "    ELSE ((SELECT COUNT(snowid) FROM trn_mttrdata b WHERE b.assignmentgroup = a.assignmentgroup AND b.priority = a.priority AND b.createToResolveInHours > " + P4_MTTR_VALUE
			+ " AND DATE_FORMAT(b.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? " + "))" + " END gt_sla," + " assignedTo " + " FROM trn_mttrdata a"
			+ " WHERE DATE_FORMAT(a.closedDate_tmst, '%m/%d/%Y %H:%i:%s') between ? AND ? " + " GROUP BY assignmentgroup, priority ORDER BY assignmentgroup, priority ";

	public static final String MTTR_RAW_SAMPLE_TEMPLATE_IMAGE = "MTTR_UploadSample.jpg";
	public static final String MTTR_PER_ASSIGN_GROUP_PNG_FILENAME = "MTTRPerAssignmentGroup.png";
	public static final String MTTR_PRIORITY_WISE_PNG_FILENAME = "MTTRPriorityWise.png";
	public static final String MTTR_REASON_WISE_PNG_FILENAME = "MTTRReasonWise.png";
	public static final String MTTR_REASON_NOT_DEFINED = "Reason Not Yet Defined";
	public static final String MTTR_ANALYSIS_REPORT = "MTTRAnalysisReport.xlsx";
	public static final String MTTR_INCIDENTS_REPORT = "MTTRIncidentsReport.xlsx";
	public static final String MTTR_ASSIGN_GROUP_PIE_CHART_TITLE = "High MTTR Incidents - Assignment Group Wise";
	public static final String MTTR_ASSIGN_GROUP_PIE_PNG_FILENAME = "AssignmentGroupWiseIncidentBarChart.png";

	public static final String MTTR_PRIORITY_PIE_CHART_TITLE = "High MTTR Incidents - Priority Wise";
	public static final String MTTR_REASON_PIE_CHART_TITLE = "High MTTR Incidents - Reason Wise";
	public static final String MTTR_CLOSED_AND_HIGH_ASSIGN_GROUP_PIE_CHART_TITLE = "Closed & High MTTR Incidents - Assignment Group Wise";
	public static final String MTTR_P1_HRS_BAR_CHART_TITLE = "P1 MTTR Hrs - Assignment Group Wise";
	public static final String MTTR_P1_HRS_BAR_CHART_PNG = "MTTRP1Chart.png";
	public static final String MTTR_P2_HRS_BAR_CHART_TITLE = "P2 MTTR Hrs - Assignment Group Wise";
	public static final String MTTR_P2_HRS_BAR_CHART_PNG = "MTTRP2Chart.png";
	public static final String MTTR_P3_HRS_BAR_CHART_TITLE = "P3 MTTR Hrs - Assignment Group Wise";
	public static final String MTTR_P3_HRS_BAR_CHART_PNG = "MTTRP3Chart.png";
	public static final String MTTR_P4_HRS_BAR_CHART_TITLE = "P4 MTTR Hrs - Assignment Group Wise";
	public static final String MTTR_P4_HRS_BAR_CHART_PNG = "MTTRP4Chart.png";

	public static final String MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE = "MTTR Weekly Trend - Priority Wise";
	public static final String MTTR_WEEKLY_TREND_PRIORITY_HRS_LINE_CHART_PNG = "MTTRWeeklyTrendPriorityChart.png";
	public static final String MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_TITLE = "MTTR MONTHLY Trend - Priority Wise";
	public static final String MTTR_MONTHLY_TREND_PRIORITY_HRS_LINE_CHART_PNG = "MTTRMONTHLYTrendPriorityChart.png";

}
