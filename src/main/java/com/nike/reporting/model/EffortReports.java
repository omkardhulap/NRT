/**
 * 
 */
package com.nike.reporting.model;

/**
 * @author Sachin_Ainapure
 *
 */
public class EffortReports {
	
	private String owner;
	private Integer weekNumber;
	private double totalEffortHours;
	private String weekStartDate;
	private String weekEndDate;
	private String applicationName;
	private String capabilityName;
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Integer getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}
	public double getTotalEffortHours() {
		return totalEffortHours;
	}
	public void setTotalEffortHours(double totalEffortHours) {
		this.totalEffortHours = totalEffortHours;
	}
	public String getWeekStartDate() {
		return weekStartDate;
	}
	public void setWeekStartDate(String weekStartDate) {
		this.weekStartDate = weekStartDate;
	}
	public String getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getCapabilityName() {
		return capabilityName;
	}
	public void setCapabilityName(String capabilityName) {
		this.capabilityName = capabilityName;
	}
	
	
	@Override
	public String toString() {
		return "EffortReports [owner=" + owner + ", weekNumber=" + weekNumber
				+ ", totalEffortHours=" + totalEffortHours + ", weekStartDate="
				+ weekStartDate + ", weekEndDate=" + weekEndDate
				+ ", applicationName=" + applicationName + ", capabilityName="
				+ capabilityName + "]";
	}
	
	

}
