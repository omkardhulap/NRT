/**
* <h1>Defect Model Object</h1>
* For the planned Outages, Snow ID is converted into defects if any of the snow id 
* starts with DFCTXXXXXXX. This class defines variables for Defect Object.
* @author  Mital Gadoya
* @version 1.0
* @since   2015-01-01
*/
package com.nike.reporting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="defect")
public class Defect {

	@Id
	@Column(name="defect_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="snow_id")
	private String snowId;
	
	/*@Column(name="desc")
	private String description;*/
	
	@Column(name="reported_by")
	private String reportedBy;
	
	@Column(name="reported_on")
	private Date reportedOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

	/*public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}*/

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public Date getReportedOn() {
		return reportedOn;
	}

	public void setReportedOn(Date reportedOn) {
		this.reportedOn = reportedOn;
	}

	@Override
	public String toString(){
		//return "id="+id+", snowId="+snowId+", description="+description +", reportedBy=" + reportedBy + ", reportedOn=" + reportedOn;
		return "id="+id+", snowId="+snowId +", reportedBy=" + reportedBy + ", reportedOn=" + reportedOn;
	}
}
