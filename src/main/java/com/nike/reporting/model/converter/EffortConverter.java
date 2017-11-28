package com.nike.reporting.model.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.AuditInfo;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.EffortReports;
import com.nike.reporting.model.dto.EffortDTO;
import com.nike.reporting.model.dto.ExcelEffortDTO;
import com.nike.reporting.ui.EffortManagementUI;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */

public class EffortConverter {

	public EffortDTO convertFromObjectToDTO(Effort effort) {
		EffortDTO effortDTO = new EffortDTO();
		effortDTO.setId(effort.getId());

		if (effort.getCategory() != null && effort.getCategory().getDescription().trim().length() > 0) {
			effortDTO.setCategory(effort.getCategory().getDescription());
		} else {
			effortDTO.setCategory("NA");
		}

		if (effort.getPriority() != null && effort.getPriority().getDescription().trim().length() > 0) {
			effortDTO.setPriority(effort.getPriority().getDescription());
		} else {
			effortDTO.setPriority("NA");
		}

		if (effort.getComplexity() != null && effort.getComplexity().getDescription().trim().length() > 0) {
			effortDTO.setComplexity(effort.getComplexity().getDescription());
		} else {
			effortDTO.setComplexity("NA");
		}

		if (effort.getApplication() != null && effort.getApplication().getAppName().trim().length() > 0) {
			effortDTO.setApplication(effort.getApplication().getAppName());
		} else {
			effortDTO.setApplication("NA");
		}

		if (effort.getSnowNumber() != null && effort.getSnowNumber().trim().length() > 0) {
			effortDTO.setSnowNumber(effort.getSnowNumber());
		} else {
			effortDTO.setSnowNumber("NA");
		}

		if (effort.getEffortDescription() != null && effort.getEffortDescription().trim().length() > 0) {
			effortDTO.setEffortDescription(effort.getEffortDescription());
		} else {
			effortDTO.setEffortDescription("NA");
		}

		if (effort.getEffortHours() != null && effort.getEffortHours().trim().length() > 0) {
			effortDTO.setEffortHours(effort.getEffortHours());
		} else {
			effortDTO.setEffortHours("0.00");
		}

		if (effort.getEffortDate() != null && effort.getEffortDate().toString().length() > 0) {
			effortDTO.setEffortDate(effort.getEffortDate());
		}

		if (effort.getAuditInfo() != null) {
			AuditInfo auditInfo = effort.getAuditInfo();
			effortDTO.setCreatedBy(auditInfo.getCreatedBy());
			effortDTO.setCreatedDate(auditInfo.getCreatedDate());
			effortDTO.setUpdatedBy(auditInfo.getUpdatedBy());
			effortDTO.setUpdatedDate(auditInfo.getUpdatedDate());
		}
		return effortDTO;
	}

	public List<EffortDTO> convertFromObjectToDTOList(List<Effort> effortList) {
		List<EffortDTO> effortDTOList = new ArrayList<EffortDTO>();
		if (effortList != null && !effortList.isEmpty()) {
			for (Effort effort : effortList) {
				effortDTOList.add(convertFromObjectToDTO(effort));
			}
		}
		return effortDTOList;
	}

	public Effort convertFromExcelDTOToObject(ExcelEffortDTO excelEffortDTO) throws DateParsingException {
		Effort effort = new Effort();

		if (excelEffortDTO.getCategory() != null && excelEffortDTO.getCategory().trim().length() > 0) {
			// effort.setCategory(CommonsUtil.getCategoryByDescription(excelEffortDTO.getCategory()).get(0));
			effort.setCategory(CommonsUtil.getCategoryByDescription(excelEffortDTO.getCategory()));
		}

		if (excelEffortDTO.getPriority() != null && excelEffortDTO.getPriority().trim().length() > 0) {
			// effort.setPriority(CommonsUtil.getPriorityByDescription(excelEffortDTO.getPriority()).get(0));
			effort.setPriority(CommonsUtil.getPriorityByDescription(excelEffortDTO.getPriority()));
		}

		if (excelEffortDTO.getComplexity() != null && excelEffortDTO.getComplexity().trim().length() > 0) {
			// effort.setComplexity(CommonsUtil.getComplexityByDescription(excelEffortDTO.getComplexity()).get(0));
			effort.setComplexity(CommonsUtil.getComplexityByDescription(excelEffortDTO.getComplexity()));
		}

		if (excelEffortDTO.getApplication() != null && excelEffortDTO.getApplication().trim().length() > 0) {
			// effort.setApplication(CommonsUtil.getApplicationByName(excelEffortDTO.getApplication()).get(0));
			effort.setApplication(CommonsUtil.getApplicationByName(excelEffortDTO.getApplication()));
		}

		if (excelEffortDTO.getSnowNumber() != null && excelEffortDTO.getSnowNumber().trim().length() > 0) {
			effort.setSnowNumber(excelEffortDTO.getSnowNumber());
		}

		if (excelEffortDTO.getEffortDescription() != null && excelEffortDTO.getEffortDescription().trim().length() > 0) {
			effort.setEffortDescription(excelEffortDTO.getEffortDescription());
		}

		if (excelEffortDTO.getEffortHours() != null && excelEffortDTO.getEffortHours().trim().length() > 0) {
			effort.setEffortHours(excelEffortDTO.getEffortHours());
		}

		if (excelEffortDTO.getEffortDate() != null && excelEffortDTO.getEffortDate().trim().length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
			try {
				effort.setEffortDate(sdf.parse(excelEffortDTO.getEffortDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new DateParsingException(e, ErrorMessages.DATE_PARSING_EXCEPTION);
			}
		}

		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setCreatedBy(excelEffortDTO.getCreatedBy());
		auditInfo.setCreatedDate(excelEffortDTO.getCreatedDate());
		auditInfo.setUpdatedBy(excelEffortDTO.getUpdatedBy());
		auditInfo.setUpdatedDate(excelEffortDTO.getUpdatedDate());

		effort.setAuditInfo(auditInfo);

		return effort;
	}

	public List<Effort> convertFromExcelDTOToObjectList(List<ExcelEffortDTO> excelEffortDTOList) throws DateParsingException {
		List<Effort> effortList = new ArrayList<Effort>();
		if (excelEffortDTOList != null && !excelEffortDTOList.isEmpty()) {
			for (ExcelEffortDTO excelEffortDTO : excelEffortDTOList) {
				effortList.add(convertFromExcelDTOToObject(excelEffortDTO));
			}
		}
		return effortList;
	}

	public EffortManagementUI populateEffortForUI(Effort e) {

		if (e != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			EffortManagementUI effortUI = new EffortManagementUI();
			effortUI.setApplication(e.getApplication());
			effortUI.setCategory(e.getCategory());
			if (e.getCategory() != null && e.getCategory().getActivityDescription() != null) {
				effortUI.setEffortCategoryActivityDesc(e.getCategory().getActivityDescription());
			}
			effortUI.setComplexity(e.getComplexity());
			if (e.getAuditInfo() != null) {
				effortUI.setCreatedBy(e.getAuditInfo().getCreatedBy());
				effortUI.setCreatedDate(sdf.format(e.getAuditInfo().getCreatedDate()));
				effortUI.setUpdatedBy(e.getAuditInfo().getUpdatedBy());
				effortUI.setUpdatedDate(sdf.format(e.getAuditInfo().getUpdatedDate()));
			}
			if (e.getEffortDate() != null) {
				effortUI.setEffortDate(sdf.format(e.getEffortDate()));
			}
			effortUI.setEffortDescription(e.getEffortDescription());
			effortUI.setEffortHours(e.getEffortHours());
			effortUI.setId(e.getId());
			effortUI.setPriority(e.getPriority());
			effortUI.setSnowNumber(e.getSnowNumber());
			return effortUI;
		}
		return null;
	}
	
	public List<EffortReports> convertToEffortReports(List effortDataFromSQL){
		List<EffortReports> effortReportsList = new ArrayList<EffortReports>();
		for(Object object : effortDataFromSQL)
        {
           Map row = (Map)object;
           EffortReports effortReports = new EffortReports();
           effortReports.setApplicationName(row.get("APP_NAME").toString());
           effortReports.setCapabilityName(row.get("CAPABILITY_DESC").toString());
           effortReports.setOwner(row.get("CRTD_BY").toString());
           effortReports.setTotalEffortHours(Double.parseDouble(row.get("TOTALHOURS").toString()));
           effortReports.setWeekEndDate(row.get("WEEKEND").toString());
           effortReports.setWeekNumber(Integer.parseInt(row.get("WEEKNUMBER").toString()));
           effortReports.setWeekStartDate(row.get("WEEKSTART").toString());
           System.out.println(effortReports);
           
           effortReportsList.add(effortReports);
        }
		return effortReportsList;
	}
	
}
