package com.nike.reporting.model;

public class MTTRAnalysis {

	private Integer mttrId;
	private String assignmentgroup;
	private String priority;
	private int pes_sla;
	private float average_hrs;
	private int total_incidents;
	private float lt_sla;
	private float gt_sla;
	private float percentSLA_achieved;

	public MTTRAnalysis() {

	}

	public Integer getMttrId() {
		return mttrId;
	}

	public void setMttrId(Integer mttrId) {
		this.mttrId = mttrId;
	}

	public String getAssignmentgroup() {
		return assignmentgroup;
	}

	public void setAssignmentgroup(String assignmentgroup) {
		this.assignmentgroup = assignmentgroup;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getPes_sla() {
		return pes_sla;
	}

	public void setPes_sla(int pes_sla) {
		this.pes_sla = pes_sla;
	}

	public float getAverage_hrs() {
		return average_hrs;
	}

	public void setAverage_hrs(float average_hrs) {
		this.average_hrs = average_hrs;
	}

	public int getTotal_incidents() {
		return total_incidents;
	}

	public void setTotal_incidents(int total_incidents) {
		this.total_incidents = total_incidents;
	}

	public float getLt_sla() {
		return lt_sla;
	}

	public void setLt_sla(float lt_sla) {
		this.lt_sla = lt_sla;
	}

	public float getGt_sla() {
		return gt_sla;
	}

	public void setGt_sla(float gt_sla) {
		this.gt_sla = gt_sla;
	}

	public float getPercentSLA_achieved() {
		return percentSLA_achieved;
	}

	public void setPercentSLA_achieved(float percentSLA_achieved) {
		this.percentSLA_achieved = percentSLA_achieved;
	}

}
