/**
 * <h1>Outage Service Object</h1>
 * OutageServiceImpl.java defines methods for Outage Service declared in OutageService.
 * @author  Mital Gadoya
 * @version 1.0
 * @since   2015-01-01
 * 
 * 
 */
package com.nike.reporting.service;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.OutageDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.exceptions.NikeException;
import com.nike.reporting.model.Outage;
import com.nike.reporting.model.SearchOutage;
import com.nike.reporting.model.dto.OutageDTO;
import com.nike.reporting.util.ErrorMessages;
import com.nike.reporting.util.ReportingConstants;

public class OutageServiceImpl implements OutageService {

	private OutageDAO outageDAO;

	public void setOutageDAO(OutageDAO outageDAO) {
		this.outageDAO = outageDAO;
	}

	@Override
	@Transactional
	public void addOutage(Outage o) {
		this.outageDAO.addOutage(o);

	}

	@Override
	@Transactional
	public void updateOutage(Outage o) {
		this.outageDAO.updateOutage(o);

	}

	@Override
	@Transactional
	public List<Outage> listOutages() {
		return this.outageDAO.listOutages();
	}

	@Override
	@Transactional
	public Outage getOutageById(int id) {
		return this.outageDAO.getOutageById(id);
	}

	@Override
	@Transactional
	public void removeOutage(int id) {
		this.outageDAO.removeOutage(id);
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<Outage> getOutageBySearchCriteria(Outage o) {
	 * return this.outageDAO.getOutageBySearchCriteria(o); }
	 */

	@Override
	@Transactional
	public List<Outage> getOutageBySearchCriteria(SearchOutage so) throws DateParsingException {

		return this.outageDAO.getOutageBySearchCriteria(so);

	}

	@Override
	public synchronized String createEOSPptx(HttpServletRequest request, HttpServletResponse response, OutageDTO outageDTO) throws NikeException {
		FileInputStream is = null;
		FileOutputStream out = null;
		String fileName = null;
		XMLSlideShow ppt = null;
		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();

		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);

		try {
			is = new FileInputStream(appPath + "\\resources\\templates\\EmergencyOutageSummaryReport.pptx");
			ppt = new XMLSlideShow(is);
			is.close();
		} catch (IOException e) {
			throw new NikeException(e, ErrorMessages.CREATE_OUTAGE_PPT_FILE_NOT_EXCEPTION);
		}

		ppt.getPageSize();
		for (XSLFSlide slide : ppt.getSlides()) {
			for (XSLFShape shape : slide) {
				shape.getAnchor();
				if (shape instanceof XSLFTable) {
					XSLFTable t = (XSLFTable) shape;
					List<XSLFTableRow> r;
					r = t.getRows();

					System.out.println("\n" + "Print Details For: " + t.getShapeName());
					for (int i = 0; i < r.size(); i++) {
						XSLFTableCell cell0 = r.get(i).getCells().get(0);
						XSLFTableCell cell1 = r.get(i).getCells().get(1);

						if (cell1.getText().trim().equalsIgnoreCase("1")) {
							addTextToCell(cell1, outageDTO.getDescription());
						} else if (cell1.getText().trim().equalsIgnoreCase("2")) {
							addTextToCell(cell1, outageDTO.getStOwner());
						} else if (cell1.getText().trim().equalsIgnoreCase("3")) {
							addTextToCell(cell1, outageDTO.getBusinessAffected());
						} else if (cell1.getText().trim().equalsIgnoreCase("4")) {
							addTextToCell(cell1, outageDTO.getDueTo());
						} else if (cell1.getText().trim().equalsIgnoreCase("5")) {
							addTextToCell(cell1, outageDTO.getExecutiveSummary());
						} else if (cell1.getText().trim().equalsIgnoreCase("6")) {
							addTextToCell(cell1, outageDTO.getSnowIds());
						} else if (cell1.getText().trim().equalsIgnoreCase("7")) {
							addTextToCell(cell1, outageDTO.getPriority() + " / " + outageDTO.getSeverity());
						} else if (cell1.getText().trim().equalsIgnoreCase("8")) {
							addTextToCell(cell1, new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN_NO_TIME).format(outageDTO.getReportedOn()));
						} else if (cell1.getText().trim().equalsIgnoreCase("9")) {
							addTextToCell(cell1, outageDTO.getReportedBy());
						} else if (cell1.getText().trim().equalsIgnoreCase("10")) {
							addTextToCell(cell1, outageDTO.getOutageDurationInHrsMins());
						} else if (cell1.getText().trim().equalsIgnoreCase("11")) {
							addTextToCell(cell1, outageDTO.getTechnicalIssues());
						} else if (cell1.getText().trim().equalsIgnoreCase("12")) {
							addTextToCell(cell1, outageDTO.getResolution());
						} else if (cell1.getText().trim().equalsIgnoreCase("13")) {
							addTextToCell(cell1, outageDTO.getRootCause());
						} else if (cell1.getText().trim().equalsIgnoreCase("14")) {
							if (outageDTO.getVendorAccountable().equalsIgnoreCase("Yes")) {
								addTextToCell(cell1, "Yes (" + outageDTO.getVendorAccountableName() + ")");
							} else {
								addTextToCell(cell1, outageDTO.getVendorAccountable());
							}
						} else if (cell1.getText().trim().equalsIgnoreCase("15")) {
							addTextToCell(cell1, outageDTO.getAarOwner() + ", " + new SimpleDateFormat("MM/dd").format(outageDTO.getAarDate()));
						} else if (cell1.getText().trim().equalsIgnoreCase("17")) {
							if (outageDTO.getPointOfFailure().contains("PLATFORM")) {
								addTextToCellWithBGColor(cell1, outageDTO.getPlatform(), Color.RED);
							} else {
								addTextToCell(cell1, outageDTO.getPlatform());
							}
						}

						if (cell0.getText().trim().equalsIgnoreCase("16")) {
							XSLFTableCell cell2 = r.get(i).getCells().get(2);
							XSLFTableCell cell3 = r.get(i).getCells().get(3);
							XSLFTableCell cell4 = r.get(i).getCells().get(4);
							if (outageDTO.getPointOfFailure().contains("INFRASTRUCTURE")) {
								addTextToCellWithBGColor(cell0, outageDTO.getDatabase(), Color.RED);
							} else {
								addTextToCell(cell0, outageDTO.getDatabase());
							}

							if (cell2.getText().trim().equalsIgnoreCase("18")) {
								if (outageDTO.getPointOfFailure().contains("APPLICATION")) {
									addTextToCellWithBGColor(cell2, outageDTO.getApplications(), Color.RED);
								} else {
									addTextToCell(cell2, outageDTO.getApplications());
								}
							}
							if (cell3.getText().trim().equalsIgnoreCase("19")) {
								addTextToCell(cell3, new SimpleDateFormat("MM/dd/yyyy - hh:mm a").format(outageDTO.getDeploymentStartDate()) + " (WHQ)");
							}
							if (cell4.getText().trim().equalsIgnoreCase("20")) {
								addTextToCell(cell4, new SimpleDateFormat("MM/dd/yyyy - hh:mm a").format(outageDTO.getDeploymentEndDate()) + " (WHQ)");
							}
						}

					}
				}
			}
		}
		fileName = "ST_Weekly_Prod_Supp_Report_Emergency_Outage_" + outageDTO.getApplications() + "_" + new SimpleDateFormat("ddMMMyyyy").format(outageDTO.getDeploymentStartDate()) + ".pptx";
		try {
			out = new FileOutputStream(appPath + "\\resources\\downloads\\" + fileName);
			ppt.write(out);
			out.close();
		} catch (IOException e) {
			throw new NikeException(e, ErrorMessages.CREATE_OUTAGE_PPT_FILE_NOT_EXCEPTION);
		}

		return fileName;
	}

	private static final void addTextToCell(XSLFTableCell cell, String cellContents) {
		// clear the original contents
		cell.clearText();
		// add new paragraph
		XSLFTextParagraph paragraph = cell.addNewTextParagraph();
		// create text run object
		XSLFTextRun run = paragraph.addNewTextRun();
		// set the cell contents
		run.setText(cellContents);
		// set font size and family
		run.setFontFamily("Arial Narrow (Body)");
		run.setFontSize(12);
		// change the text into bold format
		run.setBold(true);
		// set the font color
		run.setFontColor(java.awt.Color.BLACK);
		// change the text it to italic format
		// run.setItalic(true);
		// strike through the text
		// run.setStrikethrough(true);
		// underline the text
		// run.setUnderline(true);
		// paragraph.addLineBreak();
	}

	private static final void addTextToCellWithBGColor(XSLFTableCell cell, String cellContents, Color color) {
		// clear the original contents
		cell.clearText();
		// add new paragraph
		XSLFTextParagraph paragraph = cell.addNewTextParagraph();
		// create text run object
		XSLFTextRun run = paragraph.addNewTextRun();
		// set the cell contents
		run.setText(cellContents);
		// set font size and family
		run.setFontFamily("Arial Narrow (Body)");
		run.setFontSize(12);
		// change the text into bold format
		run.setBold(true);
		// set the font color
		run.setFontColor(color);

		// TODO: Giving error while opening
		// the ppt and repairs it.
		// cell.setFillColor(color);
	}

}
