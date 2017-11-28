/**
 * 
 */
package com.nike.reporting.model;

import java.util.List;

/**
 * @author vishal_lalwani01
 *
 */
public class SearchHighlight {
	private String fromDate;
	private String toDate;
	private List<String> capabilities;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<String> getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(List<String> capabilities) {
		this.capabilities = capabilities;
	}
	
	
	
}
