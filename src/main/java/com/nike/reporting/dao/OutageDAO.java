package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.SearchOutage;

public interface OutageDAO {
	public void addOutage(Outage o);
	public void updateOutage(Outage o);
	public List<Outage> listOutages();
	public Outage getOutageById(int id);
	public List<Outage> getOutageBySearchCriteria(SearchOutage so) throws DateParsingException;
	public void removeOutage(int id);

}
