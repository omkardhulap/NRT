package com.nike.reporting.model.dto;

import java.util.Date;

public class OutageDTO {
	private Integer id;
	private String applications;
	private String outageType;
	private String description;
	private String businessAffected;
	private String outageRequired;
	private Date deploymentStartDate;
	private Date deploymentEndDate;
	private String approvedBy;
	private Date approvalDate;
	private String scope;
	private String stOwner;
	private String dueTo;
	private String executiveSummary;
	private String priority;
	private String snowIds;
	private Integer outageDuration;
	private String aarOwner;
	private Date aarDate;
	private String database;
	private String platform;
	private String technicalIssues;
	private String resolution;
	private String rootCause;
	private String vendorAccountable;
	private Date reportedOn;
	private String reportedBy;
	private String outageDurationInHrsMins;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String associated_capabilities;

	private String vendorAccountableName;
	private String severity;
	private String pointOfFailure;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}

	public String getOutageType() {
		return outageType;
	}

	public void setOutageType(String outageType) {
		this.outageType = outageType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessAffected() {
		return businessAffected;
	}

	public void setBusinessAffected(String businessAffected) {
		this.businessAffected = businessAffected;
	}

	public String getOutageRequired() {
		return outageRequired;
	}

	public void setOutageRequired(String outageRequired) {
		this.outageRequired = outageRequired;
	}

	public Date getDeploymentStartDate() {
		return deploymentStartDate;
	}

	public void setDeploymentStartDate(Date deploymentStartDate) {
		this.deploymentStartDate = deploymentStartDate;
	}

	public Date getDeploymentEndDate() {
		return deploymentEndDate;
	}

	public void setDeploymentEndDate(Date deploymentEndDate) {
		this.deploymentEndDate = deploymentEndDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getStOwner() {
		return stOwner;
	}

	public void setStOwner(String stOwner) {
		this.stOwner = stOwner;
	}

	public String getDueTo() {
		return dueTo;
	}

	public void setDueTo(String dueTo) {
		this.dueTo = dueTo;
	}

	public String getExecutiveSummary() {
		return executiveSummary;
	}

	public void setExecutiveSummary(String executiveSummary) {
		this.executiveSummary = executiveSummary;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSnowIds() {
		return snowIds;
	}

	public void setSnowIds(String snowIds) {
		this.snowIds = snowIds;
	}

	public Integer getOutageDuration() {
		return outageDuration;
	}

	public void setOutageDuration(Integer outageDuration) {
		this.outageDuration = outageDuration;
	}

	public String getAarOwner() {
		return aarOwner;
	}

	public void setAarOwner(String aarOwner) {
		this.aarOwner = aarOwner;
	}

	public Date getAarDate() {
		return aarDate;
	}

	public void setAarDate(Date aarDate) {
		this.aarDate = aarDate;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTechnicalIssues() {
		return technicalIssues;
	}

	public void setTechnicalIssues(String technicalIssues) {
		this.technicalIssues = technicalIssues;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getVendorAccountable() {
		return vendorAccountable;
	}

	public void setVendorAccountable(String vendorAccountable) {
		this.vendorAccountable = vendorAccountable;
	}

	public Date getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(Date reportedOn) {
		this.reportedOn = reportedOn;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getOutageDurationInHrsMins() {
		return outageDurationInHrsMins;
	}

	public void setOutageDurationInHrsMins(String outageDurationInHrsMins) {
		this.outageDurationInHrsMins = outageDurationInHrsMins;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAssociated_capabilities() {
		return associated_capabilities;
	}

	public void setAssociated_capabilities(String associated_capabilities) {
		this.associated_capabilities = associated_capabilities;
	}

	public String getVendorAccountableName() {
		return vendorAccountableName;
	}

	public void setVendorAccountableName(String vendorAccountableName) {
		this.vendorAccountableName = vendorAccountableName;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getPointOfFailure() {
		return pointOfFailure;
	}

	public void setPointOfFailure(String pointOfFailure) {
		this.pointOfFailure = pointOfFailure;
	}

}
