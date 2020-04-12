package org.yuesi.databridge.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public static DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static String now() {
		LocalDate date = LocalDate.now();
		String now = date.format(dft);
		return now;
	}
	
	public static String aweekBefore() {
		LocalDate date = LocalDate.now().minusWeeks(1);
		String str = date.format(dft);
		return str;
	}
	
}
