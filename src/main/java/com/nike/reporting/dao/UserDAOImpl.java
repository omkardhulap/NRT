package com.nike.reporting.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Nrt_user;
import com.nike.reporting.model.Outage;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	@Transactional
	public void addUser(Nrt_user up) throws NikeException {
		Session session = this.sessionFactory.getCurrentSession();
		up.setNikeid(up.getNikeid().toLowerCase());
		up.setInfyid(up.getInfyid().toLowerCase());
		up.setPassword(LoginUtil.encrypt(up.getPassword()));
		up.setNikeEmail(up.getNikeEmail());
		up.setInfyEmail(up.getInfyEmail());

		SQLQuery query = session.createSQLQuery("select distinct lower(nikeid) from nrt_user");
		List<Object[]> list = query.list();
		if (list != null & list.contains(up.getNikeid().toLowerCase())) {
			throw new NikeException("Operation Failed:", ErrorMessages.DB_USER_EXISTS_EXCEPTION);
		}

		session.persist(up);

		logger.info("User saved successfully, User Details=" + up);
	}

	@Override
	@Transactional
	public void updateUser(Nrt_user up) throws NikeException {

		/*
		 * Add audit info for the outage.
		 */
		AuditInfo auditInfo = null;
		auditInfo = new AuditInfo();
		if (up.getAuditInfo() == null || up.getAuditInfo().getCreatedBy() == null) {
			auditInfo.setCreatedBy(up.getAuditInfo().getUpdatedBy());
			auditInfo.setUpdatedBy(up.getAuditInfo().getUpdatedBy());
			auditInfo.setCreatedDate(new Date());
			auditInfo.setUpdatedDate(new Date());
		} else {
			auditInfo = up.getAuditInfo();
			if (auditInfo == null) {
				auditInfo = new AuditInfo();
			}
		}
		auditInfo.setUpdatedBy(up.getAuditInfo().getUpdatedBy());
		auditInfo.setUpdatedDate(new Date());
		up.setAuditInfo(auditInfo);

		Session session = this.sessionFactory.getCurrentSession();
		up.setPassword(LoginUtil.encrypt(up.getPassword()));
		up.setNikeEmail(up.getNikeEmail());
		up.setInfyEmail(up.getInfyEmail());
		session.update(up);
		logger.info("User updated successfully, Outage Details=" + up);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Nrt_user> listUsers(String user_id) {
		List<Nrt_user> usersList = null;
		Session session = this.sessionFactory.getCurrentSession();

		if (LoginUtil.hasRole(ReportingConstants.ROLE_ADMIN) || LoginUtil.hasRole(ReportingConstants.ROLE_MANAGER)) {
			usersList = session.createQuery("from Nrt_user").list();
		} else {
			Query query = session.createQuery("from Nrt_user u WHERE u.nikeid = :nikeid");
			query.setParameter("nikeid", user_id);
			usersList = query.list();
		}
		return usersList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Nrt_user> listAllUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Nrt_user> usersList = session.createQuery("from Nrt_user").list();
		return usersList;
	}

	@Override
	@Transactional
	public Nrt_user getUserById(int id) throws NikeException {
		Session session = this.sessionFactory.getCurrentSession();
		Nrt_user up = (Nrt_user) session.load(Nrt_user.class, new Integer(id));
		logger.info("User loaded successfully, User details=" + up);
		return up;
	}

	@Override
	@Transactional
	public Nrt_user getUserByName(String user) {
		Nrt_user nu = null;
		Session session = this.sessionFactory.getCurrentSession();
		List<Nrt_user> usersList = session.createQuery("from Nrt_user where status='Y' AND (lower(infyid) = '" + user.toLowerCase() + "' or lower(nikeid) = '" + user.toLowerCase() + "'" + ")").list();
		logger.info("User loaded successfully, User details=" + nu);

		if (usersList.size() > 0) {
			nu = usersList.get(0);
		}

		return nu;
	}

	@Override
	@Transactional
	public void deleteUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Outage o = (Outage) session.load(Outage.class, new Integer(id));
		if (null != o) {
			session.delete(o);
		}
		logger.info("User deleted successfully, User details=" + o);
	}

	@Override
	@Transactional
	public boolean authenticateUser(Nrt_user up) {
		boolean authenticate = false;
		Session session = this.sessionFactory.getCurrentSession();
		List<Nrt_user> usersList = session.createQuery("from Nrt_user where infyid='" + "a" + "' or nikeid='" + "a" + "'").list();

		if (usersList.size() > 0) {
			authenticate = true;
		}

		return authenticate;
	}

}
