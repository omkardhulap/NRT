/**
 * 
 */
package com.nike.reporting.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.HighlightDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.SearchHighlight;
import com.nike.reporting.model.converter.HighlightConvertor;
import com.nike.reporting.model.dto.HighlightDTO;

/**
 * @author vishal_lalwani01
 *
 */
public class HighlightServiceImpl implements HighlightService {
	
	private static final Logger logger = LoggerFactory.getLogger(HighlightServiceImpl.class);
	
	private HighlightDAO highlightDAO;

	@Autowired(required = true)
	@Qualifier(value = "highlightDAO")
	public void setHighlightDAO(HighlightDAO highlightDAO) {
		this.highlightDAO = highlightDAO;
	}
	
	private HighlightConvertor highlightConvertor;
	
	@Autowired(required = true)
	@Qualifier(value = "highlightConvertor")
	public void setHighlightConvertor(HighlightConvertor highlightConvertor) {
		this.highlightConvertor = highlightConvertor;
	}

	/* (non-Javadoc)
	 * @see com.nike.reporting.service.HighlightService#getHighlightById(int)
	 */
	@Override
	@Transactional
	public Highlight getHighlightById(int id) {
		return this.highlightDAO.getHighlightById(id);
	}

	@Override
	@Transactional
	public void addHighlight(Highlight highlight) {
		this.highlightDAO.addHighlight(highlight);
		
	}

	@Override
	@Transactional
	public void updateHighlight(Highlight highlight) {
		this.highlightDAO.updateHighlight(highlight);
		
	}

	@Override
	@Transactional
	public List<HighlightDTO> getHighlightBySearchCriteria(SearchHighlight searchHighlight) throws DateParsingException {
		return highlightConvertor.convertEntityListToDTOList(this.highlightDAO.getHighlightBySearchCriteria(searchHighlight));
	}

	@Override
	@Transactional
	public void deleteHighlights(List<Integer> highlightIds) {
			this.highlightDAO.deleteHighlightByIds(highlightIds);
	}

}
