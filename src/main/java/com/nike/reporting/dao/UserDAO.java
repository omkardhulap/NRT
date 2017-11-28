package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Nrt_user;

public interface UserDAO {

	public void addUser(Nrt_user up) throws NikeException;

	public void updateUser(Nrt_user up) throws NikeException;

	public List<Nrt_user> listUsers(String user_id);

	public Nrt_user getUserById(int id) throws NikeException;

	public Nrt_user getUserByName(String id);

	public void deleteUser(int id);

	public boolean authenticateUser(Nrt_user up);

	List<Nrt_user> listAllUsers();

}
