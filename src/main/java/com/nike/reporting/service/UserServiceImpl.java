package com.nike.reporting.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.UserDAO;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Nrt_user;

public class UserServiceImpl implements UserService {

	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional
	public void addUser(Nrt_user up) throws NikeException {
		this.userDAO.addUser(up);
	}

	@Override
	@Transactional
	public void updateUser(Nrt_user up) throws NikeException {
		this.userDAO.updateUser(up);

	}

	@Override
	@Transactional
	public List<Nrt_user> listUsers(String user_id) {
		return this.userDAO.listUsers(user_id);
	}

	@Override
	@Transactional
	public Nrt_user getUserById(int id) throws NikeException {
		return this.userDAO.getUserById(id);
	}
	
	@Override
	@Transactional
	public Nrt_user getUserByName(String userName) throws NikeException {
		return this.userDAO.getUserByName(userName);
	}

	@Override
	@Transactional
	public void deleteUser(int id) {
		this.userDAO.deleteUser(id);
	}

	@Override
	public boolean authenticateUser(Nrt_user up) {
		return this.userDAO.authenticateUser(up);
	}

}
