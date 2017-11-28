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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.MTTRDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.MTTRAnalysis;
import com.nike.reporting.model.MTTRData;
import com.nike.reporting.model.SearchMTTR;
import com.nike.reporting.model.dto.ExcelMTTRDTO;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.LoginUtil;
import com.nike.reporting.util.ReportingConstants;

/**
 * @author Sachin_Ainapure
 * 
 */
public class MTTRServiceImpl implements MTTRService {
	private static final Logger logger = LoggerFactory.getLogger(MTTRServiceImpl.class);
	private MTTRDAO MTTRDAO;

	public MTTRDAO getMTTRDAO() {
		return MTTRDAO;
	}

	public void setMTTRDAO(MTTRDAO MTTRDAO) {
		this.MTTRDAO = MTTRDAO;
	}

	@Override
	@Transactional
	public List<MTTRData> getMTTRDataFromDB() {
		return MTTRDAO.getMTTRFromDB();
	}

	@Override
	@Transactional
	public List<ExcelMTTRDTO> getMTTRDataFromExcel(File file, String userId) throws NikeException {
		String ext = FilenameUtils.getExtension(file.getName());
		if (!ext.equalsIgnoreCase("xls")) {
			throw new NikeException("INVALID_FILE_EXTENSION", ErrorMessages.MTTR_FILE_EXTENSTION_INCORRECT);
		}
		List<ExcelMTTRDTO> excelMTTRDTOList = new ArrayList<ExcelMTTRDTO>();
		FileInputStream fis = null;
		try {
			DateFormat df = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
			// Create Workbook instance holding reference to .xls file
			fis = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);

			// TODO: Check for template uploaded

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			boolean isCorrectFile = true;
			while (rowIterator.hasNext()) {
				ExcelMTTRDTO excelMTTRDTO = new ExcelMTTRDTO();
				Row row = rowIterator.next();
				// System.out.print("#### row.getRowNum(): " + row.getRowNum() +
				// "\t");
				// Skip first row [starts with 0]
				if (row.getRowNum() == 0) {
					if (!row.getCell(0).getStringCellValue().equalsIgnoreCase("Number")) {
						isCorrectFile = false;
					} else if (!row.getCell(1).getStringCellValue().equalsIgnoreCase("Category 1")) {
						isCorrectFile = false;
					} else if (!row.getCell(2).getStringCellValue().equalsIgnoreCase("Priority")) {
						isCorrectFile = false;
					} else if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("Escalation")) {
						isCorrectFile = false;
					} else if (!row.getCell(4).getStringCellValue().equalsIgnoreCase("Definition")) {
						isCorrectFile = false;
					} else if (!row.getCell(5).getStringCellValue().equalsIgnoreCase("Value")) {
						isCorrectFile = false;
					} else if (!row.getCell(6).getStringCellValue().equalsIgnoreCase("Start")) {
						isCorrectFile = false;
					} else if (!row.getCell(7).getStringCellValue().equalsIgnoreCase("Created")) {
						isCorrectFile = false;
					} else if (!row.getCell(8).getStringCellValue().equalsIgnoreCase("End")) {
						isCorrectFile = false;
					} else if (!row.getCell(9).getStringCellValue().equalsIgnoreCase("Closed")) {
						isCorrectFile = false;
					} else if (!row.getCell(10).getStringCellValue().equalsIgnoreCase("Duration")) {
						isCorrectFile = false;
					} else if (!row.getCell(11).getStringCellValue().equalsIgnoreCase("Assignment group")) {
						isCorrectFile = false;
					} else if (!row.getCell(12).getStringCellValue().equalsIgnoreCase("Assigned to")) {
						isCorrectFile = false;
					} else if (!row.getCell(13).getStringCellValue().equalsIgnoreCase("Resolution Code")) {
						isCorrectFile = false;
					} else if (!row.getCell(14).getStringCellValue().equalsIgnoreCase("Resolution Notes")) {
						isCorrectFile = false;
					} else if (!row.getCell(15).getStringCellValue().equalsIgnoreCase("Configuration item")) {
						isCorrectFile = false;
					}
					if (isCorrectFile) {
						continue;
					} else {
						throw new NikeException("MTTR_FILE_INCORRECT", ErrorMessages.MTTR_FILE_INCORRECT);
					}
				}
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getColumnIndex()) {
					case 0:
						/*
						 * String number = cell.getStringCellValue(); if (number
						 * == null || number.trim().length() == 0) { continue; }
						 */
						excelMTTRDTO.setSnowId(cell.getStringCellValue());
						break;
					case 1:
						excelMTTRDTO.setCategory(cell.getStringCellValue());
						break;
					case 2:
						excelMTTRDTO.setPriority(cell.getStringCellValue());
						break;
					case 3:
						excelMTTRDTO.setEscalation(cell.getStringCellValue());
						break;
					case 4:
						excelMTTRDTO.setDefinition(cell.getStringCellValue().trim());
						break;
					case 5:
						excelMTTRDTO.setValue(cell.getStringCellValue().trim());
						break;
					case 6:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								excelMTTRDTO.setStartDate(cell.getDateCellValue());
							}
						} else {
							excelMTTRDTO.setStartDate(cell.getDateCellValue());
						}
						break;
					case 7:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								excelMTTRDTO.setCreatedDate(cell.getDateCellValue());
							}
						} else {
							excelMTTRDTO.setCreatedDate(cell.getDateCellValue());
						}
						break;
					case 8:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								excelMTTRDTO.setEndDate(cell.getDateCellValue());
							}
						} else {
							excelMTTRDTO.setEndDate(cell.getDateCellValue());
						}
						break;
					case 9:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								excelMTTRDTO.setClosedDate(cell.getDateCellValue());
							}
						} else {
							excelMTTRDTO.setClosedDate(cell.getDateCellValue());
						}
						break;

					case 10:
						if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
							excelMTTRDTO.setDuration((int) (cell.getNumericCellValue()));
						} else {
							excelMTTRDTO.setDuration(Integer.parseInt(cell.getStringCellValue()));
						}
						break;
					case 11:
						excelMTTRDTO.setAssignmentGroup(cell.getStringCellValue().trim());
						break;

					case 12:
						excelMTTRDTO.setAssignedTo(cell.getStringCellValue().trim());
						break;

					case 13:
						excelMTTRDTO.setResolutionCode(cell.getStringCellValue().trim());
						break;

					case 14:
						excelMTTRDTO.setResolutionNotes(cell.getStringCellValue().trim());
						break;

					case 15:
						excelMTTRDTO.setConfigurationItem(cell.getStringCellValue().trim());
						break;
					}

				}
				// Add Audit Info
				excelMTTRDTO.setUploadedBy(userId);
				excelMTTRDTO.setEditedBy(userId);
				excelMTTRDTO.setUploadedDate(new Date());
				excelMTTRDTO.setEditedDate(new Date());
				excelMTTRDTOList.add(excelMTTRDTO);
			}

		} catch (FileNotFoundException e) {
			throw new NikeException(e, ErrorMessages.MTTR_FILE_NOT_FOUND_EXCEPTION);
		} catch (IOException e) {
			throw new NikeException(e, ErrorMessages.MTTR_FILE_IO_EXCEPTION);
		} catch (NikeException ne) {
			throw new NikeException(ne, ne.getErrMsg());
		} catch (Exception e) {
			throw new NikeException(e, e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
			}
		}

		Iterator<ExcelMTTRDTO> itr = excelMTTRDTOList.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);
		Map<String, String> dateWiseTotalMap = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		while (itr.hasNext()) {
			ExcelMTTRDTO excelMTTRDTO = itr.next();
			logger.info(excelMTTRDTO.toString());
			// TODO: remove blank rows
			// if ((ExcelMTTRDTO.getApplication() == null ||
			// ExcelMTTRDTO.getApplication().trim().length() == 0)
			// && (ExcelMTTRDTO.getCategory() == null ||
			// ExcelMTTRDTO.getCategory().trim().length() == 0)
			// && (ExcelMTTRDTO.getPriority() == null ||
			// ExcelMTTRDTO.getPriority().trim().length() == 0)
			// && (ExcelMTTRDTO.getComplexity() == null ||
			// ExcelMTTRDTO.getComplexity().trim().length() == 0)
			// && (ExcelMTTRDTO.getSnowNumber() == null ||
			// ExcelMTTRDTO.getSnowNumber().trim().length() == 0)
			// && (ExcelMTTRDTO.getMTTRDescription() == null ||
			// ExcelMTTRDTO.getMTTRDescription().trim().length() == 0)
			// && (ExcelMTTRDTO.getMTTRHours() == null ||
			// ExcelMTTRDTO.getMTTRHours().trim().length() == 0)
			// && (ExcelMTTRDTO.getMTTRDate() == null ||
			// ExcelMTTRDTO.getMTTRDate().trim().length() == 0)) {
			// itr.remove();
			// continue;
			// }

			// TODO: Validations
			// if ((ExcelMTTRDTO.getApplication() == null ||
			// ExcelMTTRDTO.getApplication().trim().length() == 0)
			// || (ExcelMTTRDTO.getCategory() == null ||
			// ExcelMTTRDTO.getCategory().trim().length() == 0)
			// || (ExcelMTTRDTO.getMTTRDescription() == null ||
			// ExcelMTTRDTO.getMTTRDescription().trim().length() == 0)
			// || (ExcelMTTRDTO.getMTTRHours() == null ||
			// ExcelMTTRDTO.getMTTRHours().trim().length() == 0)
			// || (ExcelMTTRDTO.getMTTRDate() == null ||
			// ExcelMTTRDTO.getMTTRDate().trim().length() == 0)) {
			// throw new NikeException("VALIDATION_FAILED",
			// ErrorMessages.MTTR_FILE_VALIDATION_EXCEPTION +
			// "Application, Category, MTTR Description, MTTR Hours & MTTR Date are mandatory fields.");
			// }

		}
		return excelMTTRDTOList;
	}

	@Override
	@Transactional
	public void addMTTRs(List<MTTRData> MTTRDataList) throws HibernateException {

		List<MTTRData> mttrDataFromDBList = getMTTRDataFromDB();

		for (MTTRData MTTRData : MTTRDataList) {
			if (mttrDataFromDBList.contains(MTTRData)) {
				System.out.println(MTTRData.getSnowId() + " [createdDate:" + MTTRData.getCreatedDate() + "# startDate:" + MTTRData.getStartDate() + "] already exists in DB.");
			} else {
				// Add MTTR if incident is not present
				MTTRDAO.addMTTR(MTTRData);
				System.out.println(MTTRData.getSnowId() + " [createdDate:" + MTTRData.getCreatedDate() + "# startDate:" + MTTRData.getStartDate() + "] added to DB.");
			}

		}
	}

	@Override
	@Transactional
	public List createMTTRAnalysisTable(String sqlQuery, SearchMTTR searchMTTR) {
		return MTTRDAO.getMTTRForReports(sqlQuery, searchMTTR);
	}

	@Override
	@Transactional
	public List<MTTRData> getMTTRBySearchCriteria(SearchMTTR searchMTTR) throws DateParsingException {
		return this.MTTRDAO.getMTTRBySearchCriteria(searchMTTR);
	}

	@Override
	@Transactional
	public MTTRData getMTTRById(int id) {
		return this.MTTRDAO.getMTTRById(id);
	}

	@Override
	@Transactional
	public void updateMTTR(MTTRData mttrData) {
		this.MTTRDAO.updateMTTR(mttrData);
	}

	@Override
	@Transactional
	public boolean exportMTTRAnalysisToExcel(List<MTTRAnalysis> listData, HttpServletRequest request, HttpServletResponse response, String fileName) {
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
			System.err.print(fileName + " template not found.");
			return false;
		}

		rowNum++;
		try {
			CellStyle cellDefaultStyle = workbook.createCellStyle();
			CellStyle cellNumberStyle = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			cellNumberStyle.setDataFormat(format.getFormat("0.00"));
			XSSFFont xssfFont = workbook.createFont();
			xssfFont.setFontName("Calibri");
			xssfFont.setFontHeightInPoints((short) 10);
			cellDefaultStyle.setFont(xssfFont);
			cellNumberStyle.setFont(xssfFont);

			for (int j = 0; j < listData.size(); j++) {
				Row row = firstSheet.createRow(rowNum);
				MTTRAnalysis mttrAnalysisObject = listData.get(j);

				Cell cell = row.createCell(0);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mttrAnalysisObject.getAssignmentgroup());

				cell = row.createCell(1);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(mttrAnalysisObject.getPriority());

				cell = row.createCell(2);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(mttrAnalysisObject.getPes_sla());

				cell = row.createCell(3);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellStyle(cellNumberStyle);
				cell.setCellValue(mttrAnalysisObject.getAverage_hrs());

				cell = row.createCell(4);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(mttrAnalysisObject.getTotal_incidents());

				cell = row.createCell(5);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(mttrAnalysisObject.getLt_sla());

				cell = row.createCell(6);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(mttrAnalysisObject.getGt_sla());

				cell = row.createCell(7);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellStyle(cellNumberStyle);
				cell.setCellValue(mttrAnalysisObject.getPercentSLA_achieved());

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

	@Override
	@Transactional
	public boolean exportMTTRIncidentsToExcel(List<ExcelMTTRDTO> listData, HttpServletRequest request, HttpServletResponse response, String fileName) {
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
			System.err.print(fileName + " template not found.");
			return false;
		}

		rowNum++;
		try {
			CellStyle cellDefaultStyle = workbook.createCellStyle();
			CellStyle cellNumberStyle = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			cellNumberStyle.setDataFormat(format.getFormat("0.00"));
			XSSFFont xssfFont = workbook.createFont();
			xssfFont.setFontName("Calibri");
			xssfFont.setFontHeightInPoints((short) 10);
			cellDefaultStyle.setFont(xssfFont);
			cellNumberStyle.setFont(xssfFont);

			for (int j = 0; j < listData.size(); j++) {
				Row row = firstSheet.createRow(rowNum);
				ExcelMTTRDTO excelMTTRDTO = listData.get(j);

				Cell cell = row.createCell(0);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getSnowId());

				cell = row.createCell(1);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getPriority());

				cell = row.createCell(2);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getAssignmentGroup());

				cell = row.createCell(3);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellNumberStyle);
				cell.setCellValue(excelMTTRDTO.getAssignedTo());

				cell = row.createCell(4);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(excelMTTRDTO.getCreateToResolveInHours());

				cell = row.createCell(5);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getResolutionCode());

				cell = row.createCell(6);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getResolutionNotes());

				cell = row.createCell(7);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getMttrBreachReason());

				cell = row.createCell(8);
				cell.setCellStyle(cellDefaultStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(excelMTTRDTO.getRemarks());

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
