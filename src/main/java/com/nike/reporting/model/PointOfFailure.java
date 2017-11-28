/**
 * 
 */
package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sachin_Ainapure
 * 
 */
@Entity
@Table(name = "pointoffailure")
public class PointOfFailure {

	@Id
	@Column(name = "idpointOfFailure")
	private Integer id;

	@Column(name = "description")
	private String pointOfFailureDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPointOfFailureDesc() {
		return pointOfFailureDesc;
	}

	public void setPointOfFailureDesc(String pointOfFailureDesc) {
		this.pointOfFailureDesc = pointOfFailureDesc;
	}

}
