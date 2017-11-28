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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Application;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.BenefitType;
import com.nike.reporting.model.Priority;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.ui.ProblemManagementUI;

/**
 * 
 * Utility class for Problem Management.
 * 
 */
@Repository
public class ProblemManagementUtil {

	private static SessionFactory sessionFactory;

	@Autowired
	public static void setSessionFactory(SessionFactory sf) {
		sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	public static List<Application> getAllApplications() {

		Session session = sessionFactory.openSession();
		List<Application> appList = session.createQuery("from Application").list();
		session.close();
		return appList;

	}

	@SuppressWarnings("unchecked")
	public static List<Priority> getAllPriorities() {
		Session session = sessionFactory.openSession();
		List<Priority> priorityList = session.createQuery("from Priority").list();
		session.close();
		return priorityList;
	}

	public static ProblemManagement populateProblemManagement(ProblemManagementUI pUI, ProblemManagement pm) throws DateParsingException {
		if (null == pm) {
			pm = new ProblemManagement();
		}
		pm.setInnovationTitle(pUI.getInnovationTitle());
		pm.setProblemStatement(pUI.getProblemStatement());
		pm.setSolution(pUI.getSolution());
		pm.setApplication(pUI.getApplication());
		pm.setItBenefit(pUI.getItBenefit());
		pm.setBusinessBenefit(pUI.getBusinessBenefit());
		pm.setStatus(pUI.getStatus());
		pm.setPriority(pUI.getPriority());
		pm.setEffortHours(pUI.getEffortHours());
		pm.setIdeatedBy(pUI.getIdeatedBy());
		pm.setImplementedBy(pUI.getImplementedBy());
		pm.setSme(pUI.getSme());

		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		try {
			pm.setInitiationDate(sdf.parse(pUI.getInitiationDate()));
			pm.setCompletionDate(sdf.parse(pUI.getCompletionDate()));
		} catch (ParseException e) {
			throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
		}
		pm.setCompletionPercentage(pUI.getCompletionPercentage());
		pm.setComments(pUI.getComments());
		pm.setBenefitTypes(getBenefitTypesByValues(pUI.getBenefitTypes()));
		pm.setDollarSaving(pUI.getDollarSaving());
		pm.setIncidentReduction(pUI.getIncidentReduction());
		pm.setEffortSaving(pUI.getEffortSaving());
		/*
		 * Add audit info for the problem management.
		 */
		AuditInfo auditInfo = null;
		auditInfo = new AuditInfo();
		if (pm.getAuditInfo() == null || pm.getAuditInfo().getCreatedBy() == null) {
			auditInfo.setCreatedBy(pUI.getUserId());
			auditInfo.setCreatedDate(new Date());
		} else {
			auditInfo = pm.getAuditInfo();
			if (auditInfo == null) {
				auditInfo = new AuditInfo();
			}
		}
		auditInfo.setUpdatedBy(pUI.getUserId());
		auditInfo.setUpdatedDate(new Date());
		pm.setAuditInfo(auditInfo);

		return pm;
	}

	public static ProblemManagementUI populateProblemManagementForUI(ProblemManagement pm) {

		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		ProblemManagementUI pUI = new ProblemManagementUI();
		pUI.setId(pm.getId());
		pUI.setInnovationTitle(pm.getInnovationTitle());
		pUI.setProblemStatement(pm.getProblemStatement());
		pUI.setSolution(pm.getSolution());
		pUI.setApplication(pm.getApplication());
		pUI.setItBenefit(pm.getItBenefit());
		pUI.setBusinessBenefit(pm.getBusinessBenefit());
		pUI.setStatus(pm.getStatus());
		pUI.setPriority(pm.getPriority());
		pUI.setEffortHours(pm.getEffortHours());
		pUI.setIdeatedBy(pm.getIdeatedBy());
		pUI.setImplementedBy(pm.getImplementedBy());
		pUI.setSme(pm.getSme());
		pUI.setInitiationDate(sdf.format(pm.getInitiationDate()));
		pUI.setCompletionDate(sdf.format(pm.getCompletionDate()));
		pUI.setCompletionPercentage(pm.getCompletionPercentage());
		pUI.setComments(pm.getComments());
		pUI.setBenefitTypes(getListOfValuesFromBenefitTypes(pm.getBenefitTypes()));
		pUI.setDollarSaving(pm.getDollarSaving());
		pUI.setIncidentReduction(pm.getIncidentReduction());
		pUI.setEffortSaving(pm.getEffortSaving());

		return pUI;
	}

	@SuppressWarnings("unchecked")
	private static List<BenefitType> getBenefitTypesByValues(List<String> benefitTypeValues) {
		List<BenefitType> benefitTypes = null;
		if (benefitTypeValues != null && benefitTypeValues.size() > 0) {
			Session session = sessionFactory.openSession();
			Criteria cr = session.createCriteria(BenefitType.class);
			cr.add(Restrictions.in("value", benefitTypeValues));
			benefitTypes = cr.list();
			session.close();
		}
		return benefitTypes;
	}

	private static List<String> getListOfValuesFromBenefitTypes(List<BenefitType> benefitTypes) {
		List<String> benefitTypeValues = new ArrayList<String>();
		for (BenefitType benefitType : benefitTypes) {
			benefitTypeValues.add(benefitType.getValue());
		}
		return benefitTypeValues;

	}

}
