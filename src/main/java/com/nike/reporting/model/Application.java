/**
* <h1>Application Model Object</h1>
* NRT exposes a couple of application to select for User.
* All the applications are fetched from the database as Application Objects.
* @author  Mital Gadoya
* @version 1.0
* @since   2015-01-01
*/
package com.nike.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="application")
public class Application {

	@Id
	@Column(name="app_id")
	private Integer id;
	
	@Column(name="app_name")
	private String appName;
	
	@ManyToOne
	@JoinColumn(name = "capability_id")
	private Capability capability;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}

	@Override
	public int hashCode(){
		return id;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Application && this.id == ((Application)o).id)
			return true;
		return false;
	}
	
}
