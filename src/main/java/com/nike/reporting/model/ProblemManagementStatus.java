package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Problem Management Status object model.
 *
 */
@Entity
@Table(name="problem_management_status")
public class ProblemManagementStatus {

	@Id
	@Column(name="status_id")
	private Integer id;
	
	@Column(name="status_desc")
	private String description;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
