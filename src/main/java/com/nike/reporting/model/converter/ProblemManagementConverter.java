package com.nike.reporting.model.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.BenefitType;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.dto.ProblemManagementDTO;

public class ProblemManagementConverter {

	public ProblemManagementDTO convertFromObjectToDTO(ProblemManagement pm) {
		ProblemManagementDTO problemManagementDTO = new ProblemManagementDTO();
		problemManagementDTO.setId(pm.getId());
		problemManagementDTO.setInnovationTitle(pm.getInnovationTitle());
		problemManagementDTO.setProblemStatement(pm.getProblemStatement());
		problemManagementDTO.setSolution(pm.getSolution());
		if(null != pm.getApplication()) {
			problemManagementDTO.setApplication(pm.getApplication().getAppName());
		}
		problemManagementDTO.setItBenefit(pm.getItBenefit());
		problemManagementDTO.setBusinessBenefit(pm.getBusinessBenefit());
		if(null != pm.getStatus()) {
			problemManagementDTO.setStatus(pm.getStatus().getDescription());
		}
		if(null != pm.getPriority()) {
			problemManagementDTO.setPriority(pm.getPriority().getDescription());
		}

		problemManagementDTO.setEffortHours(pm.getEffortHours());
		problemManagementDTO.setIdeatedBy(pm.getIdeatedBy());
		problemManagementDTO.setImplementedBy(pm.getImplementedBy());
		problemManagementDTO.setSme(pm.getSme());
		problemManagementDTO.setInitiationDate(pm.getInitiationDate());			
		problemManagementDTO.setCompletionDate(pm.getCompletionDate());
		problemManagementDTO.setCompletionPercentage(pm.getCompletionPercentage());
		problemManagementDTO.setComments(pm.getComments());

		if(null != pm.getBenefitTypes()) {
			List<String> strBenefitTypes = new ArrayList<String>();
			for(BenefitType benefitType:pm.getBenefitTypes()) {
				strBenefitTypes.add(benefitType.getValue());
			}
			problemManagementDTO.setBenefitTypes(StringUtils.join(strBenefitTypes, ", "));
		}
		problemManagementDTO.setDollarSaving(pm.getDollarSaving());
		problemManagementDTO.setIncidentReduction(pm.getIncidentReduction());
		problemManagementDTO.setEffortSaving(pm.getEffortSaving());

		if (pm.getAuditInfo() != null) {
			AuditInfo auditInfo = pm.getAuditInfo();
			problemManagementDTO.setCreatedBy(auditInfo.getCreatedBy());
			problemManagementDTO.setCreatedDate(auditInfo.getCreatedDate());
			problemManagementDTO.setUpdatedBy(auditInfo.getUpdatedBy());
			problemManagementDTO.setUpdatedDate(auditInfo.getUpdatedDate());
		}
		return problemManagementDTO;
	}

	public List<ProblemManagementDTO> convertFromObjectToDTOList(List<ProblemManagement> problemManagementList) {
		List<ProblemManagementDTO> problemManagementDTOList = new ArrayList<ProblemManagementDTO>();
		if (problemManagementList != null && !problemManagementList.isEmpty()) {
			for(ProblemManagement problemManagement:problemManagementList) {				
				problemManagementDTOList.add(convertFromObjectToDTO(problemManagement));
			}
		}
		return problemManagementDTOList;
	}
}
