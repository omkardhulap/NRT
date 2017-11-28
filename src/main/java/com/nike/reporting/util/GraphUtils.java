/**
 * 
 */
package com.nike.reporting.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * @author Sachin_Ainapure
 * 
 */
public final class GraphUtils {

	public static synchronized JFreeChart createBARChart(String chartTitle, String domainXAxisLabel, String rangeYAxisLabel, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet,
			boolean legendsReq, boolean toolTips, boolean urls, Color color1, Color color2) {

		// create the bar chart
		final JFreeChart chart = ChartFactory.createBarChart(chartTitle, domainXAxisLabel, rangeYAxisLabel, dataSet, plotOrientation, legendsReq, toolTips, urls);

		// set the background color for the chart
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.lightGray);
		// plot.setOutlineVisible(false);
		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setMaximumBarWidth(0.015);

		// display values on top of bar chart
		renderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
		renderer.setSeriesItemLabelGenerator(1, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
		renderer.setSeriesItemLabelGenerator(2, new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getNumberInstance()));
		Font font10 = new Font("Dialog", Font.PLAIN, 10);
		renderer.setBaseItemLabelFont(font10);

		renderer.setSeriesItemLabelsVisible(0, true);
		renderer.setSeriesItemLabelsVisible(1, true);
		renderer.setSeriesItemLabelsVisible(2, true);

		// set gap in bars
		renderer.setItemMargin(0.03);

		// set up gradient paints for series...
		final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, color1, 0.0f, 0.0f, color1);
		final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, color2, 0.0f, 0.0f, color2);
		final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.orange, 0.0f, 0.0f, Color.orange);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

		TextTitle my_Chart_title = new TextTitle(chartTitle, new Font("Arial", Font.BOLD, 20));
		chart.setTitle(my_Chart_title);

		/*
		 * position is the value on the axis (should be average)
		 */
		// ValueMarker marker = new ValueMarker(72.5);
		// marker.setPaint(Color.black);
		// // marker.setLabel("here"); // see JavaDoc for labels, colors,
		// strokes
		// // XYPlot plot = (XYPlot) chart.getPlot();
		// plot.addRangeMarker(marker);

		return chart;
	}

	public static synchronized JFreeChart createPIEChart(String chartTitle, DefaultPieDataset pieDataSet, boolean legend, boolean toolTips, boolean url) {
		JFreeChart chart = ChartFactory.createPieChart(chartTitle, pieDataSet, legend, toolTips, url);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(45);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setShadowPaint(null);
		// plot.setOutlineVisible(false);//hide graph border
		// plot.setForegroundAlpha(0.9f);
		plot.setBackgroundPaint(Color.white);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: [{1}, {2}]"));
		// chart.setBackgroundPaint(new Color(249, 231, 236));
		chart.setBackgroundPaint(Color.white);
		plot.setLabelOutlinePaint(null);
		plot.setLabelOutlineStroke(null);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelShadowPaint(null);
		plot.setMaximumLabelWidth(0.25);
		plot.setCircular(true);
		plot.setOutlinePaint(Color.lightGray);
		plot.setLabelPaint(Color.darkGray);
		plot.setInteriorGap(0.03);

		TextTitle myChartTitle = new TextTitle(chartTitle, new Font("Arial", Font.BOLD, 20));
		chart.setTitle(myChartTitle);

		return chart;
	}

	public static synchronized JFreeChart createLineChart(String chartTitle, String domainXAxisLabel, String rangeYAxisLabel, PlotOrientation plotOrientation, DefaultCategoryDataset dataSet,
			boolean legendsReq, boolean toolTips, boolean urls, Color color1, Color color2) {
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, domainXAxisLabel, rangeYAxisLabel, dataSet, plotOrientation, true, true, false);
		// set the background color for the chart
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		// Enable shapes on the line chart
		renderer.setShapesVisible(true);
		// chart.getXYPlot().setRenderer(renderer);
		// sets paint color for each series
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.blue);
		renderer.setSeriesPaint(2, Color.YELLOW);
		renderer.setSeriesPaint(2, Color.green);

		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));
		renderer.setSeriesStroke(3, new BasicStroke(2.0f));

		// // get a reference to the plot for further customisation...
		// final XYPlot plot = chart.getXYPlot();
		//
		// plot.setRangeGridlinesVisible(true);
		// plot.setRangeGridlinePaint(Color.gray);
		// plot.setDomainGridlinesVisible(true);
		// chart.getXYPlot().setDomainGridlinePaint(Color.gray);

		// final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.lightGray);

		// final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// renderer.setSeriesLinesVisible(0, false);
		// renderer.setSeriesShapesVisible(1, false);
		// plot.setRenderer(renderer);

		// plot.setOutlineVisible(false);
		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

		TextTitle my_Chart_title = new TextTitle(chartTitle, new Font("Arial", Font.BOLD, 20));
		chart.setTitle(my_Chart_title);

		return chart;
	}

	public static long getDaysBetweenDates(String fromDate, String toDate) {
		SimpleDateFormat format = new SimpleDateFormat(ReportingConstants.DATE_FORMAT_PATTERN);

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);
			// in milliseconds
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			return diffDays;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static synchronized File getChartFile(HttpServletRequest request, String fileName) {
		ServletContext servletContext = null;
		servletContext = request.getSession().getServletContext();
		String applicationPath = servletContext.getRealPath("");
		String chartPath = applicationPath + "\\resources\\downloads\\" + fileName;
		return new File(chartPath);
	}
}
