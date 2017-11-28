package com.nike.reporting.ui;

import java.util.List;

import com.nike.reporting.model.Application;
import com.nike.reporting.model.Priority;
import com.nike.reporting.model.ProblemManagementStatus;

/**
 * 
 * Problem Management UI DTO.
 *
 */
public class ProblemManagementUI {

	private Integer id;
	
	private String innovationTitle;
	
	private String problemStatement;

	private String solution;

	private Application application;
	
	private String itBenefit;
	
	private String businessBenefit;

	private ProblemManagementStatus status;
	
	private Priority priority;
	
	private Double effortHours;
	
	private String ideatedBy;

	private String implementedBy;
	
	private String sme;
	
	private String initiationDate;
	
	private String completionDate;
	
	private Integer completionPercentage;
	
	private String comments;
	
	private List<String> benefitTypes;
	
	private Integer dollarSaving;
	
	private Integer incidentReduction;
	
	private Integer effortSaving;
	
	private String userId;


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

	public String getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(String initiationDate) {
		this.initiationDate = initiationDate;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
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

	public List<String> getBenefitTypes() {
		return benefitTypes;
	}

	public void setBenefitTypes(List<String> benefitTypes) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public ProblemManagementUI(Integer id, String innovationTitle,
			String problemStatement, String solution, Application application,
			String itBenefit, String businessBenefit,
			ProblemManagementStatus status, Priority priority,
			Double effortHours, String ideatedBy, String implementedBy,
			String sme, String initiationDate, String completionDate,
			Integer completionPercentage, String comments,
			List<String> benefitTypes, Integer dollarSaving,
			Integer incidentReduction, Integer effortSaving, String userId) {
		super();
		this.id = id;
		this.innovationTitle = innovationTitle;
		this.problemStatement = problemStatement;
		this.solution = solution;
		this.application = application;
		this.itBenefit = itBenefit;
		this.businessBenefit = businessBenefit;
		this.status = status;
		this.priority = priority;
		this.effortHours = effortHours;
		this.ideatedBy = ideatedBy;
		this.implementedBy = implementedBy;
		this.sme = sme;
		this.initiationDate = initiationDate;
		this.completionDate = completionDate;
		this.completionPercentage = completionPercentage;
		this.comments = comments;
		this.benefitTypes = benefitTypes;
		this.dollarSaving = dollarSaving;
		this.incidentReduction = incidentReduction;
		this.effortSaving = effortSaving;
		this.userId = userId;
	}

	public ProblemManagementUI() {
	}

}
