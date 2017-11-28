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
import com.nike.reporting.model.MTTRAnalysis;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.dto.ExcelMTTRDTO;

/**
 * @author Sachin_Ainapure
 * 
 */
public interface MTTRService {

	// public List<Effort> getEffortBySearchCriteria(SearchEffort searchEffort)
	// throws DateParsingException;

	public List<ExcelMTTRDTO> getMTTRDataFromExcel(File file, String userId) throws NikeException;

	public void addMTTRs(List<MTTRData> mttrDataList);

	public List<MTTRData> getMTTRDataFromDB() throws NikeException;

	public List createMTTRAnalysisTable(String sqlQuery, SearchMTTR searchMTTR);

	public List<MTTRData> getMTTRBySearchCriteria(SearchMTTR searchMTTR) throws DateParsingException;

	public MTTRData getMTTRById(int id);

	public void updateMTTR(MTTRData mttrData);

	public boolean exportMTTRAnalysisToExcel(List<MTTRAnalysis> listData, HttpServletRequest request, HttpServletResponse response, String fileName);

	public boolean exportMTTRIncidentsToExcel(List<ExcelMTTRDTO> listData, HttpServletRequest request, HttpServletResponse response, String fileName);

	// public boolean createExcelReportFromList(String sqlQuery,
	// HttpServletRequest request, HttpServletResponse response, String
	// reportName);

}
