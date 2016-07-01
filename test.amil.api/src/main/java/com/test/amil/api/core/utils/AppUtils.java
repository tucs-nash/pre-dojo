package com.test.amil.api.core.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe utilitária do app
 * @author tbdea
 *
 */
public final class AppUtils {

	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	public static Date convertStringToDateTime(String dateString) throws ParseException {
		return convertStringToDate(dateString, DATE_TIME_FORMAT);
	}

	public static String applyingValueHandling(String value) {
		return value.trim();
	}
	
	private static Date convertStringToDate(String dateString, String dateTimeFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		return sdf.parse(dateString);
	}
}
