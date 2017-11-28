package com.nike.reporting.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nike.reporting.model.Application;

public class ApplicationDAOImpl implements ApplicationDAO {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addApplication(Application o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateApplicatione(Application o) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Application> listApplications() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Application> applicationList = session.createQuery("from Application").list();

		return applicationList;
	}

	/*
	 * public Application getApplicationById(int id) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public List<Application> getApplicationsByName(String[]
	 * appNames) { Session session = this.sessionFactory.getCurrentSession();
	 * List<Application> applicationList = null; Criteria cr =
	 * session.createCriteria(Application.class); if(appNames != null &&
	 * appNames.length > 0){ cr.add(Restrictions.in("appName", appNames));
	 * applicationList = cr.list(); } return applicationList; }
	 */

	@Override
	public void removeApplication(int id) {
		// TODO Auto-generated method stub

	}

}
