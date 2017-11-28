package com.nike.reporting.service;

import java.util.List;

import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Nrt_user;

public interface UserService {

	public void addUser(Nrt_user o) throws NikeException;

	public void updateUser(Nrt_user o) throws NikeException;

	public List<Nrt_user> listUsers(String user_id);

	public Nrt_user getUserById(int id) throws NikeException;

	public void deleteUser(int id);

	public boolean authenticateUser(Nrt_user up);

	public Nrt_user getUserByName(String userName) throws NikeException;

}
