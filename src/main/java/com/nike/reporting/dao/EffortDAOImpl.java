/**
 * 
 */
package com.nike.reporting.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.EffortReports;
import com.nike.reporting.model.SearchEffort;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */
public class EffortDAOImpl implements EffortDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(EffortDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.dao.EffortDAO#addEffort(com.nike.reporting.model.Effort
	 * )
	 */
	@Override
	public void addEffort(Effort effort) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(effort);
		logger.info("Effort saved successfully, Effort Details=" + effort);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.dao.EffortDAO#updateEffort(com.nike.reporting.model
	 * .Effort)
	 */
	@Override
	public void updateEffort(Effort effort) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(effort);
		logger.info("Effort saved successfully, Effort Details=" + effort);

	}

	@Override
	public void deleteEffort(Effort effort) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(effort);
		logger.info("Effort deleted successfully, Effort Details=" + effort);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nike.reporting.dao.EffortDAO#getEffortById(int)
	 */
	@Override
	public Effort getEffortById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Effort effort = (Effort) session.load(Effort.class, new Integer(id));
		logger.info("Effort fetched, Effort details=" + effort);
		return effort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.dao.EffortDAO#getEffortBySearchCriteria(com.nike.reporting
	 * .model.Effort)
	 */
	@Override
	public List<Effort> getEffortBySearchCriteria(SearchEffort searchEffort)
			throws DateParsingException {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria searchCriteria = session.createCriteria(Effort.class);

		if (StringUtils.isNotBlank(searchEffort.getFromDate())
				&& StringUtils.isNotBlank(searchEffort.getToDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					ReportingConstants.DATE_FORMAT_PATTERN);
			try {
				searchCriteria.add(Restrictions.ge("effortDate",
						sdf.parse(searchEffort.getFromDate())));
				searchCriteria.add(Restrictions.le("effortDate",
						sdf.parse(searchEffort.getToDate())));
			} catch (ParseException e) {
				throw new DateParsingException(e,
						ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}

		if (searchEffort.getApplications() != null
				&& !searchEffort.getApplications().isEmpty()) {
			searchCriteria.createAlias("application", "applicationAlias");
			searchCriteria.add(Restrictions.in("applicationAlias.appName",
					searchEffort.getApplications()));
		}

		if (searchEffort.getEffortId() != null
				&& !searchEffort.getEffortId().toString().isEmpty()) {
			searchCriteria
					.add(Restrictions.eq("id", searchEffort.getEffortId()));
		}

		if (searchEffort.getEffortCategory() != null
				&& !searchEffort.getEffortCategory().isEmpty()) {
			searchCriteria.createAlias("category", "categoryAlias");
			searchCriteria.add(Restrictions.in("categoryAlias.description",
					searchEffort.getEffortCategory()));
		}

		if (searchEffort.getComplexity() != null
				&& !searchEffort.getComplexity().isEmpty()) {
			searchCriteria.createAlias("complexity", "complexityAlias");
			searchCriteria.add(Restrictions.in("complexityAlias.description",
					searchEffort.getComplexity()));
		}

		if (searchEffort.getSnowId() != null
				&& !searchEffort.getSnowId().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("snowNumber",
					searchEffort.getSnowId()));
		}
		if (searchEffort.getOwner() != null
				&& !searchEffort.getOwner().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("auditInfo.createdBy",
					searchEffort.getOwner()));
		}
		searchCriteria.addOrder(Order.asc("id"));
		searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return searchCriteria.list();
	}

	@Override
	public List getEffortForReports(String plainSQlQuery) {
		List effortReportsList = null;
		Session session = this.sessionFactory.getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery(plainSQlQuery);
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		effortReportsList = sqlQuery.list();
		logger.info("EffortReports details=" + effortReportsList.size());
		return effortReportsList;
	}
}
