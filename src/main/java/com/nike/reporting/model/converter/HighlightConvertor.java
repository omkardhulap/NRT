package com.nike.reporting.model.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nike.reporting.dao.HighlightDAO;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Highlight;
import com.nike.reporting.model.dto.HighlightDTO;

public class HighlightConvertor {
	
	private HighlightDAO highlightDAO;
	
	public void setHighlightDAO(HighlightDAO highlightDAO) {
		this.highlightDAO = highlightDAO;
	}

	public HighlightDTO convertEntityToDTO(Highlight highlight) {

		HighlightDTO highlightDTO = new HighlightDTO();
		highlightDTO.setId(highlight.getId());
		highlightDTO.setDescription(highlight.getDescription());
		highlightDTO.setFromDate(highlight.getFromDate());
		highlightDTO.setToDate(highlight.getToDate());
		
		List<String> uiCapabilities = new ArrayList<>();
		if(null != highlight.getCapabilities()){
			for(Capability cap : highlight.getCapabilities()){
				uiCapabilities.add(cap.getDescription());
			}
		}
		highlightDTO.setCapabilities(uiCapabilities);
		
		if (highlight.getAuditInfo() != null) {
			AuditInfo auditInfo = highlight.getAuditInfo();
			highlightDTO.setCreatedBy(auditInfo.getCreatedBy());
			highlightDTO.setCreatedDate(auditInfo.getCreatedDate());
			highlightDTO.setUpdatedBy(auditInfo.getUpdatedBy());
			highlightDTO.setUpdatedDate(auditInfo.getUpdatedDate());
		}

		return highlightDTO;

	}

	public List<HighlightDTO> convertEntityListToDTOList(List<Highlight> highlightList) {
		List<HighlightDTO> highlightDTOs = new ArrayList<HighlightDTO>();
		HighlightDTO highlightDTO = new HighlightDTO();
		if (highlightList != null) {
			Iterator<Highlight> hiIterator = highlightList.iterator();
			while (hiIterator.hasNext()) {
				Highlight highlight = hiIterator.next();
				highlightDTO = convertEntityToDTO(highlight);
				highlightDTOs.add(highlightDTO);
			}
		}

		return highlightDTOs;

	}
	
	public Highlight convertDtoToEntity(HighlightDTO highlightDTO) {

		Highlight highlight = new Highlight();
		
		highlight.setId(highlightDTO.getId());
		highlight.setFromDate(highlightDTO.getFromDate());
		highlight.setToDate(highlightDTO.getToDate());
		highlight.setDescription(highlightDTO.getDescription());
		
		if(highlightDTO.getCapabilities() != null){
			highlight.setCapabilities(this.highlightDAO.getCapabilitiesByDescription(highlightDTO.getCapabilities()));
		}
		
		return highlight;

	}

	
}
