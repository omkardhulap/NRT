/**
 * <h1>Outage Model Object</h1>
 * Outage.java defines variables to be captured for both planned and unplanned outages.
 * For the planned Outages, Snow ID is converted into Incidents, enhancements and 
 * defects and captured into their respective tables.
 * For the unplanned Outages, Incident Id is captured as Incident snow ID and per 
 * unplanned outage an entry will be recorded in the Incident table. 
 * @author  Mital Gadoya
 * @version 1.0
 * @since   2015-01-01
 */
package com.nike.reporting.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "outage")
public class Outage {

	@Id
	@Column(name = "outage_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "outage_application_link", joinColumns = { @JoinColumn(name = "outage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "app_id", nullable = false, updatable = false) })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Application> applications;

	@Column(name = "outage_type")
	private Character outageType;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "capabilty_id") private Capability capability;
	 */

	@Column(name = "short_desc")
	private String description;

	@Transient
	private String snowId;

	@Column(name = "business_affected")
	private String businessAffected;

	@Column(name = "outage_required")
	private Boolean outageRequired;

	@Column(name = "outage_start_tmst")
	private Date deploymentStartDate;

	@Column(name = "outage_end_tmst")
	private Date deploymentEndDate;

	@ManyToOne
	@JoinColumn(name = "dpmnt_approved_by")
	private ReportLookup approvedBy;

	@Column(name = "dpmnt_approv_date")
	private Date approvalDate;

	@Column(name = "scope")
	private String scope;

	@Column(name = "st_owner")
	private String stOwner;

	@Column(name = "due_to")
	private String dueTo;

	@Column(name = "executive_summary")
	private String executiveSummary;

	@ManyToOne
	@JoinColumn(name = "priority_id")
	private Priority priority;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "incident_outage_link", joinColumns = { @JoinColumn(name = "outage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "incident_id", nullable = false, updatable = false) })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Incident> incidentList;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "enhancement_outage_link", joinColumns = { @JoinColumn(name = "outage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "enh_id", nullable = false, updatable = false) })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Enhancement> enhancementList;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "defect_outage_link", joinColumns = { @JoinColumn(name = "outage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "defect_id", nullable = false, updatable = false) })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Defect> defectList;

	@Column(name = "technical_issues")
	private String technicalIssues;

	@Column(name = "resolution")
	private String resolution;

	@Column(name = "root_cause")
	private String rootCause;

	@Column(name = "vendor_accountable")
	private String vendorAccountable;

	@Column(name = "aar_owner")
	private String aarOwner;

	@Column(name = "aar_date")
	private Date aarDate;

	@Column(name = "outage_db")
	private String database;

	@Column(name = "platform")
	private String platform;

	@Column(name = "vendor_accountable_name")
	private String vendorAccountableName;

	@Embedded
	private AuditInfo auditInfo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Outage_POF_Link", joinColumns = { @JoinColumn(name = "outage_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "pof_id", nullable = false, updatable = false) })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PointOfFailure> pointOfFailures;

	@ManyToOne
	@JoinColumn(name = "severity_id")
	private Severity severity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * public Capability getCapability() { return capability; }
	 * 
	 * public void setCapability(Capability capability) { this.capability =
	 * capability; }
	 */

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Character getOutageType() {
		return outageType;
	}

	public void setOutageType(Character outageType) {
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

	public Boolean getOutageRequired() {
		return outageRequired;
	}

	public void setOutageRequired(Boolean outageRequired) {
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

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

	public ReportLookup getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ReportLookup approvedBy) {
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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public List<Incident> getIncidentList() {
		return incidentList;
	}

	public void setIncidentList(List<Incident> incidentList) {
		this.incidentList = incidentList;
	}

	public List<Enhancement> getEnhancementList() {
		return enhancementList;
	}

	public void setEnhancementList(List<Enhancement> enhancementList) {
		this.enhancementList = enhancementList;
	}

	public List<Defect> getDefectList() {
		return defectList;
	}

	public void setDefectList(List<Defect> defectList) {
		this.defectList = defectList;
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

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public List<PointOfFailure> getPointOfFailures() {
		return pointOfFailures;
	}

	public void setPointOfFailures(List<PointOfFailure> pointOfFailures) {
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

	public Outage() {

	}

	@Override
	public String toString() {
		return "id=" + id + ", description=" + description + ", deploymentStartDate=" + deploymentStartDate + ", deploymentEndDate=" + deploymentEndDate + ", approvedBy=" + approvedBy
				+ ", approvalDate=" + approvalDate + ", scope=" + scope + ", stOwner=" + stOwner + ", dueTo=" + dueTo + ", executiveSummary=" + executiveSummary + ", technicalIssues="
				+ technicalIssues + ", resolution=" + resolution + ", rootCause=" + rootCause + ", vendorAccountable=" + vendorAccountable + ", aarOwner=" + aarOwner + ", aarDate=" + aarDate
				+ ", database=" + database + ", platform=" + platform + ", Incident Details :" + incidentList;
	}

}
