/**
 * 
 */
package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * @author ishaqkhan
 *
 */
public class TimePackageTest {

	//Exercise 8-6. Using plus methods on LocalDate and LocalTime
	@Test
	public void localDatePlus() throws Exception {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	LocalDate start = LocalDate.of(2020, Month.FEBRUARY, 2);
	    LocalDate end = start.plusDays(3);
	    assertEquals("2020-02-05", end.format(formatter));
	    end = start.plusWeeks(5);
	    assertEquals("2020-03-08", end.format(formatter));
	    end = start.plusMonths(7);
	    assertEquals("2020-09-02", end.format(formatter));
	    end = start.plusYears(2);
	    assertEquals("2022-02-02", end.format(formatter));
	}
	
	@Test
	public void localTimePlus() throws Exception {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
	    LocalTime start = LocalTime.of(11, 30, 0, 0);
	LocalTime end = start.plusNanos(1_000_000);
	    assertEquals("11:30:00.001", end.format(formatter));
	    end = start.plusSeconds(20);
	    assertEquals("11:30:20", end.format(formatter));
	    end = start.plusMinutes(45);
	    assertEquals("12:15:00", end.format(formatter));
	    end = start.plusHours(5);
	    assertEquals("16:30:00", end.format(formatter));
	}
	
	//Exercise 8-7.  e plus and minus methods
	@Test
	public void plus_minus() throws Exception{
		Period period = Period.of(2, 3, 4); // 2 years 3 months 4 days
		LocalDateTime start = LocalDateTime.of(2020, Month.FEBRUARY, 3, 22, 22);
		LocalDateTime end = start.plus(period);
		assertEquals("2022-05-07T22:22:00", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		end = start.plus(3, ChronoUnit.HALF_DAYS);
		assertEquals("2020-02-05T10:22:00", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		end = start.minus(period);
		assertEquals("2017-10-30T22:22:00", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		end = start.minus(2, ChronoUnit.CENTURIES);
		assertEquals("1820-02-03T22:22:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		end = start.plus(3, ChronoUnit.MILLENNIA);
		assertEquals("5020-02-03T22:22:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	
	//Exercise 8-8. Using with methods on LocalDateTime
	@Test
	public void with() throws Exception {
		LocalDateTime start = LocalDateTime.of(2017, Month.FEBRUARY, 2, 11, 30); 
		LocalDateTime end = start.withMinute(45); 
		assertEquals("2017-02-02T11:45:00",
			end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		end = start.withHour(16);
		assertEquals("2017-02-02T16:30:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		end = start.withDayOfMonth(28);
		assertEquals("2017-02-28T11:30:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		end = start.withDayOfYear(300);
		assertEquals("2017-10-27T11:30:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		end = start.withYear(2020);
		assertEquals("2020-02-02T11:30:00",
		    end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	
	@Test(expected = DateTimeException.class) 
	public void withInvalidDate() throws Exception {
		    LocalDateTime start = LocalDateTime.of(2017, Month.FEBRUARY, 2, 11, 30);
		    start.withDayOfMonth(29);
		    //Since 2017 is not a leap year, you can’t set the date to February 29. 
		    //The result is a DateTimeException, as the last test shows.
	}
	
	//Exercise 8-9. Adjusting the month to an invalid value
	@Test
	public void temporalField() throws Exception {
		LocalDateTime start = LocalDateTime.of(2017, Month.JANUARY, 31, 11, 30); 
		LocalDateTime end = start.with(ChronoField.MONTH_OF_YEAR, 2); 
		assertEquals("2017-02-28T11:30:00",end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	//Exercise 8-10. Using static methods in TemporalAdjusters
	@Test
	public void adjusters()throws Exception {
		LocalDateTime start = LocalDateTime.of(2017, Month.JANUARY, 2, 11, 30);
		LocalDateTime end   = start.with(TemporalAdjusters.firstDayOfNextMonth());
		assertEquals("2017-02-01T11:30", end.toString());
		
		end = start.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
		assertEquals("2017-01-05T11:30", end.toString());
		
		end = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY));
		assertEquals("2016-12-29T11:30", end.toString());
	}
	
	//Exercise 8-12. Testing the adjuster for July 2017
	@Test
	public void payDay() throws Exception { 
		TemporalAdjuster adjuster = new PaydayAdjuster(); 
		IntStream.rangeClosed(1, 14)
	           .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
	           .forEach(date -> assertEquals(14, date.with(adjuster).getDayOfMonth()));
		
		IntStream.rangeClosed(15, 31)
	           .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
	           .forEach(date -> assertEquals(31, date.with(adjuster).getDayOfMonth()));
	}
	
	//Exercise 8-14. Using a method reference for the temporal adjuster
	@Test
	public void payDayMethodReference() throws Exception {  
		IntStream.rangeClosed(1, 14)
	           .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
	           .forEach(date -> assertEquals(14, date.with(Adjusters::adjustInto).getDayOfMonth()));
		
		IntStream.rangeClosed(15, 31)
	           .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
	           .forEach(date -> assertEquals(31, date.with(Adjusters::adjustInto).getDayOfMonth()));
	}
	
	//Exercise 8-15. Using the methods from TemporalQueries
	@Test
	public void queries() throws Exception { 
		
		assertEquals(ChronoUnit.DAYS,
	        LocalDate.now().query(TemporalQueries.precision()));
		
	    assertEquals(ChronoUnit.NANOS,
	        LocalTime.now().query(TemporalQueries.precision()));
	    
	    assertEquals(ZoneId.systemDefault(),
	        ZonedDateTime.now().query(TemporalQueries.zone()));
	    
	    assertEquals(ZoneId.systemDefault(), ZonedDateTime.now().query(TemporalQueries.zoneId()));
	}

	//Exercise 8-16. Method to calculate days until Talk Like A Pirate Day
	private long daysUntilPirateDay(TemporalAccessor temporal) { 
		int day = temporal.get(ChronoField.DAY_OF_MONTH);
		int month = temporal.get(ChronoField.MONTH_OF_YEAR); 
		int year = temporal.get(ChronoField.YEAR);
		LocalDate date = LocalDate.of(year, month, day); 
		LocalDate tlapd = LocalDate.of(year, Month.SEPTEMBER, 19); 
		if (date.isAfter(tlapd)) {
	        tlapd = tlapd.plusYears(1);
	    }
		return ChronoUnit.DAYS.between(date, tlapd);
	}
	
	//Exercise 8-17. Using a TemporalQuery via a method reference
	@Test
	public void pirateDay() throws Exception { 
		IntStream.range(10, 19)
	             .mapToObj(n -> LocalDate.of(2017, Month.SEPTEMBER, n))
	             .forEach(date ->
	             assertTrue(date.query(this::daysUntilPirateDay) <= 9)); 
		
		IntStream.rangeClosed(20, 30)
			.mapToObj(n -> LocalDate.of(2017, Month.SEPTEMBER, n))
			.forEach(date -> {
				Long days = date.query(this::daysUntilPirateDay);
			     assertTrue(days >= 354 && days < 365);
			 });
	}
	
	//Exercise 8-37. Getting the current region names
	@Test
	public void getRegionNamesForSystemDefault() throws Exception { 
		ZonedDateTime now = ZonedDateTime.now();
		ZoneId zoneId = now.getZone();
		List<String> names = Chapter8.getRegionNamesForZoneId(zoneId);
	    assertTrue(names.contains(zoneId.getId()));
	}
	
	//Exercise 8-39. Testing region names for a given o set
	@Test
	public void getRegionNamesForGMT() throws Exception { 
		List<String> names = Chapter8.getRegionNamesForOffset(0, 0);
	    assertTrue(names.contains("GMT"));
	    assertTrue(names.contains("Etc/GMT"));
	    assertTrue(names.contains("Etc/UTC"));
	    assertTrue(names.contains("UTC"));
	    assertTrue(names.contains("Etc/Zulu"));
	}
	@Test
	public void getRegionNamesForNepal() throws Exception { 
		List<String> names = Chapter8.getRegionNamesForOffset(5, 45);
	    assertTrue(names.contains("Asia/Kathmandu"));
	    assertTrue(names.contains("Asia/Katmandu"));
	}
	@Test
	public void getRegionNamesForChicago() throws Exception {
		ZoneId chicago = ZoneId.of("America/Chicago");
		List<String> names = Chapter8.getRegionNamesForZoneId(chicago);
	    assertTrue(names.contains("America/Chicago"));
	    assertTrue(names.contains("US/Central"));
	    assertTrue(names.contains("Canada/Central"));
	    assertTrue(names.contains("Etc/GMT+5") || names.contains("Etc/GMT+6"));
	}

}

