package com.nike.reporting.model;

import java.util.Date;

public class SearchMTTR {

	private Integer mttrId;
	private String snowId;
	private String category;
	private String priority;
	private String fromDate;
	private String toDate;
	private Date createdDate;
	private String assignmentGroup;
	private String mttrBreachReason;
	private Boolean isMTTRBreached;
	private float percentMttrSlaAchieved;
	private String assignedTo;

	public SearchMTTR() {

	}

	public Integer getMttrId() {
		return mttrId;
	}

	public void setMttrId(Integer mttrId) {
		this.mttrId = mttrId;
	}

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public String getMttrBreachReason() {
		return mttrBreachReason;
	}

	public void setMttrBreachReason(String mttrBreachReason) {
		this.mttrBreachReason = mttrBreachReason;
	}

	public Boolean getIsMTTRBreached() {
		return isMTTRBreached;
	}

	public void setIsMTTRBreached(Boolean isMTTRBreached) {
		this.isMTTRBreached = isMTTRBreached;
	}

	public float getPercentMttrSlaAchieved() {
		return percentMttrSlaAchieved;
	}

	public void setPercentMttrSlaAchieved(float percentMttrSlaAchieved) {
		this.percentMttrSlaAchieved = percentMttrSlaAchieved;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

}
