/**
* <h1>Capability Model Object</h1>
* NRT exposes a couple of capabilities to select for User.
* All the capabilities are fetched from the database as capabilities Objects.
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
@Table(name="capability")
public class Capability {

	@Id
	@Column(name="capability_id")
	private Integer id;
	
	@Column(name="capability_desc")
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
