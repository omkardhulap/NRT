/**
 * 
 */
package com.nike.reporting.ui;

import com.nike.reporting.model.Application;
import com.nike.reporting.model.Category;
import com.nike.reporting.model.Complexity;
import com.nike.reporting.model.Priority;

/**
 * @author Sachin_Ainapure
 * 
 */
public class EffortManagementUI {

	private Integer id;

	private Category category;

	private String effortCategoryActivityDesc;

	private Priority priority;

	private Complexity complexity;

	private Application application;

	private String snowNumber;

	private String effortDescription;

	private String effortHours;

	private String effortDate;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getEffortCategoryActivityDesc() {
		return effortCategoryActivityDesc;
	}

	public void setEffortCategoryActivityDesc(String effortCategoryActivityDesc) {
		this.effortCategoryActivityDesc = effortCategoryActivityDesc;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Complexity getComplexity() {
		return complexity;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
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

	public String getEffortDate() {
		return effortDate;
	}

	public void setEffortDate(String effortDate) {
		this.effortDate = effortDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
