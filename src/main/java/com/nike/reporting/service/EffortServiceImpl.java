/**
 * 
 */
package com.nike.reporting.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.EffortDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Effort;
import com.nike.reporting.model.EffortReports;
import com.nike.reporting.model.SearchEffort;
import com.nike.reporting.model.converter.EffortConverter;
import com.nike.reporting.model.dto.ExcelEffortDTO;
import com.nike.reporting.util.CommonsUtil;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */
public class EffortServiceImpl implements EffortService {
	private static final Logger logger = LoggerFactory.getLogger(EffortServiceImpl.class);
	private EffortDAO effortDAO;

	public EffortDAO getEffortDAO() {
		return effortDAO;
	}

	public void setEffortDAO(EffortDAO effortDAO) {
		this.effortDAO = effortDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.service.EffortService#addEffort(com.nike.reporting
	 * .model.Effort)
	 */
	@Override
	@Transactional
	public void addEffort(Effort effort) {
		this.effortDAO.addEffort(effort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.service.EffortService#updateEffort(com.nike.reporting
	 * .model.Effort)
	 */
	@Override
	@Transactional
	public void updateEffort(Effort effort) {
		this.effortDAO.updateEffort(effort);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.service.EffortService#deleteEffort(com.nike.reporting
	 * .model.Effort)
	 */
	@Override
	@Transactional
	public void deleteEffort(Effort effort) {
		this.effortDAO.deleteEffort(effort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nike.reporting.service.EffortService#getEffortById(int)
	 */
	@Override
	@Transactional
	public Effort getEffortById(int id) {
		return this.effortDAO.getEffortById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nike.reporting.service.EffortService#getEffortBySearchCriteria(com
	 * .nike.reporting.model.Effort)
	 */
	@Override
	@Transactional
	public List<Effort> getEffortBySearchCriteria(SearchEffort searchEffort) throws DateParsingException {
		return this.effortDAO.getEffortBySearchCriteria(searchEffort);
	}

	@Override
	@Transactional
	public List<ExcelEffortDTO> getEffortListFromExcel(File file, String userId) throws NikeException {
		String ext = FilenameUtils.getExtension(file.getName());
		if (!ext.equalsIgnoreCase("xlsx")) {
			throw new NikeException("INVALID_FILE_EXTENSION", ErrorMessages.EFFORT_FILE_EXTENSTION_INCORRECT);
		}
		List<ExcelEffortDTO> excelEffortDTOList = new ArrayList<ExcelEffortDTO>();
		FileInputStream fis = null;
		try {
			DateFormat df = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
			// Create Workbook instance holding reference to .xlsx file
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			// Check for template value "EFFORT_TRACKER_TEMPLATE_ID" with cell
			// A2
			CellReference cellReference = new CellReference("A2");
			Row a2Row = sheet.getRow(cellReference.getRow());
			Cell a2Cell;
			try {
				a2Cell = a2Row.getCell(cellReference.getCol());
				if (!a2Cell.getStringCellValue().equalsIgnoreCase(ReportingConstants.EFFORT_TRACKER_TEMPLATE_ID)) {
					throw new NikeException("EFFORT_FILE_INCORRECT", ErrorMessages.EFFORT_FILE_INCORRECT);
				}
				// logger.debug("#### a2Cell.getStringCellValue(): " +
				// a2Cell.getStringCellValue());
			} catch (Exception e) {
				throw new NikeException("EFFORT_FILE_INCORRECT", ErrorMessages.EFFORT_FILE_INCORRECT);
			}
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				ExcelEffortDTO excelEffortDTO = new ExcelEffortDTO();
				Row row = rowIterator.next();
				// System.out.print("#### row.getRowNum(): " + row.getRowNum() +
				// "\t");
				// Skip first 3 rows [starts with 0]
				if (row.getRowNum() < 3) {
					continue;
				}
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getColumnIndex()) {
					case 0:
						String application = cell.getStringCellValue();
						if (application == null || application.trim().length() == 0) {
							continue;
						}
						excelEffortDTO.setApplication(cell.getStringCellValue());
						break;
					case 1:
						excelEffortDTO.setCategory(cell.getStringCellValue());
						break;
					case 2:
						excelEffortDTO.setPriority(cell.getStringCellValue());
						break;
					case 3:
						excelEffortDTO.setComplexity(cell.getStringCellValue());
						break;
					case 4:
						excelEffortDTO.setSnowNumber(cell.getStringCellValue().trim());
						break;
					case 5:
						excelEffortDTO.setEffortDescription(cell.getStringCellValue().trim());
						break;
					case 6:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							excelEffortDTO.setEffortHours(Double.toString(cell.getNumericCellValue()));
						} else {
							excelEffortDTO.setEffortHours(null);
						}
						break;
					case 7:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								excelEffortDTO.setEffortDate(df.format(cell.getDateCellValue()));
							}
						} else {
							excelEffortDTO.setEffortDate(null);
						}
						break;
					}
				}
				// Add Audit Info
				excelEffortDTO.setCreatedBy(userId);
				excelEffortDTO.setUpdatedBy(userId);
				excelEffortDTO.setCreatedDate(new Date());
				excelEffortDTO.setUpdatedDate(new Date());
				excelEffortDTOList.add(excelEffortDTO);
			}

		} catch (NikeException e) {
			throw new NikeException(e, ErrorMessages.EFFORT_FILE_INCORRECT);
		} catch (FileNotFoundException e) {
			throw new NikeException(e, ErrorMessages.EFFORT_FILE_NOT_FOUND_EXCEPTION);
		} catch (IOException e) {
			throw new NikeException(e, ErrorMessages.EFFORT_FILE_IO_EXCEPTION);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
			}
		}

		Iterator<ExcelEffortDTO> itr = excelEffortDTOList.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
		Map<String, String> dateWiseTotalMap = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		while (itr.hasNext()) {
			ExcelEffortDTO excelEffortDTO = itr.next();
			logger.info(excelEffortDTO.toString());
			// remove blank rows
			if ((excelEffortDTO.getApplication() == null || excelEffortDTO.getApplication().trim().length() == 0)
					&& (excelEffortDTO.getCategory() == null || excelEffortDTO.getCategory().trim().length() == 0)
					&& (excelEffortDTO.getPriority() == null || excelEffortDTO.getPriority().trim().length() == 0)
					&& (excelEffortDTO.getComplexity() == null || excelEffortDTO.getComplexity().trim().length() == 0)
					&& (excelEffortDTO.getSnowNumber() == null || excelEffortDTO.getSnowNumber().trim().length() == 0)
					&& (excelEffortDTO.getEffortDescription() == null || excelEffortDTO.getEffortDescription().trim().length() == 0)
					&& (excelEffortDTO.getEffortHours() == null || excelEffortDTO.getEffortHours().trim().length() == 0)
					&& (excelEffortDTO.getEffortDate() == null || excelEffortDTO.getEffortDate().trim().length() == 0)) {
				itr.remove();
				continue;
			}

			// Validations
			if ((excelEffortDTO.getApplication() == null || excelEffortDTO.getApplication().trim().length() == 0)
					|| (excelEffortDTO.getCategory() == null || excelEffortDTO.getCategory().trim().length() == 0)
					|| (excelEffortDTO.getEffortDescription() == null || excelEffortDTO.getEffortDescription().trim().length() == 0)
					|| (excelEffortDTO.getEffortHours() == null || excelEffortDTO.getEffortHours().trim().length() == 0)
					|| (excelEffortDTO.getEffortDate() == null || excelEffortDTO.getEffortDate().trim().length() == 0)) {
				throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
						+ "Application, Category, Effort Description, Effort Hours & Effort Date are mandatory fields.");
			}

			if (excelEffortDTO.getApplication().equalsIgnoreCase("Application") || excelEffortDTO.getApplication().equalsIgnoreCase("Effort Tracker")
					|| excelEffortDTO.getApplication().equalsIgnoreCase(ReportingConstants.EFFORT_TRACKER_TEMPLATE_ID)) {
				itr.remove();
				// ignore header
				continue;
			}

			if (excelEffortDTO.getCategory().equalsIgnoreCase(ReportingConstants.INCIDENT_MANAGEMENT) || excelEffortDTO.getCategory().equalsIgnoreCase(ReportingConstants.DEFECT_MANAGEMENT)) {
				if ((excelEffortDTO.getPriority() == null || excelEffortDTO.getPriority().trim().length() == 0)
						|| (excelEffortDTO.getComplexity() == null || excelEffortDTO.getComplexity().trim().length() == 0)
						|| (excelEffortDTO.getSnowNumber() == null || excelEffortDTO.getSnowNumber().trim().length() == 0)) {
					throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
							+ "For Incident Management & Defect Management effort category, Priority/Complexity/SNOW Id are mandatory fields.");
				}
				if (excelEffortDTO.getCategory().equalsIgnoreCase(ReportingConstants.INCIDENT_MANAGEMENT)
						&& (!(excelEffortDTO.getSnowNumber().startsWith("INC")) || !(excelEffortDTO.getSnowNumber().startsWith("SUB")) || !(excelEffortDTO.getSnowNumber().startsWith("TKT")))
						&& (excelEffortDTO.getSnowNumber().trim().length() != 10)) {
					throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
							+ "For Incident Management effort category, SNOW Id should start with INC, SUB or TKT and should be 10 characters long.");
				}
				if (excelEffortDTO.getCategory().equalsIgnoreCase(ReportingConstants.DEFECT_MANAGEMENT)
						&& ((!(excelEffortDTO.getSnowNumber().startsWith("DFCT")) && !(excelEffortDTO.getSnowNumber().startsWith("ENHC"))) || (excelEffortDTO.getSnowNumber().trim().length() != 11))) {
					throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
							+ "For Defect Management effort category, SNOW Id should start with DFCT or ENHC and should be 11 characters long");
				}
				if (!(excelEffortDTO.getPriority().matches(ReportingConstants.PRIORITY_REGEX))) {
					throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
							+ "For Incident Management & Defect Management effort category, Priority should be P1, P2, P3 or P4");
				}
				if (excelEffortDTO.getComplexity().equalsIgnoreCase("Not Applicable")) {
					throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION
							+ "For Incident Management & Defect Management effort category, Complexity should not be 'Not Applicable'");
				}
			} else {
				excelEffortDTO.setPriority(null);
				excelEffortDTO.setComplexity(null);
				excelEffortDTO.setSnowNumber(null);
			}

			if (!CommonsUtil.isNumeric(excelEffortDTO.getEffortHours())) {
				throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION + "Effort Hours should be numeric value (upto two decimal places).");
			} else if (Double.parseDouble(excelEffortDTO.getEffortHours()) > ReportingConstants.WEEKLY_EFFORT_MAX_LIMIT_HOURS) {
				throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION + "Effort hour value " + excelEffortDTO.getEffortHours() + " cannot be greater than "
						+ ReportingConstants.WEEKLY_EFFORT_MAX_LIMIT_HOURS + " hours on " + excelEffortDTO.getEffortDate());
			} else {
				if (dateWiseTotalMap.containsKey(excelEffortDTO.getEffortDate())) {
					double value = Double.parseDouble(dateWiseTotalMap.get(excelEffortDTO.getEffortDate())) + Double.parseDouble(excelEffortDTO.getEffortHours());
					if (value > ReportingConstants.WEEKLY_EFFORT_MAX_LIMIT_HOURS) {
						throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION + "Total effort hours cannot be greater than "
								+ ReportingConstants.WEEKLY_EFFORT_MAX_LIMIT_HOURS + " hours on " + excelEffortDTO.getEffortDate());
					}
					dateWiseTotalMap.put(excelEffortDTO.getEffortDate(), String.valueOf(value));
				} else {
					dateWiseTotalMap.put(excelEffortDTO.getEffortDate(), excelEffortDTO.getEffortHours());
				}
			}
			if (!CommonsUtil.isValidDate(excelEffortDTO.getEffortDate(), ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME)) {
				throw new NikeException("VALIDATION_FAILED", ErrorMessages.EFFORT_FILE_VALIDATION_EXCEPTION + "Effort Date cannot be greater than current WHQ date and should be in the format "
						+ ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME);
			}
		}
		return excelEffortDTOList;
	}

	@Override
	@Transactional
	public void addEfforts(List<Effort> effortList) {
		for (Effort effort : effortList) {
			effortDAO.addEffort(effort);
		}
	}

	@Override
	@Transactional
	public boolean createExcelReportFromList(String sqlQuery, HttpServletRequest request, HttpServletResponse response, String fileName) {
		List effortReportDataList = effortDAO.getEffortForReports(sqlQuery);
		EffortConverter effortConverter = new EffortConverter();
		List<EffortReports> effortReportsList = effortConverter.convertToEffortReports(effortReportDataList);
		return buildExcelReport(effortReportsList, request, response, fileName);
	}

	private static boolean buildExcelReport(List<EffortReports> listData, HttpServletRequest request, HttpServletResponse response, String fileName) {
		FileInputStream fis = null;
		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);
		XSSFWorkbook workbook = null;
		XSSFSheet firstSheet = null;
		FileOutputStream fos = null;
		int rowNum = 0;

		try {
			fis = new FileInputStream(appPath + "\\resources\\templates\\" + fileName);
			workbook = new XSSFWorkbook(fis);
			firstSheet = workbook.getSheetAt(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.print("WeeklyEffortReport.xlsx template not found.");
			return false;
		}
		// SXSSFWorkbook workbook;
		// workbook = new SXSSFWorkbook();
		// Sheet firstSheet = workbook.createSheet("WeekyEffort");

		/*
		 * Header already present in xlsx template Row headerRow =
		 * firstSheet.createRow(0); Cell headerCell = headerRow.createCell(0);
		 * if
		 * (fileName.equalsIgnoreCase(ReportingConstants.WEEKLY_EFFORT_REPORT_NAME
		 * )) { headerCell.setCellValue("Owner"); } else if
		 * (fileName.equalsIgnoreCase
		 * (ReportingConstants.WEEKLY_APPLICATION_WISE_EFFORT_REPORT_NAME)){
		 * headerCell.setCellValue("Application"); }else
		 * if(fileName.equalsIgnoreCase
		 * (ReportingConstants.WEEKLY_CAPABILITY_WISE_EFFORT_REPORT_NAME)){
		 * headerCell.setCellValue("Capability"); } headerCell =
		 * headerRow.createCell(1); headerCell.setCellValue("WeekNumber");
		 * headerCell = headerRow.createCell(2);
		 * headerCell.setCellValue("TotalEffortHours"); headerCell =
		 * headerRow.createCell(3); headerCell.setCellValue("WeekStartDate");
		 * headerCell = headerRow.createCell(4);
		 * headerCell.setCellValue("WeekEndDate");
		 */

		rowNum++;
		try {
			for (int j = 0; j < listData.size(); j++) {
				Row row = firstSheet.createRow(rowNum);
				EffortReports effortReportObject = listData.get(j);

				Cell cell = row.createCell(0);
				if (fileName.equalsIgnoreCase(ReportingConstants.WEEKLY_EFFORT_REPORT_NAME)) {
					cell.setCellValue(effortReportObject.getOwner());
				} else if (fileName.equalsIgnoreCase(ReportingConstants.WEEKLY_APPLICATION_WISE_EFFORT_REPORT_NAME)) {
					cell.setCellValue(effortReportObject.getApplicationName());
				} else if (fileName.equalsIgnoreCase(ReportingConstants.WEEKLY_CAPABILITY_WISE_EFFORT_REPORT_NAME)) {
					cell.setCellValue(effortReportObject.getCapabilityName());
				}

				cell = row.createCell(1);
				cell.setCellValue(effortReportObject.getWeekNumber());

				cell = row.createCell(2);
				cell.setCellValue(effortReportObject.getTotalEffortHours());

				cell = row.createCell(3);
				cell.setCellValue(effortReportObject.getWeekStartDate());

				cell = row.createCell(4);
				cell.setCellValue(effortReportObject.getWeekEndDate());

				rowNum++;
			}
			String user_id = LoginUtil.getLoggedInUser();
			fos = new FileOutputStream(appPath + "\\resources\\downloads\\" + user_id + "_" + fileName);
			workbook.write(fos);
			fos.close();
			// workbook.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
