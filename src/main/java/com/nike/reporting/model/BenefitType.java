package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Benefit Type object model.
 *
 */
@Entity
@Table(name="benefit_type")
public class BenefitType {

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="value")
	private String value;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
