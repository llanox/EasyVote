package com.llanox.mobile.easyvote.data.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	public static String FORMAT_DATETIME_FULL="yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_DATETIME_SHORT="yyyy-MM-dd";
	public static String FORMAT_DAY_MONTH_LETTERS="d 'de' MMMM";
	public static String FORMAT_DAY_MONTH_SINCRONIZAR="dd/MM/yy HH:mm a";

	
	public static String changeDateToFormatString(Date fecha,String pattern ){
		DateFormat formato=new SimpleDateFormat(pattern);
		return formato.format(fecha);
	}

	public static int getCurrentDayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		return weekday;
	}
	

	
	public static Date changeStringToDate(String fecha,String pattern ) throws ParseException{
		DateFormat formato=new SimpleDateFormat(pattern);
		return formato.parse(fecha);
	}

	public static String changeMillisToFormat(long milliseconds, String pattern) {
		Date fecha = new Date(milliseconds);
		return changeDateToFormatString(fecha, pattern);
	}
}
