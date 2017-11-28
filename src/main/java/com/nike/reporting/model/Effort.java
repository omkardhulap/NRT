package com.nike.reporting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Sachin_Ainapure
 * 
 */

@Entity
@Table(name = "trn_effort_tracker")
public class Effort {

	@Id
	@Column(name = "effort_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "effort_category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "priority_id")
	private Priority priority;

	@ManyToOne
	@JoinColumn(name = "complexity_id")
	private Complexity complexity;

	@ManyToOne
	@JoinColumn(name = "app_id")
	private Application application;

	@Column(name = "snow_number")
	private String snowNumber;

	@Column(name = "effort_description")
	private String effortDescription;

	@Column(name = "effort_hours")
	private String effortHours;

	@Column(name = "effort_date")
	private Date effortDate;

	@Embedded
	private AuditInfo auditInfo;

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

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public Date getEffortDate() {
		return effortDate;
	}

	public void setEffortDate(Date effortDate) {
		this.effortDate = effortDate;
	}

}
