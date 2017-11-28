/**
 * <h1>Unplanned Outage UI Object</h1>
 * Unplanned Outage UI defines variables to capture for unplanned outages from UI.
 * These variables are validated and mapped to Outage object for database entry.
 * @author  Mital Gadoya
 * @version 1.0
 * @since   2015-01-01
 */
package com.nike.reporting.ui;

import java.util.List;

import com.nike.reporting.model.Priority;
import com.nike.reporting.model.Severity;

public class UnplannedOutageUI {

	private Integer id;

	private Character outageType;

	private String snowId;

	private String description;

	private Priority priority;

	private List<String> applications;

	private String reportedBy;

	private String reportedOn;

	private String stOwner;

	private String dueTo;

	private String executiveSummary;

	private String businessAffected;

	private String technicalIssues;

	private String resolution;

	private String rootCause;

	private String vendorAccountable;

	private String aarOwner;

	private String aarDate;

	private String database;

	private String platform;

	private String deploymentStartDate;

	private String deploymentEndDate;

	private String userId;

	private List<String> pointOfFailures;

	private String vendorAccountableName;

	private Severity severity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Character getOutageType() {
		return outageType;
	}

	public void setOutageType(Character outageType) {
		this.outageType = outageType;
	}

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(String reportedOn) {
		this.reportedOn = reportedOn;
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

	public String getBusinessAffected() {
		return businessAffected;
	}

	public void setBusinessAffected(String businessAffected) {
		this.businessAffected = businessAffected;
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

	public String getAarOwner() {
		return aarOwner;
	}

	public void setAarOwner(String aarOwner) {
		this.aarOwner = aarOwner;
	}

	public String getAarDate() {
		return aarDate;
	}

	public void setAarDate(String aarDate) {
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

	public String getDeploymentStartDate() {
		return deploymentStartDate;
	}

	public void setDeploymentStartDate(String deploymentStartDate) {
		this.deploymentStartDate = deploymentStartDate;
	}

	public String getDeploymentEndDate() {
		return deploymentEndDate;
	}

	public void setDeploymentEndDate(String deploymentEndDate) {
		this.deploymentEndDate = deploymentEndDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getPointOfFailures() {
		return pointOfFailures;
	}

	public void setPointOfFailures(List<String> pointOfFailures) {
		this.pointOfFailures = pointOfFailures;
	}

	public String getVendorAccountableName() {
		return vendorAccountableName;
	}

	public void setVendorAccountableName(String vendorAccountableName) {
		this.vendorAccountableName = vendorAccountableName;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public UnplannedOutageUI() {

	}

	public UnplannedOutageUI(Integer id, Character outageType, String snowId, String description, Priority priority, List<String> applications, String reportedBy, String reportedOn, String stOwner,
			String dueTo, String executiveSummary, String businessAffected, String technicalIssues, String resolution, String rootCause, String vendorAccountable, String aarOwner, String aarDate,
			String database, String platform, String deploymentStartDate, String deploymentEndDate, String userId, List<String> pointOfFailures, String vendorAccountableName, Severity severity) {
		super();
		this.id = id;
		this.outageType = outageType;
		this.snowId = snowId;
		this.description = description;
		this.priority = priority;
		this.applications = applications;
		this.reportedBy = reportedBy;
		this.reportedOn = reportedOn;
		this.stOwner = stOwner;
		this.dueTo = dueTo;
		this.executiveSummary = executiveSummary;
		this.businessAffected = businessAffected;
		this.technicalIssues = technicalIssues;
		this.resolution = resolution;
		this.rootCause = rootCause;
		this.vendorAccountable = vendorAccountable;
		this.aarOwner = aarOwner;
		this.aarDate = aarDate;
		this.database = database;
		this.platform = platform;
		this.deploymentStartDate = deploymentStartDate;
		this.deploymentEndDate = deploymentEndDate;
		this.userId = userId;
		this.pointOfFailures = pointOfFailures;
		this.vendorAccountableName = vendorAccountableName;
		this.severity = severity;
	}

}
