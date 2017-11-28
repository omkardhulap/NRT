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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */
public class MTTRDAOImpl implements MTTRDAO {

	private static final Logger logger = LoggerFactory.getLogger(MTTRDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addMTTR(MTTRData mttrData) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(mttrData);
		logger.info("MTTRData saved successfully, MTTRData Details=" + mttrData);
	}

	@Override
	public void updateMTTR(MTTRData mttrData) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(mttrData);
		logger.info("MTTRData updated successfully, MTTRData Details=" + mttrData);
	}

	@Override
	public List<MTTRData> getMTTRBySearchCriteria(SearchMTTR searchMTTR) throws DateParsingException {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria searchCriteria = session.createCriteria(MTTRData.class);

		if (StringUtils.isNotBlank(searchMTTR.getFromDate()) && StringUtils.isNotBlank(searchMTTR.getToDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			try {
				searchCriteria.add(Restrictions.ge("closedDate", sdf.parse(searchMTTR.getFromDate())));
				searchCriteria.add(Restrictions.le("closedDate", sdf.parse(searchMTTR.getToDate())));
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}

		if (searchMTTR.getMttrId() != null && !searchMTTR.getMttrId().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("id", searchMTTR.getMttrId()));
		}

		// if (searchMTTR.getCategory() != null &&
		// !searchMTTR.getCategory().isEmpty()) {
		// searchCriteria.createAlias("category", "categoryAlias");
		// searchCriteria.add(Restrictions.eq("category",
		// searchMTTR.getCategory()));
		// }

		if (searchMTTR.getPriority() != null && !searchMTTR.getPriority().isEmpty()) {
			searchCriteria.createAlias("priority", "priorityAlias");
			searchCriteria.add(Restrictions.eq("priority", searchMTTR.getPriority()));
		}

		if (searchMTTR.getSnowId() != null && !searchMTTR.getSnowId().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("snowId", searchMTTR.getSnowId()));
		}

		if (searchMTTR.getAssignedTo() != null && !searchMTTR.getAssignedTo().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("assignedTo", searchMTTR.getAssignedTo()));
		}

		if (searchMTTR.getAssignmentGroup() != null && !searchMTTR.getAssignmentGroup().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("assignmentGroup", searchMTTR.getAssignmentGroup()));
		}

		if (searchMTTR.getMttrBreachReason() != null && !searchMTTR.getMttrBreachReason().toString().isEmpty()) {
			searchCriteria.add(Restrictions.like("mttrBreachReason", searchMTTR.getMttrBreachReason(), MatchMode.ANYWHERE));
		}

		if (searchMTTR.getIsMTTRBreached() != null && !searchMTTR.getIsMTTRBreached().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("isMTTRBreached", searchMTTR.getIsMTTRBreached()));
		}

		searchCriteria.addOrder(Order.asc("closedDate"));
		searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return searchCriteria.list();
	}

	@Override
	public List<MTTRData> getMTTRFromDB() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MTTRData> mttrDataList = session.createQuery("from MTTRData").list();
		return mttrDataList;
	}

	@Override
	public List getMTTRForReports(String plainSQlQuery, SearchMTTR searchMTTR) {
		try {
			List effortReportsList = null;
			Session session = this.sessionFactory.getCurrentSession();
			SQLQuery sqlQuery = session.createSQLQuery(plainSQlQuery);
			sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			sqlQuery.setParameter(0, searchMTTR.getFromDate());
			sqlQuery.setParameter(1, searchMTTR.getToDate());
			sqlQuery.setParameter(2, searchMTTR.getFromDate());
			sqlQuery.setParameter(3, searchMTTR.getToDate());
			sqlQuery.setParameter(4, searchMTTR.getFromDate());
			sqlQuery.setParameter(5, searchMTTR.getToDate());
			sqlQuery.setParameter(6, searchMTTR.getFromDate());
			sqlQuery.setParameter(7, searchMTTR.getToDate());
			sqlQuery.setParameter(8, searchMTTR.getFromDate());
			sqlQuery.setParameter(9, searchMTTR.getToDate());
			sqlQuery.setParameter(10, searchMTTR.getFromDate());
			sqlQuery.setParameter(11, searchMTTR.getToDate());
			sqlQuery.setParameter(12, searchMTTR.getFromDate());
			sqlQuery.setParameter(13, searchMTTR.getToDate());
			sqlQuery.setParameter(14, searchMTTR.getFromDate());
			sqlQuery.setParameter(15, searchMTTR.getToDate());
			sqlQuery.setParameter(16, searchMTTR.getFromDate());
			sqlQuery.setParameter(17, searchMTTR.getToDate());

			effortReportsList = sqlQuery.list();
			logger.info("MTTRReports details=" + effortReportsList.size());
			return effortReportsList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HibernateException(e.getMessage());
		}
	}

	@Override
	public MTTRData getMTTRById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		MTTRData mttrData = (MTTRData) session.load(MTTRData.class, new Integer(id));
		logger.info("MTTRData fetched, MTTRData details=" + mttrData);
		return mttrData;

		// Session session = this.sessionFactory.getCurrentSession();
		// Criteria searchCriteria = session.createCriteria(MTTRData.class);
		// searchCriteria.add(Restrictions.eq("id", id));
		// return (MTTRData) searchCriteria.list(0);
	}
}
