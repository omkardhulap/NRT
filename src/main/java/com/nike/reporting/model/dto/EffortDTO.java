package com.nike.reporting.model.dto;

import java.util.Date;

/**
 * @author Sachin_Ainapure
 * 
 */
public class EffortDTO {

	private Integer id;

	private String category;

	private String priority;

	private String complexity;

	private String application;

	private String snowNumber;

	private String effortDescription;

	private String effortHours;

	private Date effortDate;

	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private Date updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getSnowNumber() {
		return snowNumber;
	}

	public void setSnowNumber(String snowNumber) {
		this.snowNumber = snowNumber;
	}

	public String getEffortDescription() {
		return effortDescription;
	}

	public void setEffortDescription(String effortDescription) {
		this.effortDescription = effortDescription;
	}

	public String getEffortHours() {
		return effortHours;
	}

	public void setEffortHours(String effortHours) {
		this.effortHours = effortHours;
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

	public Date getEffortDate() {
		return effortDate;
	}

	public void setEffortDate(Date effortDate) {
		this.effortDate = effortDate;
	}

}
