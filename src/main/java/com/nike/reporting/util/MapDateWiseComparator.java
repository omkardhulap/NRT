package com.nike.reporting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author Sachin Ainapure
 */
public class MapDateWiseComparator implements Comparator {

	private static Map<Locale, List> monthMap = new HashMap<Locale, List>();
	private static SimpleDateFormat dateFormat;

	public MapDateWiseComparator(SimpleDateFormat dateFormat) {
		List months = new ArrayList(12);
		Calendar cal = Calendar.getInstance();
		this.dateFormat = dateFormat;
		if (!monthMap.containsKey(Locale.ENGLISH)) {
			for (int i = 0; i < 12; i++) {
				cal.set(Calendar.MONTH, i);
				months.add(dateFormat.format(cal.getTime()).toLowerCase());
			}
			monthMap.put(Locale.ENGLISH, months);
		}
	}

	// @Override
	// public int compare(Object month1, Object month2) {
	// List months = monthMap.get(Locale.ENGLISH);
	// if (months == null) {
	// throw new
	// NullPointerException("MapDateWiseComparator cannot perform comparison - internal data is not initialized properly.");
	// }
	// return (months.indexOf(((String) month1).toLowerCase()) -
	// months.indexOf(((String) month2).toLowerCase()));
	//
	// }

	@Override
	public int compare(Object month1, Object month2) {
		try {
			return (this.dateFormat.parse((String) month1)).compareTo(this.dateFormat.parse((String) month2));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new NullPointerException("MapDateWiseComparator cannot perform comparison - " + e.getMessage());
		}
	}
}