/**
* <h1>Priority Model Object</h1>
* Priority.java defines priorities for unplanned outage.
* At the moment, P1,P2,P3 and P4 priorities exist for Unplanned outages.These priorities
* exists in the database and mapped to Priority instances of these class in the NRT
* application.
* @author  Mital Gadoya
* @version 1.0
* @since   2015-01-01
*/
package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="priority")
public class Priority {

	@Id
	@Column(name="priority_id")
	private Integer id;
	
	@Column(name="priority_desc")
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
