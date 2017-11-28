package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.model.Application;

public interface ApplicationDAO {
	
	public void addApplication(Application o);
	public void updateApplicatione(Application o);
	public List<Application> listApplications();
	/*public Application getApplicationById(int id);
	public List<Application> getApplicationsByName(String[] appNames);*/
	public void removeApplication(int id);
}
