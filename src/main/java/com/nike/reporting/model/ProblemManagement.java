package com.nike.reporting.model;

import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 * Problem Management object model
 *
 */
@Entity
@Table(name="problem_management")
public class ProblemManagement {

	@Id
	@Column(name="problem_management_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "innovation_title")	
	private String innovationTitle;
	
	@Column(name="problem_statement")
	private String problemStatement;
	
	@Column(name="solution")
	private String solution;

	@ManyToOne
	@JoinColumn(name = "app_id")
	private Application application;
	
	@Column(name="it_benefit")
	private String itBenefit;
	
	@Column(name="business_benefit")
	private String businessBenefit;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private ProblemManagementStatus status;

	@ManyToOne
	@JoinColumn(name = "priority_id")
	private Priority priority;
	
	@Column(name="effort_hours")
	private Double effortHours;
	
	@Column(name="ideated_by")
	private String ideatedBy;
	
	@Column(name="implemented_by")
	private String implementedBy;
	
	@Column(name="sme")
	private String sme;

	@Column(name="initiation_date")
	private Date initiationDate;
	
	@Column(name="completion_date")
	private Date completionDate;

	@Column(name="completion_percentage")
	private Integer completionPercentage;
	
	@Column(name="comments")
	private String comments;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "problem_management_benefit_type_link", 
	   joinColumns = { @JoinColumn(name = "problem_management_id", nullable = false, updatable = false) },
	   inverseJoinColumns = { @JoinColumn(name = "benefit_type_id",nullable = false, updatable = false)})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<BenefitType> benefitTypes;
	
	@Column(name="dollar_saving")
	private Integer dollarSaving;
	
	@Column(name="incident_reduction_per_month")
	private Integer incidentReduction;

	@Column(name="effort_saving_per_month")
	private Integer effortSaving;

	@Embedded
	private AuditInfo auditInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInnovationTitle() {
		return innovationTitle;
	}

	public void setInnovationTitle(String innovationTitle) {
		this.innovationTitle = innovationTitle;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getItBenefit() {
		return itBenefit;
	}

	public void setItBenefit(String itBenefit) {
		this.itBenefit = itBenefit;
	}

	public String getBusinessBenefit() {
		return businessBenefit;
	}

	public void setBusinessBenefit(String businessBenefit) {
		this.businessBenefit = businessBenefit;
	}

	public ProblemManagementStatus getStatus() {
		return status;
	}

	public void setStatus(ProblemManagementStatus status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Double getEffortHours() {
		return effortHours;
	}

	public void setEffortHours(Double effortHours) {
		this.effortHours = effortHours;
	}


	public String getIdeatedBy() {
		return ideatedBy;
	}

	public void setIdeatedBy(String ideatedBy) {
		this.ideatedBy = ideatedBy;
	}

	public String getImplementedBy() {
		return implementedBy;
	}

	public void setImplementedBy(String implementedBy) {
		this.implementedBy = implementedBy;
	}

	public String getSme() {
		return sme;
	}

	public void setSme(String sme) {
		this.sme = sme;
	}

	public Date getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Integer getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Integer completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<BenefitType> getBenefitTypes() {
		return benefitTypes;
	}

	public void setBenefitTypes(List<BenefitType> benefitTypes) {
		this.benefitTypes = benefitTypes;
	}

	public Integer getDollarSaving() {
		return dollarSaving;
	}

	public void setDollarSaving(Integer dollarSaving) {
		this.dollarSaving = dollarSaving;
	}

	public Integer getIncidentReduction() {
		return incidentReduction;
	}

	public void setIncidentReduction(Integer incidentReduction) {
		this.incidentReduction = incidentReduction;
	}

	public Integer getEffortSaving() {
		return effortSaving;
	}

	public void setEffortSaving(Integer effortSaving) {
		this.effortSaving = effortSaving;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	@Override
	public String toString() {
		return "ProblemManagement [id=" + id + ", innovationTitle="
				+ innovationTitle + ", problemStatement=" + problemStatement
				+ ", solution=" + solution + ", application=" + application
				+ ", itBenefit=" + itBenefit + ", businessBenefit="
				+ businessBenefit + ", status=" + status + ", priority="
				+ priority + ", effortHours=" + effortHours + ", ideatedBy="
				+ ideatedBy + ", implementedBy=" + implementedBy + ", sme="
				+ sme + ", initiationDate=" + initiationDate
				+ ", completionDate=" + completionDate
				+ ", completionPercentage=" + completionPercentage
				+ ", comments=" + comments + ", benefitTypes=" + benefitTypes
				+ ", dollarSaving=" + dollarSaving + ", incidentReduction="
				+ incidentReduction + ", effortSaving=" + effortSaving
				+ ", auditInfo=" + auditInfo + "]";
	}

}
