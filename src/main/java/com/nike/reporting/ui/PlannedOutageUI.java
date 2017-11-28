/**
* <h1>Planned Outage UI Object</h1>
* Planned Outage UI defines variables to capture for planned outages from UI.
* These variables are validated and mapped to Outage object for database entry.
* @author  Mital Gadoya
* @version 1.0
* @since   2015-01-01
*/
package com.nike.reporting.ui;

import java.util.List;

import com.nike.reporting.model.ReportLookup;

public class PlannedOutageUI {

	private Integer id;
	
	private Character outageType;

	private List<String> applications;
	
	private Boolean outageRequired;
	
	private String snowId;

	private String description;

	private String deploymentStartDate;

	private String deploymentEndDate;

	private ReportLookup approvedBy;

	private String approvalDate;

	private String scope;
	
	private String userId;

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

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public Boolean getOutageRequired() {
		return outageRequired;
	}

	public void setOutageRequired(Boolean outageRequired) {
		this.outageRequired = outageRequired;
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

	public ReportLookup getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ReportLookup approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
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

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PlannedOutageUI(){
		
	}

	public PlannedOutageUI(Integer id, Character outageType,
			List<String> applications, Boolean outageRequired,
			String snowId, String description, String deploymentStartDate,
			String deploymentEndDate, ReportLookup approvedBy, String approvalDate,
			String scope, String userId) {
		super();
		this.id = id;
		this.outageType = outageType;
		this.applications = applications;
		this.outageRequired = outageRequired;
		this.snowId = snowId;
		this.description = description;
		this.deploymentStartDate = deploymentStartDate;
		this.deploymentEndDate = deploymentEndDate;
		this.approvedBy = approvedBy;
		this.approvalDate = approvalDate;
		this.scope = scope;
		this.userId = userId;
	}
}
