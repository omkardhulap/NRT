package com.nike.reporting.service;

import java.util.List;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.SearchHighlight;
import com.nike.reporting.model.dto.HighlightDTO;

/**
 * @author vishal_lalwani01
 *
 */
public interface HighlightService {

	public Highlight getHighlightById(int id);

	public void addHighlight(Highlight highlight);

	public void updateHighlight(Highlight highlight);

	public List<HighlightDTO> getHighlightBySearchCriteria(SearchHighlight searchHighlight) throws DateParsingException;

	public void deleteHighlights(List<Integer> highlightIds);

}
