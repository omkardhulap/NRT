/**
 * 
 */
package com.nike.reporting.dao;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.SearchHighlight;

/**
 * @author vishal_lalwani01
 *
 */
public interface HighlightDAO {
	
	public Highlight getHighlightById(int id);

	public void addHighlight(Highlight highlight);

	public void updateHighlight(Highlight highlight);
	
	public List<Capability> getCapabilitiesByDescription(List<String> capabilities);

	public List<Highlight> getHighlightBySearchCriteria(SearchHighlight searchHighlight) throws DateParsingException;

	public void deleteHighlightById(int id);

	public void deleteHighlightByIds(List<Integer> ids);
	
}
