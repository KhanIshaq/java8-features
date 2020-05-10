/**
 * 
 */
package features;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author ishaqkhan
 * Exercise 8-21. Converting java.util classes to java.time classes (more to come)
 */
public class ConvertDate {
	
	public LocalDate convertFromSqlDateToLocalDate(java.sql.Date sqlDate) {
		return sqlDate.toLocalDate();
	}
	
	public java.sql.Date convertFromLocalDateToSqlDate(LocalDate localDate) {
		return java.sql.Date.valueOf(localDate);
	}

	public LocalDateTime convertFromSqlDateToLocalDateTime(java.sql.Timestamp ts) {
		return ts.toLocalDateTime();
	}
	
	public java.sql.Timestamp convertFromLocalDateToSqlDate(LocalDateTime localDateTime) {
		return java.sql.Timestamp.valueOf(localDateTime);
	}

	//Exercise 8-22. Converting a java.util.Date to a java.time.LocalDate
	public LocalDate convertUtilDateToLocalDate(java.util.Date date) { 
		return new java.sql.Date(date.getTime()).toLocalDate();
	}
	
	//Exercise 8-23. Converting from java.util.Calendar to java.time.ZonedDateTime
	public ZonedDateTime convertFromCalendar(Calendar cal){
		return ZonedDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
	}
	
	//Exercise 8-24. Using getter methods from Calendar to LocalDateTime
	public LocalDateTime convertFromCalendarUsingGetters(Calendar cal) {
		return LocalDateTime.of(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE),
				cal.get(Calendar.HOUR),
				cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
				
	}
	
	//Exercise 8-25. Generating and parsing a timestamp string
	public LocalDateTime convertFromUtilDateToLDUsingString(java.util.Date date) { 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
		return LocalDateTime.parse(df.format(date), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	//Exercise 8-26. Converting a GregorianCalendar to a ZonedDateTime
	public ZonedDateTime convertFromGregorianCalendar(Calendar cal) {
		GregorianCalendar gcal = (GregorianCalendar) cal;
		return gcal.toZonedDateTime();
	}
	
	//Exercise 8-27. Converting java.util.Dat to java.time.LocalDate (JAVA 9 ONLY)
//	public LocalDate convertFromUtilDateJava9(java.util.Date date) {
//		return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
//	}
	
}
