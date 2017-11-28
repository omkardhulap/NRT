/**
 * 
 */
package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.SearchEffort;

/**
 * @author Sachin_Ainapure
 * 
 */
public interface EffortDAO {
	public void addEffort(Effort effort);

	public void updateEffort(Effort effort);

	public void deleteEffort(Effort effort);

	public Effort getEffortById(int id);

	public List<Effort> getEffortBySearchCriteria(SearchEffort searchEffort) throws DateParsingException;
	
	public List getEffortForReports(String sqlQuery);
	
}
