package com.nike.reporting.model;

import java.util.Date;
import java.util.List;

public class SearchEffort {

	private Integer effortId;
	private List<String> effortCategory;
	private List<String> Priority;
	private List<String> complexity;
	private List<String> applications;
	private String snowId;
	private String fromDate;
	private String toDate;
	private Date effortDate;
	private String owner;

	public SearchEffort() {

	}

	public Integer getEffortId() {
		return effortId;
	}

	public void setEffortId(Integer effortId) {
		this.effortId = effortId;
	}

	public List<String> getEffortCategory() {
		return effortCategory;
	}

	public void setEffortCategory(List<String> effortCategory) {
		this.effortCategory = effortCategory;
	}

	public List<String> getPriority() {
		return Priority;
	}

	public void setPriority(List<String> priority) {
		Priority = priority;
	}

	public List<String> getComplexity() {
		return complexity;
	}

	public void setComplexity(List<String> complexity) {
		this.complexity = complexity;
	}

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
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

	public Date getEffortDate() {
		return effortDate;
	}

	public void setEffortDate(Date effortDate) {
		this.effortDate = effortDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
