/**
 * 
 */
package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;

/**
 * @author Sachin_Ainapure
 * 
 */
public interface MTTRDAO {

	public void addMTTR(MTTRData mttrData);

	public void updateMTTR(MTTRData mttrData);

	public List<MTTRData> getMTTRBySearchCriteria(SearchMTTR searchMTTR) throws DateParsingException;

	public List<MTTRData> getMTTRFromDB();

	public List getMTTRForReports(String plainSQlQuery, SearchMTTR searchMTTR);

	public MTTRData getMTTRById(int id);

}
