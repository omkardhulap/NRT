package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.SearchProblemManagement;

public interface ProblemManagementDAO {
	public void addProblemManagement(ProblemManagement pm);
	public void updateProblemManagement(ProblemManagement pm);
	public ProblemManagement getProblemManagementById(int id);
	public List<ProblemManagement> getProblemManagementBySearchCriteria(SearchProblemManagement spm) throws DateParsingException;

}
