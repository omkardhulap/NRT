/**
* <h1>Search Problem Management Object</h1>
* SearchProblemManagement.java defines variables for search PM screen.
*/
package com.nike.reporting.model;

import java.util.List;

public class SearchProblemManagement {

	private String fromDate;
	private String toDate;
	private List<String> applications;
	private List<String> benefitTypes;
	private List<String> statuses;
	private String owner;
	boolean fetchForLastModified;

	public SearchProblemManagement(){		
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

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public List<String> getBenefitTypes() {
		return benefitTypes;
	}

	public void setBenefitTypes(List<String> benefitTypes) {
		this.benefitTypes = benefitTypes;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean getFetchForLastModified() {
		return fetchForLastModified;
	}

	public void setFetchForLastModified(boolean fetchForLastModified) {
		this.fetchForLastModified = fetchForLastModified;
	}

}
