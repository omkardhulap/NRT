/**
 * <h1>Utility Class for Outage</h1>
 * OutageUtil.java exposes convertor method for Outage, PlannedOutageUI and UnplannedOutageUI. 
 * When user submits the request for planned outage, plannedOutageUI is converted
 * into Outage object using these class methods.
 * When user submits the request for unplanned outage, UnplannedOutageUI is converted
 * into Outage object using these class methods.
 * When user edits outage, outage is loaded from the database and converted into UI objects
 * using methods exposed by OutageUtil.java.
 * NRT application uses different controls such as Multiselect for applications, radio button for
 * report look up and dropdown for priority. Values for all these form controls are fetched from
 * the database by OutageUtil.java class methods.
 * @author  Mital Gadoya
 * @version 1.0
 * @since   2015-01-01
 */
package com.nike.reporting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Application;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.BenefitType;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Category;
import com.nike.reporting.model.Complexity;
import com.nike.reporting.model.Defect;
import com.nike.reporting.model.Enhancement;
import com.nike.reporting.model.Incident;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.PointOfFailure;
import com.nike.reporting.model.Priority;
import com.nike.reporting.model.ProblemManagementStatus;
import com.nike.reporting.model.ReportLookup;
import com.nike.reporting.model.Severity;
import com.nike.reporting.ui.PlannedOutageUI;
import com.nike.reporting.ui.UnplannedOutageUI;

@Repository
public class OutageUtil {

	private static final Logger logger = LoggerFactory.getLogger(OutageUtil.class);

	private static SessionFactory sessionFactory;

	public static void setSessionFactory(SessionFactory sf) {
		sessionFactory = sf;
	}

	public static Outage populatePlannedOutage(PlannedOutageUI plannedOutage, Outage o) throws DateParsingException {
		if (plannedOutage != null) {
			if (null == o) {
				o = new Outage();
			}
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			String splitString = plannedOutage.getSnowId();
			String delims = ",";
			String[] tokens = splitString.split(delims);
			List<Incident> tempIncidentList = null;
			List<Enhancement> tempEnhancementList = null;
			List<Defect> tempDefectList = null;
			for (String token : tokens) {
				if (token.startsWith(ReportingConstants.INC)) {
					if (tempIncidentList == null) {
						tempIncidentList = new ArrayList<Incident>();
					}
					Incident incident = new Incident();
					incident.setSnowId(token);
					tempIncidentList.add(incident);

				}
				if (token.startsWith(ReportingConstants.DFCT)) {

					if (tempDefectList == null) {
						tempDefectList = new ArrayList<Defect>();
					}
					Defect defect = new Defect();
					defect.setSnowId(token);
					tempDefectList.add(defect);

				}
				if (token.startsWith(ReportingConstants.ENHC)) {
					if (tempEnhancementList == null) {
						tempEnhancementList = new ArrayList<Enhancement>();
					}
					Enhancement enhancement = new Enhancement();
					enhancement.setSnowId(token);
					tempEnhancementList.add(enhancement);
				}

			}

			if (tempIncidentList != null) {
				o.setIncidentList(tempIncidentList);
			}
			if (tempDefectList != null) {
				o.setDefectList(tempDefectList);
			}
			if (tempEnhancementList != null) {
				o.setEnhancementList(tempEnhancementList);
			}
			o.setId(plannedOutage.getId());
			o.setOutageType(ReportingConstants.PLANNED_OUTAGE_TYPE_CHAR);
			o.setApplications(getApplicationsByNames(plannedOutage.getApplications()));
			o.setOutageRequired(plannedOutage.getOutageRequired());
			o.setDescription(plannedOutage.getDescription());
			try {
				o.setDeploymentStartDate(sdf.parse(plannedOutage.getDeploymentStartDate()));
				o.setDeploymentEndDate(sdf.parse(plannedOutage.getDeploymentEndDate()));
				o.setApprovedBy(plannedOutage.getApprovedBy());
				o.setApprovalDate(sdf.parse(plannedOutage.getApprovalDate()));
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
			o.setScope(plannedOutage.getScope());

			/*
			 * Add audit info for the outage.
			 */
			AuditInfo auditInfo = null;
			auditInfo = new AuditInfo();
			if (o.getAuditInfo() == null || o.getAuditInfo().getCreatedBy() == null) {
				auditInfo.setCreatedBy(plannedOutage.getUserId());
				auditInfo.setCreatedDate(new Date());
			} else {
				auditInfo = o.getAuditInfo();
				if (auditInfo == null) {
					auditInfo = new AuditInfo();
				}
			}
			auditInfo.setUpdatedBy(plannedOutage.getUserId());
			auditInfo.setUpdatedDate(new Date());
			o.setAuditInfo(auditInfo);

			return o;
		}
		return null;
	}

	public static Outage populateUnplannedOutage(UnplannedOutageUI unplannedOutage, Outage o) throws DateParsingException {
		if (unplannedOutage != null) {
			if (null == o) {
				o = new Outage();
			}
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			Incident inc = new Incident();
			inc.setSnowId(unplannedOutage.getSnowId());
			inc.setReportedBy(unplannedOutage.getReportedBy());
			try {
				inc.setReportedOn(sdf.parse(unplannedOutage.getReportedOn()));
				List<Incident> inList = new ArrayList<Incident>();
				inList.add(inc);
				o.setIncidentList(inList);
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}

			o.setStOwner(unplannedOutage.getStOwner());
			o.setId(unplannedOutage.getId());
			o.setOutageType(ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR);
			o.setDescription(unplannedOutage.getDescription());
			o.setPriority(unplannedOutage.getPriority());
			o.setBusinessAffected(unplannedOutage.getBusinessAffected());
			o.setDueTo(unplannedOutage.getDueTo());
			o.setExecutiveSummary(unplannedOutage.getExecutiveSummary());
			o.setTechnicalIssues(unplannedOutage.getTechnicalIssues());
			o.setResolution(unplannedOutage.getResolution());
			o.setRootCause(unplannedOutage.getRootCause());
			o.setVendorAccountable(unplannedOutage.getVendorAccountable());
			o.setAarOwner(unplannedOutage.getAarOwner());
			o.setApplications(getApplicationsByNames(unplannedOutage.getApplications()));
			o.setDatabase(unplannedOutage.getDatabase());
			o.setPlatform(unplannedOutage.getPlatform());
			o.setOutageRequired(true);
			o.setPointOfFailures(getPointOfFailureByNames(unplannedOutage.getPointOfFailures()));
			o.setVendorAccountableName(unplannedOutage.getVendorAccountableName());
			o.setSeverity(unplannedOutage.getSeverity());

			try {
				o.setAarDate(sdf.parse(unplannedOutage.getAarDate()));
				o.setDeploymentStartDate(sdf.parse(unplannedOutage.getDeploymentStartDate()));
				o.setDeploymentEndDate(sdf.parse(unplannedOutage.getDeploymentEndDate()));
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}

			/*
			 * Add audit info for the outage.
			 */
			AuditInfo auditInfo = null;
			auditInfo = new AuditInfo();
			if (o.getAuditInfo() == null || o.getAuditInfo().getCreatedBy() == null) {
				auditInfo.setCreatedBy(unplannedOutage.getUserId());
				auditInfo.setCreatedDate(new Date());
			} else {
				auditInfo = o.getAuditInfo();
				if (auditInfo == null) {
					auditInfo = new AuditInfo();
				}
			}
			auditInfo.setUpdatedBy(unplannedOutage.getUserId());
			auditInfo.setUpdatedDate(new Date());
			o.setAuditInfo(auditInfo);
			return o;
		}
		return null;
	}

	public static PlannedOutageUI populatePlannedOutageForUI(Outage o) {

		if (o != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			PlannedOutageUI pUI = new PlannedOutageUI();
			pUI.setId(o.getId());
			pUI.setOutageType(ReportingConstants.PLANNED_OUTAGE_TYPE_CHAR);
			pUI.setApplications(getListofStringFromApplications(o.getApplications()));
			pUI.setOutageRequired(o.getOutageRequired());
			pUI.setDescription(o.getDescription());
			pUI.setDeploymentStartDate(sdf.format(o.getDeploymentStartDate()));
			pUI.setDeploymentEndDate(sdf.format(o.getDeploymentEndDate()));
			pUI.setApprovedBy(o.getApprovedBy());
			pUI.setApprovalDate(sdf.format(o.getApprovalDate()));
			pUI.setScope(o.getScope());
			StringBuilder sb = new StringBuilder();
			if (o.getIncidentList() != null) {
				for (Incident inc : o.getIncidentList()) {
					sb.append(inc.getSnowId()).append(",");
				}
			}
			if (o.getDefectList() != null) {
				for (Defect def : o.getDefectList()) {
					sb.append(def.getSnowId()).append(",");
				}
			}
			if (o.getEnhancementList() != null) {
				for (Enhancement enh : o.getEnhancementList()) {
					sb.append(enh.getSnowId()).append(",");
				}
			}

			if (sb.length() > 0) {
				pUI.setSnowId(sb.substring(0, sb.length() - 1));
			}

			return pUI;
		}
		return null;
	}

	public static UnplannedOutageUI populateUnplannedOutageForUI(Outage o) {

		if (o != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			UnplannedOutageUI upUI = new UnplannedOutageUI();
			upUI.setId(o.getId());
			upUI.setOutageType(ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR);
			upUI.setDescription(o.getDescription());
			upUI.setPriority(o.getPriority());
			upUI.setStOwner(o.getStOwner());
			upUI.setBusinessAffected(o.getBusinessAffected());
			upUI.setDueTo(o.getDueTo());
			upUI.setExecutiveSummary(o.getExecutiveSummary());
			upUI.setTechnicalIssues(o.getTechnicalIssues());
			upUI.setResolution(o.getResolution());
			upUI.setRootCause(o.getRootCause());
			upUI.setVendorAccountable(o.getVendorAccountable());
			upUI.setAarOwner(o.getAarOwner());
			upUI.setAarDate(sdf.format(o.getAarDate()));
			upUI.setApplications(getListofStringFromApplications(o.getApplications()));
			upUI.setDatabase(o.getDatabase());
			upUI.setPlatform(o.getPlatform());
			upUI.setDeploymentStartDate(sdf.format(o.getDeploymentStartDate()));
			upUI.setDeploymentEndDate(sdf.format(o.getDeploymentEndDate()));
			upUI.setSnowId(o.getIncidentList().get(0).getSnowId());
			upUI.setReportedBy(o.getIncidentList().get(0).getReportedBy());
			upUI.setReportedOn(sdf.format(o.getIncidentList().get(0).getReportedOn()));
			upUI.setSeverity(o.getSeverity());
			upUI.setVendorAccountableName(o.getVendorAccountableName());
			upUI.setPointOfFailures(getListofStringFromPointOfFailures(o.getPointOfFailures()));
			return upUI;
		}
		return null;
	}

	public static Application getApplicationById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Application> getApplicationsByNames(List<String> appNames) {
		Session session = sessionFactory.openSession();
		List<Application> applicationList = null;
		Criteria cr = session.createCriteria(Application.class);
		if (appNames != null && appNames.size() > 0) {
			cr.add(Restrictions.in("appName", appNames.toArray()));
			applicationList = cr.list();
		}
		session.close();
		return applicationList;
	}

	public static List<PointOfFailure> getPointOfFailureByNames(List<String> pointOfFailureNames) {
		Session session = sessionFactory.openSession();
		List<PointOfFailure> pointOfFailureList = null;
		Criteria cr = session.createCriteria(PointOfFailure.class);
		if (pointOfFailureNames != null && pointOfFailureNames.size() > 0) {
			cr.add(Restrictions.in("pointOfFailureDesc", pointOfFailureNames.toArray()));
			pointOfFailureList = cr.list();
		}
		session.close();
		return pointOfFailureList;
	}

	@SuppressWarnings("unchecked")
	public static List<ReportLookup> getAllReportLookup() {
		Session session = sessionFactory.openSession();
		List<ReportLookup> reportLookupList = session.createQuery("from ReportLookup where value in ('CAB','CCB','N/A')").list();
		session.close();
		return reportLookupList;
	}

	@SuppressWarnings("unchecked")
	public static List<ReportLookup> getMTTRBreachLookup() {
		Session session = sessionFactory.openSession();
		List<ReportLookup> reportLookupList = session.createQuery("from ReportLookup where name in ('MTTR_REASON')").list();
		session.close();
		return reportLookupList;
	}

	public static List<String> getListofStringFromApplications(List<Application> appList) {
		List<String> appStringList = new ArrayList<String>();
		for (Application a : appList) {
			appStringList.add(a.getAppName());
		}
		return appStringList;

	}

	public static List<String> getListofStringFromPointOfFailures(List<PointOfFailure> pofList) {
		List<String> pofStringList = new ArrayList<String>();
		for (PointOfFailure pof : pofList) {
			pofStringList.add(pof.getPointOfFailureDesc());
		}
		return pofStringList;

	}

	public static List<PointOfFailure> getAllPointOfFailures() {
		Session session = sessionFactory.openSession();
		List<PointOfFailure> pofList = session.createQuery("from PointOfFailure").list();
		session.close();
		return pofList;
	}

	public static List<Application> getAllApplications() {

		Session session = sessionFactory.openSession();
		List<Application> appList = session.createQuery("from Application").list();
		session.close();
		return appList;

	}

	public static List<Priority> getAllPriorities() {
		Session session = sessionFactory.openSession();
		List<Priority> priorityList = session.createQuery("from Priority").list();
		session.close();
		return priorityList;
	}

	public static List<Complexity> getAllComplexities() {
		Session session = sessionFactory.openSession();
		List<Complexity> complexityList = session.createQuery("from Complexity").list();
		session.close();
		return complexityList;
	}

	public static List<Category> getAllCategories() {
		Session session = sessionFactory.openSession();
		List<Category> categoryList = session.createQuery("from Category").list();
		session.close();
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	public static List<Severity> getAllSeverities() {
		Session session = sessionFactory.openSession();
		List<Severity> severityList = session.createQuery("from Severity").list();
		session.close();
		return severityList;
	}

	public static List<Capability> getAllCapabilities() {
		Session session = sessionFactory.openSession();
		List<Capability> capList = session.createQuery("from Capability").list();
		session.close();
		return capList;
	}

	@SuppressWarnings("unchecked")
	public static List<ProblemManagementStatus> getProblemManagementStatus() {
		Session session = sessionFactory.openSession();
		List<ProblemManagementStatus> statusList = session.createQuery("from ProblemManagementStatus").list();
		session.close();
		return statusList;
	}

	@SuppressWarnings("unchecked")
	public static List<BenefitType> getProblemManagementBenefitTypes() {
		Session session = sessionFactory.openSession();
		List<BenefitType> benefitTypeList = session.createQuery("from BenefitType").list();
		session.close();
		return benefitTypeList;
	}

}
