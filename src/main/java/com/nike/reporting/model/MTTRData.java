package com.nike.reporting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sachin_Ainapure
 * 
 */

@Entity
@Table(name = "trn_mttrdata")
public class MTTRData {

	@Id
	@Column(name = "mttr_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "snowId")
	private String snowId;

	@Column(name = "category")
	private String category;

	@Column(name = "priority")
	private String priority;

	@Column(name = "escalation")
	private String escalation;

	@Column(name = "definition")
	private String definition;

	@Column(name = "value")
	private String value;

	@Column(name = "startDate_tmst")
	private Date startDate;

	@Column(name = "createdDate_tmst")
	private Date createdDate;

	@Column(name = "endDate_tmst")
	private Date endDate;

	@Column(name = "closedDate_tmst")
	private Date closedDate;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "assignmentGroup")
	private String assignmentGroup;

	@Column(name = "createToCloseCompleteDuration")
	private Double createToCloseCompleteDuration;

	@Column(name = "createToCloseCompleteInHours")
	private Double createToCloseCompleteInHours;

	@Column(name = "createToResolveDuration")
	private Double createToResolveDuration;

	@Column(name = "createToResolveInHours")
	private Double createToResolveInHours;

	@Column(name = "assignedTo")
	private String assignedTo;

	@Column(name = "isMTTRBreached")
	private Boolean isMTTRBreached;

	@Column(name = "mttr_breach_reason")
	private String mttrBreachReason;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "week_ending")
	private Date weekEnding;

	@Column(name = "resolutionCode")
	private String resolutionCode;

	@Column(name = "resolutionNotes")
	private String resolutionNotes;

	@Column(name = "configurationItem")
	private String configurationItem;

	@Embedded
	private AuditInfo auditInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSnowId() {
		return snowId;
	}

	public void setSnowId(String snowId) {
		this.snowId = snowId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getEscalation() {
		return escalation;
	}

	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public Double getCreateToCloseCompleteDuration() {
		return createToCloseCompleteDuration;
	}

	public void setCreateToCloseCompleteDuration(Double createToCloseCompleteDuration) {
		this.createToCloseCompleteDuration = createToCloseCompleteDuration;
	}

	public Double getCreateToCloseCompleteInHours() {
		return createToCloseCompleteInHours;
	}

	public void setCreateToCloseCompleteInHours(Double createToCloseCompleteInHours) {
		this.createToCloseCompleteInHours = createToCloseCompleteInHours;
	}

	public Double getCreateToResolveDuration() {
		return createToResolveDuration;
	}

	public void setCreateToResolveDuration(Double createToResolveDuration) {
		this.createToResolveDuration = createToResolveDuration;
	}

	public Double getCreateToResolveInHours() {
		return createToResolveInHours;
	}

	public void setCreateToResolveInHours(Double createToResolveInHours) {
		this.createToResolveInHours = createToResolveInHours;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Boolean getIsMTTRBreached() {
		return isMTTRBreached;
	}

	public void setIsMTTRBreached(Boolean isMTTRBreached) {
		this.isMTTRBreached = isMTTRBreached;
	}

	public String getMttrBreachReason() {
		return mttrBreachReason;
	}

	public void setMttrBreachReason(String mttrBreachReason) {
		this.mttrBreachReason = mttrBreachReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getWeekEnding() {
		return weekEnding;
	}

	public void setWeekEnding(Date weekEnding) {
		this.weekEnding = weekEnding;
	}

	public String getResolutionCode() {
		return resolutionCode;
	}

	public void setResolutionCode(String resolutionCode) {
		this.resolutionCode = resolutionCode;
	}

	public String getResolutionNotes() {
		return resolutionNotes;
	}

	public void setResolutionNotes(String resolutionNotes) {
		this.resolutionNotes = resolutionNotes;
	}

	public String getConfigurationItem() {
		return configurationItem;
	}

	public void setConfigurationItem(String configurationItem) {
		this.configurationItem = configurationItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((snowId == null) ? 0 : snowId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MTTRData other = (MTTRData) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (snowId == null) {
			if (other.snowId != null) {
				return false;
			}
		} else if (!snowId.equals(other.snowId)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		return true;
	}

}
