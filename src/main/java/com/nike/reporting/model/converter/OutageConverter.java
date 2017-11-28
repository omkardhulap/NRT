package com.nike.reporting.model.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nike.reporting.model.Application;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Capability;
import com.nike.reporting.model.Defect;
import com.nike.reporting.model.Enhancement;
import com.nike.reporting.model.Incident;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.PointOfFailure;
import com.nike.reporting.model.dto.OutageDTO;
import com.nike.reporting.util.ReportingConstants;

public class OutageConverter {

	public OutageDTO convertFromObjectToDTO(Outage outage) {
		OutageDTO outageDTO = new OutageDTO();
		if (outage != null) {
			outageDTO.setId(outage.getId());
			outageDTO.setApprovalDate(outage.getApprovalDate());
			outageDTO.setDeploymentStartDate(outage.getDeploymentStartDate());
			outageDTO.setDeploymentEndDate(outage.getDeploymentEndDate());

			if (outage.getApprovedBy() != null) {
				outageDTO.setApprovedBy(outage.getApprovedBy().getName());
			}

			outageDTO.setBusinessAffected(outage.getBusinessAffected());
			outageDTO.setDueTo(outage.getDueTo());
			outageDTO.setExecutiveSummary(outage.getExecutiveSummary());
			if (outage.getOutageType() == ReportingConstants.PLANNED_OUTAGE_TYPE_CHAR) {
				outageDTO.setOutageType(ReportingConstants.PLANNED_OUTAGE_STRING);
			} else if (outage.getOutageType() == ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR) {
				outageDTO.setOutageType(ReportingConstants.UNPLANNED_OUTAGE_STRING);
			}

			if (outage.getDeploymentStartDate() != null && outage.getDeploymentEndDate() != null) {
				long diff = outage.getDeploymentEndDate().getTime() - outage.getDeploymentStartDate().getTime();
				long diffMin = diff / (60 * 1000);
				outageDTO.setOutageDuration((int) diffMin);

				int diffhrs = (int) (diff / (60 * 1000) / 60);
				int remainingMin = (int) (diffMin - (diffhrs * 60));

				// Create Hours Min String for outage duration
				String outageDurationInHrsMinsStr;
				if (diffhrs > 0 && remainingMin > 0) {
					outageDurationInHrsMinsStr = diffhrs + " hours, " + remainingMin + " minutes";
				} else if (diffhrs > 0 && remainingMin <= 0) {
					outageDurationInHrsMinsStr = diffhrs + " hours ";
				} else {
					outageDurationInHrsMinsStr = remainingMin + " minutes";
				}
				outageDTO.setOutageDurationInHrsMins(outageDurationInHrsMinsStr);
			}
			String strApps = "";
			StringBuffer sbApps = new StringBuffer(strApps);

			String strcapabilities = "";
			StringBuffer sbcapabilities = new StringBuffer(strcapabilities);
			Capability capability = null;

			if (outage.getApplications() != null) {
				for (Application application : outage.getApplications()) {
					sbApps.append(application.getAppName() + " ,");

					capability = application.getCapability();
					if (capability != null) {
						if (!sbcapabilities.toString().contains(capability.getDescription())) {
							sbcapabilities.append(capability.getDescription() + " ,");
						}
					}
				}

				strApps = sbApps.toString().replaceAll(" ,$", "");
				strcapabilities = sbcapabilities.toString().replaceAll(" ,$", "");
				outageDTO.setApplications(strApps);
				outageDTO.setAssociated_capabilities(strcapabilities);
			}

			String strPofs = "";
			StringBuffer sbPofs = new StringBuffer(strPofs);

			if (outage.getPointOfFailures() != null) {
				for (PointOfFailure pointOfFailure : outage.getPointOfFailures()) {
					sbPofs.append(pointOfFailure.getPointOfFailureDesc() + " ,");
				}

				strPofs = sbPofs.toString().replaceAll(" ,$", "");
				outageDTO.setPointOfFailure(strPofs);
			}

			outageDTO.setDescription(outage.getDescription());
			if (outage.getOutageRequired() != null) {
				if (outage.getOutageRequired() == true) {
					outageDTO.setOutageRequired(ReportingConstants.OUTAGE_REQUIRED_YES);
				} else if (outage.getOutageRequired() == false) {
					outageDTO.setOutageRequired(ReportingConstants.OUTAGE_REQUIRED_NO);
				}
			}

			if (outage.getPriority() != null) {
				outageDTO.setPriority(outage.getPriority().getDescription());
			} else {
				outageDTO.setPriority("NA");
			}

			if (outage.getSeverity() != null) {
				outageDTO.setSeverity(outage.getSeverity().getDescription());
			} else {
				outageDTO.setSeverity("NA");
			}

			outageDTO.setScope(outage.getScope());
			outageDTO.setStOwner(outage.getStOwner());
			String strSnowIds = "";
			StringBuffer sbSnowId = new StringBuffer(strSnowIds);

			if (outage.getIncidentList() != null) {
				for (Incident incident : outage.getIncidentList()) {
					sbSnowId.append(incident.getSnowId() + " ,");
				}
			}

			if (outage.getDefectList() != null) {
				for (Defect defect : outage.getDefectList()) {
					sbSnowId.append(defect.getSnowId() + " ,");
				}
			}

			if (outage.getEnhancementList() != null) {
				for (Enhancement enhancement : outage.getEnhancementList()) {
					sbSnowId.append(enhancement.getSnowId() + " ,");
				}
			}

			strSnowIds = sbSnowId.toString().replaceAll(" ,$", "");
			outageDTO.setSnowIds(strSnowIds);

			outageDTO.setAarDate(outage.getAarDate());
			outageDTO.setAarOwner(outage.getAarOwner());
			outageDTO.setDatabase(outage.getDatabase());
			outageDTO.setPlatform(outage.getPlatform());
			outageDTO.setVendorAccountable(outage.getVendorAccountable());
			outageDTO.setTechnicalIssues(outage.getTechnicalIssues());
			outageDTO.setResolution(outage.getResolution());
			outageDTO.setRootCause(outage.getRootCause());
			outageDTO.setVendorAccountableName(outage.getVendorAccountableName());

			if (outage.getAuditInfo() != null) {
				AuditInfo auditInfo = outage.getAuditInfo();
				outageDTO.setCreatedBy(auditInfo.getCreatedBy());
				outageDTO.setCreatedDate(auditInfo.getCreatedDate());
				outageDTO.setUpdatedBy(auditInfo.getUpdatedBy());
				outageDTO.setUpdatedDate(auditInfo.getUpdatedDate());
			}

			// only for unplanned outage
			if (outage.getOutageType() == ReportingConstants.UNPLANNED_OUTAGE_TYPE_CHAR) {
				outageDTO.setReportedOn(outage.getIncidentList().get(0).getReportedOn());
				outageDTO.setReportedBy(outage.getIncidentList().get(0).getReportedBy());
			}

		}

		return outageDTO;

	}

	public List<OutageDTO> convertFromObjectToDTOList(List<Outage> outageList) {
		List<OutageDTO> outageDTOList = new ArrayList<OutageDTO>();
		OutageDTO outageDTO = new OutageDTO();
		if (outageList != null) {
			Iterator<Outage> outageItr = outageList.iterator();
			while (outageItr.hasNext()) {
				Outage outage = outageItr.next();
				outageDTO = convertFromObjectToDTO(outage);
				outageDTOList.add(outageDTO);
			}
		}

		return outageDTOList;

	}
}
