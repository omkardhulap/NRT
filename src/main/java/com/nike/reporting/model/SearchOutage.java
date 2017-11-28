/**
* <h1>Search Outage Object</h1>
* SearchOutage.java defines variables for search outage screen.
* @author  Mital Gadoya
* @version 1.0
* @since   2015-01-01
*/
package com.nike.reporting.model;

import java.util.List;

public class SearchOutage {

	private Integer outageId;
	private String snowId;
	private String fromDate;
	private String toDate;
	private char[] typeOfOutage = new char[2];
	private List<String> applications;
	private List<String> capabilities;

	public SearchOutage(){
		
	}

	public Integer getOutageId() {
		return outageId;
	}

	public void setOutageId(Integer outageId) {
		this.outageId = outageId;
	}

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

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

	public char[] getTypeOfOutage() {
		return typeOfOutage;
	}

	public void setTypeOfOutage(char[] typeOfOutage) {
		this.typeOfOutage = typeOfOutage;
	}

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public List<String> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<String> capabilities) {
		this.capabilities = capabilities;
	}

}
