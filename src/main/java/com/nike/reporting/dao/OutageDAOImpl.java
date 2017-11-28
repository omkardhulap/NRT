package com.nike.reporting.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

public class OutageDAOImpl implements OutageDAO {

	private static final Logger logger = LoggerFactory.getLogger(OutageDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addOutage(Outage o) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(o);
		logger.info("Outage saved successfully, Outage Details=" + o);
	}

	@Override
	public void updateOutage(Outage o) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(o);
		logger.info("Outage updated successfully, Outage Details=" + o);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Outage> listOutages() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Outage> outagesList = session.createQuery("from Outage").list();
		for (Outage o : outagesList) {
			logger.info("Outage List::" + o);
		}
		return outagesList;
	}

	@Override
	public Outage getOutageById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Outage o = (Outage) session.load(Outage.class, new Integer(id));
		logger.info("Outage loaded successfully, Outage details=" + o);

		return o;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Outage> getOutageBySearchCriteria(SearchOutage so) throws DateParsingException {

		Session session = this.sessionFactory.getCurrentSession();
		Criteria searchCriteria = session.createCriteria(Outage.class);

		if (!ArrayUtils.isEmpty(so.getTypeOfOutage()) && so.getTypeOfOutage().length == 1) {
			searchCriteria.add(Restrictions.eq("outageType", so.getTypeOfOutage()[0]));
		}
		if (StringUtils.isNotBlank(so.getFromDate()) && StringUtils.isNotBlank(so.getToDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			try {
				searchCriteria.add(Restrictions.ge("deploymentStartDate", sdf.parse(so.getFromDate())));
				searchCriteria.add(Restrictions.le("deploymentStartDate", sdf.parse(so.getToDate())));
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}

		if (so.getApplications() != null && !so.getApplications().isEmpty()) {
			searchCriteria.createAlias("applications", "applicationsAlias");
			searchCriteria.add(Restrictions.in("applicationsAlias.appName", so.getApplications()));
		}

		if (so.getOutageId() != null && !so.getOutageId().toString().isEmpty()) {
			searchCriteria.add(Restrictions.eq("id", so.getOutageId()));
		}

		String token = so.getSnowId();

		if (token != null && !token.isEmpty()) {
			if (token.startsWith(ReportingConstants.INC)) {
				searchCriteria.createAlias("incidentList", "incidentAlias");
				searchCriteria.add(Restrictions.eq("incidentAlias.snowId", token.trim()));
			} else if (token.startsWith(ReportingConstants.DFCT)) {
				searchCriteria.createAlias("defectList", "defectAlias");
				searchCriteria.add(Restrictions.eq("defectAlias.snowId", token.trim()));
			} else if (token.startsWith(ReportingConstants.ENHC)) {
				searchCriteria.createAlias("enhancementList", "enhancementAlias");
				searchCriteria.add(Restrictions.eq("enhancementAlias.snowId", token.trim()));
			}
		}

		if (so.getCapabilities() != null && !so.getCapabilities().isEmpty()) {
			if (so.getApplications() == null || so.getApplications().isEmpty()) {
				searchCriteria.createAlias("applications", "applicationsAlias");
			}
			searchCriteria.createAlias("applicationsAlias.capability", "capabilityAlias");
			searchCriteria.add(Restrictions.in("capabilityAlias.description", so.getCapabilities()));
		}
		searchCriteria.addOrder(Order.asc("id"));
		searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return searchCriteria.list();
	}

	@Override
	public void removeOutage(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Outage o = (Outage) session.load(Outage.class, new Integer(id));
		if (null != o) {
			session.delete(o);
		}
		logger.info("Outage deleted successfully, outage details=" + o);
	}

}
