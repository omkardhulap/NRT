package com.nike.reporting.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.nike.reporting.model.Application;
import com.nike.reporting.model.BenefitType;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Category;
import com.nike.reporting.model.Complexity;
import com.nike.reporting.model.PointOfFailure;
import com.nike.reporting.model.Priority;
import com.nike.reporting.model.ProblemManagementStatus;
import com.nike.reporting.model.ReportLookup;
import com.nike.reporting.model.Severity;

/**
 * 
 * Utility class.
 * 
 */
@Repository
public class CommonsUtil {

	private static SessionFactory sessionFactory;
	public static final List<Application> applicationsList = OutageUtil.getAllApplications();
	public static final List<Priority> priorityList = OutageUtil.getAllPriorities();
	public static final List<Complexity> complexityList = OutageUtil.getAllComplexities();
	public static final List<Category> categoryList = OutageUtil.getAllCategories();
	public static final List<Severity> severityList = OutageUtil.getAllSeverities();
	public static final List<Capability> capabilityList = OutageUtil.getAllCapabilities();
	public static final List<ReportLookup> reportLookupList = OutageUtil.getAllReportLookup();
	public static final List<PointOfFailure> pointOfFailureList = OutageUtil.getAllPointOfFailures();
	public static final List<ProblemManagementStatus> problemManagementStatusList = OutageUtil.getProblemManagementStatus();
	public static final List<BenefitType> problemManagementBenefitTypeList = OutageUtil.getProblemManagementBenefitTypes();
	public static final List<ReportLookup> mttrBreachLookupList = OutageUtil.getMTTRBreachLookup();

	public static void setSessionFactory(SessionFactory sf) {
		sessionFactory = sf;
	}

	public static Application getApplicationByName(String appName) {
		for (Application application : applicationsList) {
			if (application.getAppName().equalsIgnoreCase(appName)) {
				return application;
			}
		}
		return null;
	}

	public static Category getCategoryByDescription(String description) {
		for (Category category : categoryList) {
			if (category.getDescription().equalsIgnoreCase(description)) {
				return category;
			}
		}
		return null;
	}

	public static Priority getPriorityByDescription(String description) {
		for (Priority priority : priorityList) {
			if (priority.getDescription().equalsIgnoreCase(description)) {
				return priority;
			}
		}
		return null;
	}

	public static Complexity getComplexityByDescription(String description) {
		for (Complexity complexity : complexityList) {
			if (complexity.getDescription().equalsIgnoreCase(description)) {
				return complexity;
			}
		}
		return null;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isValidDate(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		DateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dateToValidate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getNextSunday(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SUNDAY - weekday;
		if (days < 0) {
			// this will usually be the case since Calendar.SUNDAY is the
			// smallest
			days += 7;
		}
		calendar.add(Calendar.DAY_OF_YEAR, days);
		DateFormat formatter2 = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
		Date date1 = new Date(calendar.getTimeInMillis());
		return formatter2.format(date1);
		// return new Date(calendar.getTimeInMillis());
	}

	public static Date getNextSundayAsDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SUNDAY - weekday;
		if (days < 0) {
			// this will usually be the case since Calendar.SUNDAY is the
			// smallest
			days += 7;
		}
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return new Date(calendar.getTimeInMillis());
	}

	public static String getMonthYear(String dateString, SimpleDateFormat sdf) throws ParseException {
		Date date1 = sdf.parse(dateString);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);

		String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		return monthNames[month] + year;
	}

	public static String getMonthYear(Date date, SimpleDateFormat sdf) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);

		String[] monthNames = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		System.out.println(date + ": " + monthNames[month] + year);
		return monthNames[month] + year;
	}

	public static List<String> getAllSundaysBetweenDates(Date start, Date end) {
		List<String> result = new ArrayList<String>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);

		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
		while (startCalendar.getTime().compareTo(endCalendar.getTime()) <= 0) {
			int weekday = startCalendar.get(Calendar.DAY_OF_WEEK);
			if (weekday == Calendar.SUNDAY) {
				// System.out.println(startCalendar.getTime());
				result.add(sdf.format(startCalendar.getTime()));
			}
			// add one day till both dates are equal
			startCalendar.add(Calendar.DATE, 1);
		}
		return result;
	}

	public static List<String> getAllMonthsBetweenDates(Date start, Date end) {
		List<String> result = new ArrayList<String>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_MONTH_YEAR);
		while (startCalendar.getTime().compareTo(endCalendar.getTime()) <= 0) {
			// System.out.println(startCalendar.getTime());
			result.add(sdf.format(startCalendar.getTime()));
			startCalendar.add(Calendar.MONTH, 1);// add one month
		}
		return result;
	}
}
