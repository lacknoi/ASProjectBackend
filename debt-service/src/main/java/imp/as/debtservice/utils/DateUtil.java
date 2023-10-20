package imp.as.debtservice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static String convertDateToString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, new Locale("en", "US"));
		String tmp = "";
		try {
			tmp = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}
	public static String convertDateToStringTh(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, new Locale("th", "TH"));
		String tmp = "";
		try {
			tmp = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}

	public static Date convertStringToDate(String strDate, String format) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat(format, new Locale("en", "US"));

		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			throw new Exception("Invalid Date Parser Exception ");
		}
		return date;
	}
	
	public static Date convertStringToDateTh(String strDate, String format) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat(format, new Locale("th", "TH"));

		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			throw new Exception("Invalid Date Parser Exception ");
		}
		return date;
	}
}
