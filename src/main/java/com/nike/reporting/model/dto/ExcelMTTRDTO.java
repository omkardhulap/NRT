package com.nike.reporting.model.dto;

import java.util.Date;

/**
 * @author Sachin_Ainapure
 * 
 */
public class ExcelMTTRDTO {

	private String snowId;

	private String category;

	private String priority;

	private String escalation;

	private String definition;

	private String value;

	private Date startDate;

	private Date createdDate;

	private Date endDate;

	private Date closedDate;

	private int duration;

	private String assignmentGroup;

	private double createToCloseCompleteDuration;

	private double createToCloseCompleteInHours;

	private double createToResolveDuration;

	private double createToResolveInHours;

	private String uploadedBy;

	private Date uploadedDate;

	private String editedBy;

	private Date editedDate;

	private String assignedTo;

	private String resolutionCode;

	private String resolutionNotes;

	private String configurationItem;

	// Non excel objects for searching MTTR
	private Integer mttrId;
	private String mttrBreachReason;
	private Boolean isMTTRBreached;
	private float percentMttrSlaAchieved;
	private String remarks;
	private Date weekEnding;
	private String weekEndingString;

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

	public String getEscalation() {
		return escalation;
	}

	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public double getCreateToCloseCompleteDuration() {
		return createToCloseCompleteDuration;
	}

	public void setCreateToCloseCompleteDuration(double createToCloseCompleteDuration) {
		this.createToCloseCompleteDuration = createToCloseCompleteDuration;
	}

	public double getCreateToCloseCompleteInHours() {
		return createToCloseCompleteInHours;
	}

	public void setCreateToCloseCompleteInHours(double createToCloseCompleteInHours) {
		this.createToCloseCompleteInHours = createToCloseCompleteInHours;
	}

	public double getCreateToResolveDuration() {
		return createToResolveDuration;
	}

	public void setCreateToResolveDuration(double createToResolveDuration) {
		this.createToResolveDuration = createToResolveDuration;
	}

	public double getCreateToResolveInHours() {
		return createToResolveInHours;
	}

	public void setCreateToResolveInHours(double createToResolveInHours) {
		this.createToResolveInHours = createToResolveInHours;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getEditedDate() {
		return editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getResolutionCode() {
		return resolutionCode;
	}

	public void setResolutionCode(String resolutionCode) {
		this.resolutionCode = resolutionCode;
	}

	public String getResolutionNotes() {
		return resolutionNotes;
	}

	public void setResolutionNotes(String resolutionNotes) {
		this.resolutionNotes = resolutionNotes;
	}

	public String getConfigurationItem() {
		return configurationItem;
	}

	public void setConfigurationItem(String configurationItem) {
		this.configurationItem = configurationItem;
	}

	public Integer getMttrId() {
		return mttrId;
	}

	public void setMttrId(Integer mttrId) {
		this.mttrId = mttrId;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getWeekEnding() {
		return weekEnding;
	}

	public void setWeekEnding(Date weekEnding) {
		this.weekEnding = weekEnding;
	}

	public String getWeekEndingString() {
		return weekEndingString;
	}

	public void setWeekEndingString(String weekEndingString) {
		this.weekEndingString = weekEndingString;
	}

	public ExcelMTTRDTO() {

	}

}
