/**
 * 
 */
package com.nike.reporting.model.dto;

/**
 * @author Sachin_Ainapure
 * 
 */
public class MTBFPerDayDTO {

	String applicationName;
	double totalDownTime;
	double totalUpTime;
	double totalUpTimePercent;
	double totalDownTimePercent;
	int totalUnplannedOutages;
	double mtbf;
	int mtbfDays;
	double averageDownTimeHours;

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName
	 *            the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the totalDownTime
	 */
	public double getTotalDownTime() {
		return totalDownTime;
	}

	/**
	 * @param totalDownTime
	 *            the totalDownTime to set
	 */
	public void setTotalDownTime(double totalDownTime) {
		this.totalDownTime = totalDownTime;
	}

	/**
	 * @return the totalUpTime
	 */
	public double getTotalUpTime() {
		return totalUpTime;
	}

	/**
	 * @param d
	 *            the totalUpTime to set
	 */
	public void setTotalUpTime(double d) {
		this.totalUpTime = d;
	}

	/**
	 * @return the totalUpTimePercent
	 */
	public double getTotalUpTimePercent() {
		return totalUpTimePercent;
	}

	/**
	 * @param totalUpTimePercent
	 *            the totalUpTimePercent to set
	 */
	public void setTotalUpTimePercent(double totalUpTimePercent) {
		this.totalUpTimePercent = totalUpTimePercent;
	}

	/**
	 * @return the totalDownTimePercent
	 */
	public double getTotalDownTimePercent() {
		return totalDownTimePercent;
	}

	/**
	 * @param totalDownTimePercent
	 *            the totalDownTimePercent to set
	 */
	public void setTotalDownTimePercent(double totalDownTimePercent) {
		this.totalDownTimePercent = totalDownTimePercent;
	}

	/**
	 * @return the totalUnplannedOutages
	 */
	public int getTotalUnplannedOutages() {
		return totalUnplannedOutages;
	}

	/**
	 * @param totalUnplannedOutages
	 *            the totalUnplannedOutages to set
	 */
	public void setTotalUnplannedOutages(int totalUnplannedOutages) {
		this.totalUnplannedOutages = totalUnplannedOutages;
	}

	/**
	 * @return the mtbf
	 */
	public double getMtbf() {
		return mtbf;
	}

	/**
	 * @param mtbf
	 *            the mtbf to set
	 */
	public void setMtbf(double mtbf) {
		this.mtbf = mtbf;
	}

	/**
	 * @return the mtbfDays
	 */
	public int getMtbfDays() {
		return mtbfDays;
	}

	/**
	 * @param mtbfDays
	 *            the mtbfDays to set
	 */
	public void setMtbfDays(int mtbfDays) {
		this.mtbfDays = mtbfDays;
	}

	/**
	 * @return the averageDownTimeHours
	 */
	public double getAverageDownTimeHours() {
		return averageDownTimeHours;
	}

	/**
	 * @param averageDownTimeHours
	 *            the averageDownTimeHours to set
	 */
	public void setAverageDownTimeHours(double averageDownTimeHours) {
		this.averageDownTimeHours = averageDownTimeHours;
	}

}
