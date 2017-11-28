/**
 * <h1>Outage Service Object</h1>
 * OutageService.java declares methods for Outage Service.
 * @author  Mital Gadoya
 * @version 1.0
 * @since   2015-01-01
 */
package com.nike.reporting.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.model.dto.OutageDTO;

public interface OutageService {

	public void addOutage(Outage o);

	public void updateOutage(Outage o);

	public List<Outage> listOutages();

	public Outage getOutageById(int id);

	/* public List<Outage> getOutageBySearchCriteria(Outage o); */
	public void removeOutage(int id);

	public List<Outage> getOutageBySearchCriteria(SearchOutage so) throws DateParsingException;

	public String createEOSPptx(HttpServletRequest request, HttpServletResponse response, OutageDTO outageDTO) throws NikeException;

}
