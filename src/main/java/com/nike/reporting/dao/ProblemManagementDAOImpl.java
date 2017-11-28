package com.nike.reporting.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.SearchProblemManagement;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

public class ProblemManagementDAOImpl implements ProblemManagementDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProblemManagementDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addProblemManagement(ProblemManagement pm) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(pm);
		logger.info("ProblemManagement saved successfully, ProblemManagement Details=" + pm);
		
	}

	@Override
	public void updateProblemManagement(ProblemManagement pm) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(pm);
		logger.info("ProblemManagement saved successfully, ProblemManagement Details=" + pm);		
	}

	@Override
	public ProblemManagement getProblemManagementById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		ProblemManagement pm = (ProblemManagement) session.load(ProblemManagement.class, new Integer(id));
		logger.info("ProblemManagement fetched, ProblemManagement details=" + pm);
		return pm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemManagement> getProblemManagementBySearchCriteria(
			SearchProblemManagement spm) throws DateParsingException {

		Session session = this.sessionFactory.getCurrentSession();
		Criteria searchCriteria = session.createCriteria(ProblemManagement.class);

		if (StringUtils.isNotBlank(spm.getFromDate()) && StringUtils.isNotBlank(spm.getToDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			try {
				if(spm.getFetchForLastModified()) {
					searchCriteria.add(Restrictions.ge("auditInfo.updatedDate", sdf.parse(spm.getFromDate())));
					searchCriteria.add(Restrictions.le("auditInfo.updatedDate", sdf.parse(spm.getToDate())));				
				} else {
					searchCriteria.add(Restrictions.ge("initiationDate", sdf.parse(spm.getFromDate())));
					searchCriteria.add(Restrictions.le("initiationDate", sdf.parse(spm.getToDate())));
				}
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}

		if (spm.getApplications() != null && !spm.getApplications().isEmpty()) {
			searchCriteria.createAlias("application", "applicationAlias");
			searchCriteria.add(Restrictions.in("applicationAlias.appName", spm.getApplications()));
		}

		if (spm.getStatuses() != null && !spm.getStatuses().isEmpty()) {
			searchCriteria.createAlias("status", "statusAlias");
			searchCriteria.add(Restrictions.in("statusAlias.description", spm.getStatuses()));
		}

		if (spm.getBenefitTypes() != null && !spm.getBenefitTypes().isEmpty()) {
			searchCriteria.createAlias("benefitTypes", "benefitTypesAlias");
			searchCriteria.add(Restrictions.in("benefitTypesAlias.value", spm.getBenefitTypes()));
		}

		String owner = spm.getOwner();

		if(StringUtils.isNotBlank(owner)) {
			searchCriteria.add(Restrictions.or(
					Restrictions.ilike("ideatedBy", owner, MatchMode.ANYWHERE),Restrictions.ilike("implementedBy", owner, MatchMode.ANYWHERE)));
		}

		searchCriteria.addOrder(Order.asc("id"));
		searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return searchCriteria.list();
	}


}
