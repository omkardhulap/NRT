/**
 * 
 */
package com.nike.reporting.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.SearchHighlight;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author vishal_lalwani01
 *
 */
public class HighlightDAOImpl implements HighlightDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(HighlightDAOImpl.class);
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	/* (non-Javadoc)
	 * @see com.nike.reporting.dao.HighlightDAO#Highlight(int)
	 */
	@Override
	public Highlight getHighlightById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Highlight highlight = (Highlight) session.load(Highlight.class, new Integer(id));
		logger.info("Highlight loaded successfully, Highlight details=" + highlight);

		return highlight;
	}

	@Override
	public void addHighlight(Highlight highlight) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(highlight);
		logger.info("highlight saved successfully, Highlight Details=" + highlight);
		
	}

	@Override
	public void updateHighlight(Highlight highlight) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(highlight);
		logger.info("highlight updated successfully, Highlight Details=" + highlight);
		
	}
	
	@Override
	public List<Capability> getCapabilitiesByDescription(
			List<String> capabilities) {
		Session session = sessionFactory.openSession();
		List<Capability> capabilityList = null;
		Criteria cr = session.createCriteria(Capability.class);
		if (capabilities != null && capabilities.size() > 0) {
			cr.add(Restrictions.in("description", capabilities.toArray()));
			capabilityList = cr.list();
		}
		session.close();
		return capabilityList;
	}

	@Override
	public List<Highlight> getHighlightBySearchCriteria(SearchHighlight searchHighlight) throws DateParsingException {
		
		Session session = this.sessionFactory.getCurrentSession();
		Criteria searchCriteria = session.createCriteria(Highlight.class);
		
		if (StringUtils.isNotBlank(searchHighlight.getFromDate()) && StringUtils.isNotBlank(searchHighlight.getToDate())) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			try {
				searchCriteria.add(Restrictions.ge("fromDate", sdf.parse(searchHighlight.getFromDate())));
				searchCriteria.add(Restrictions.le("toDate", sdf.parse(searchHighlight.getToDate())));
			} catch (ParseException e) {
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}
		
		if (searchHighlight.getCapabilities() != null && !searchHighlight.getCapabilities().isEmpty()) {
			searchCriteria.createAlias("capabilities", "capabilityAlias");
			searchCriteria.add(Restrictions.in("capabilityAlias.description", searchHighlight.getCapabilities()));
		}
		
		searchCriteria.addOrder(Order.asc("id"));
		searchCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return searchCriteria.list();
	}

	@Override
	public void deleteHighlightById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Highlight highlight = (Highlight) session.load(Highlight.class, new Integer(id));
		if (null != highlight) {
			session.delete(highlight);
		}
		logger.info("Highlight deleted successfully, " + highlight);
	}
	
	@Override
	public void deleteHighlightByIds(List<Integer> ids) {
		Session session = this.sessionFactory.getCurrentSession();
		Highlight highlight;
		for(int id : ids){
			highlight = (Highlight) session.load(Highlight.class, new Integer(id));
			if (null != highlight) {
				session.delete(highlight);
			}
			logger.info("Highlights deleted successfully, " + highlight);
		}
	}
	
}
