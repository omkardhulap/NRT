/**
 * 
 */
package com.nike.reporting.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.SearchEffort;
import com.nike.reporting.model.dto.ExcelEffortDTO;

/**
 * @author Sachin_Ainapure
 * 
 */
public interface EffortService {

	public void addEffort(Effort effort);

	public void updateEffort(Effort effort);

	public void deleteEffort(Effort effort);

	public Effort getEffortById(int id);

	public List<Effort> getEffortBySearchCriteria(SearchEffort searchEffort) throws DateParsingException;

	public List<ExcelEffortDTO> getEffortListFromExcel(File file, String userId) throws NikeException;

	public void addEfforts(List<Effort> effortList);
	
	public boolean createExcelReportFromList(String sqlQuery, HttpServletRequest request, HttpServletResponse response, String reportName);


}
