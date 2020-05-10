/**
 * 
 */
package features;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ishaqkhan
 *
 * The java.time Package
 */
public class Chapter8 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//classes for handling dates : java.util.Date
		//classes for handling times : java.util.Calendar
		
		//java.time package is based on the Joda-Time library
		//The new package was developed under JSR-310: Date and Time API, and supports the ISO 8601 standard. 
		//It correctly adjusts for leap years and daylight savings rules in individual regions.
		
		
		//8.1 Using the Basic Date-Time Classes
		//Use Case
		//You want to use the new date & time packages in java.time
		
		//Factory Methods in classes
//		Instant
//		Duration
//		Period
//		LocalDate
//		LocalTime
//		LocalDateTime
//		ZonedDateTime
		//Classes in Date-Time all produce immutable instances, so they are thread safe.
		//Two important static factory methods: now and of
		
		//Exercise 8-1.  the now factory method
		System.out.println("Instant.now()       : " + Instant.now());
		System.out.println("LocalDate.now()     : " + LocalDate.now());
		System.out.println("LocalTime.now()     : " + LocalTime.now());
		System.out.println("LocalDateTime.now() : " + LocalDateTime.now());
		System.out.println("ZonedDateTime.now() : " + ZonedDateTime.now());
		//Exercise 8-2.  the results of calling the now method
//		Instant.now()       : 2020-02-01T07:32:26.763Z    --> Instant shows the time to nanosecond precision, in Zulu time.
//		LocalDate.now()     : 2020-02-01
//		LocalTime.now()     : 11:32:26.873
//		LocalDateTime.now() : 2020-02-01T11:32:26.873
//		ZonedDateTime.now() : 2020-02-01T11:32:26.873+04:00[Asia/Dubai]  --> append a numerical offset (here, +04:00) using UTC as a base and region name 
		
		//now method also appears in the classes Year, YearMonth, and ZoneId.
		System.out.println("Year.now()          : " + Year.now());
		System.out.println("YearMonth.now()     : " + YearMonth.now());
		System.out.println("ZoneId              : " + ZoneId.systemDefault());
		
		// static 'of' factory method is used to produce new values
		
		//Exercise 8-3.  the 'of' method for the date/time classes
		System.out.println("First landing on the Moon:");
		LocalDate moonLandingDate = LocalDate.of(1969, Month.JULY, 20);
		LocalTime moonLandingTime = LocalTime.of(20, 18);
		System.out.println("Date: " + moonLandingDate);
		System.out.println("Time: " + moonLandingTime);
		
		System.out.println("Neil Armstrong steps onto the surface: ");
		LocalTime walkTime = LocalTime.of(20, 2, 56, 150_000_000);
		LocalDateTime walk = LocalDateTime.of(moonLandingDate, walkTime);
		System.out.println(walk);
		
		//ZonedDateTime class combines dates and times with time zone information from the ZoneId class
//		There are two types of zone IDs:
//		1)Fixed offsets, relative to UTC/Greenwich, like -05:00
//		2)Geographical regions, like America/Chicago
//		Zulu time. It includes a Z along with the numerical value.
//		Classes (ZoneRules  <- ZoneRulesProvider)
//		isDaylightSavings(Instant)
		
		Set<String> regionNames = ZoneId.getAvailableZoneIds();
		//regionNames.forEach(System.out::println);
		System.out.println("There are " +  regionNames.size() + " region names");
		//For jdk1.8.0_172, there are 600 region names.
		
		//Table 8-1. Pre xes used on Date-Time methods
		//Method			Type			Use
		//of				Static factory	Creates an instance
		//from				Static factory	Converts input parameters to target class
		//parse				Static factory	parses an input string
		//format			Instance		Produces formatted output
		//get				Instance		returns part of the object
		//is 				Instance		queries the state of the object
		//with				Instance		Creates a new object by changing one element of an existing one
		//plus, minus		Instance		Creates a new object by adding or subtracting from an existing one
		//to				Instance		Converts an object to another type
		//at				Instance		Combines this object with another
		
		
		//Exercise 8-4. Applying a time zone to a LocalDateTime
		LocalDateTime dateTime = LocalDateTime.of(2020, Month.FEBRUARY, 11, 13, 20, 10);
		ZonedDateTime dub = dateTime.atZone(ZoneId.of("Asia/Dubai"));
		System.out.println(dub);
		ZonedDateTime london = dub.withZoneSameInstant(ZoneId.of("Europe/London"));
		System.out.println(london);
		
		//Two enums in the package: 
		//1) Month
		//2) DayOfWeek
		
		//Exercise 8-5. Some methods in the Month enum
		System.out.println("Days in Feb in a leap year: " + Month.FEBRUARY.length(true));
		System.out.println("Day of year for first day of Aug(leap year): " + Month.AUGUST.firstDayOfYear(true));
		System.out.println("Month.of(1): " + Month.of(1));
		System.out.println("Adding two months: " + Month.JANUARY.plus(2));
		System.out.println("Subtracting a month: " + Month.MARCH.minus(1));
		
		//Enum
		System.out.println(" " + DayOfWeek.FRIDAY);
		//ISO Standard
		System.out.println(" " + DayOfWeek.of(1));
		
		//8.2 Creating Dates and Times from Existing Instances
		//Use Case
		//You want to modify existing instance of one of the Date-Time Classes
		// Use plus or minus methods. Otherwise use the with method.
		
		// LocalDate Methods
		// LocalDate plusDays(long daysToAdd)
		// LocalDate plusWeeks(long weeksToAdd) 
		// LocalDate plusMonths(long monthsToAdd) 
		// LocalDate plusYears(long yearsToAdd)
		
		//LocalTime class has similar methods:
		// LocalTime plusNanos(long nanosToAdd)
		// LocalTime plusSeconds(long secondsToAdd) 
		// LocalTime plusMinutes(long minutesToAdd) 
		// LocalTime plusHours(long hoursToAdd)
		
		
		//LocalDateTime plus(long amountToAdd, TemporalUnit unit);
		//LocalDateTime plus(TemporalAmount amountToAdd)
		
		//LocalDateTime minus(long amountToSubtract, TemporalUnit unit) 
		//LocalDateTime minus(TemporalAmount amountToSubtract)
		
		//Same for LocalDate & LocalTime
		
		//TemporalAmount corresponds to Period or Duration
		
		//Temporal addTo(Temporal temporal)
	    //Temporal subtractFrom(Temporal temporal)
		
		//Series of 'with'method for each class
		//LocalDateTime withNano(int nanoOfSecond) 
		//LocalDateTime withSecond(int second) 
		//LocalDateTime withMinute(int minute) 
		//LocalDateTime withHour(int hour) 
		//LocalDateTime withDayOfMonth(int dayOfMonth) 
		//LocalDateTime withDayOfYear(int dayOfYear) 
		//LocalDateTime withMonth(int month) 
		//LocalDateTime withYear(int year)
		
		//There are also with methods that take a TemporalAdjuster or a TemporalField: 
		//LocalDateTime with(TemporalAdjuster adjuster)
		//LocalDateTime with(TemporalField field, long newValue)
		
		//8.3 Adjusters and Queries
		//Use Case
		//Given a temporal value, you want to adjust it to a new one based on your own logic, or you want to retrieve information about it.
		//1) TemporalAdjuster
		//2) TemporalQuery
		
		//LocalDateTime with(TemporalAdjuster adjuster)
		
		//TemporalAdjusters class methods
		//static TemporalAdjuster firstDayOfNextMonth() static TemporalAdjuster firstDayOfNextYear() static TemporalAdjuster firstDayOfYear()
		//static TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek) 
		//static TemporalAdjuster lastDayOfMonth()
		//static TemporalAdjuster lastDayOfYear()
		//static TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek)
		//static TemporalAdjuster next(DayOfWeek dayOfWeek)
		//static TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek) 
		//static TemporalAdjuster previous(DayOfWeek dayOfWeek) 
		//static TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek)
		
		//TemporalAdjuster is a functional interface, whose single abstract method is:
	    //Temporal adjustInto(Temporal temporal)
		
		//<R> R query(TemporalQuery<R> query)
		// 'query' method invokes queryFrom
		//TemporalQuery.queryFrom(TemporalAccessor)
		//All the methods on TemporalAccessor are available for performing the calculation.
		
		//static TemporalQuery<Chronology>
		//static TemporalQuery<LocalDate>
		//static TemporalQuery<LocalTime> 
		//static TemporalQuery<ZoneOffset>
		//static TemporalQuery<TemporalUnit> precision() 
		//static TemporalQuery<ZoneId> zone() 
		//static TemporalQuery<ZoneId> zoneId()
		
		//TemporalQuery interface has only a single abstract method:
		//R queryFrom(TemporalAccessor temporal)
		
		//we have a method that, given a TemporalAccessor, computes the number of days between the argument and International Talk Like A Pirate Day, September 19.5 
		
		
		//8.4 Convert from java.util.Date to java.time.LocalDate
		//Use Case
		//You want to convert from java.util.Date or java.util.Calendar to the new classes in the java.time package.
		
		
		//Example 8-19. Conversion methods in java.sql.Date
		//LocalDate toLocalDate()
	    //static Date valueOf(LocalDate date)
		
		//Example 8-20. Conversion methods in java.sql.Timestamp
		//LocalDateTime toLocalDateTime()
		//static Timestamp valueOf(LocalDateTime dateTime)
		
		//Exercise 8-21. Converting java.util classes to java.time classes (more to come)
		Date curr = new Date();
		System.out.println("Util Time -> " + curr);
		System.out.println("Time API  -> " + convertFromUtilDateUsingInstant(curr));
		
		//Exercise 8-19. Conversion methods in java.sql.Date
		//LocalDate toLocalDate();
		//static Date valueOf(LocalDate date);
		//Exercise 8-20. Conversion methods in java.sql.Timestamp
		//LocalDateTime toLocalDateTime()
		//static Timestamp valueOf(LocalDateTime dateTime);
		
		//8.5 Parsing and Formatting
		//Use Case
		//You want to parse and/or format the new date-time classes.

		//DateTimeFormatter class 
		//from constants like ISO_LOCAL_DATE to pattern letters like uuuu-MMM-dd to localized styles for any given Locale.
		
		//Example 8-28. Methods to parse and format LocalDate instances
		//static LocalDate parse(CharSequence text)  // uses ISO_LOCAL_DATE
		//static LocalDate parse(CharSequence text, DateTimeFormatter formatter)
		//String format(DateTimeFormatter formatter) 
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Raw    = " + now);
		String text = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		System.out.println("Format = " + text);
		LocalDateTime dateTime1 = LocalDateTime.parse(text);
		System.out.println("Parsed = " + dateTime1);
		
		//Example 8-30. Formatting dates
		LocalDate date = LocalDate.of(2020, Month.FEBRUARY, 5);
		
		System.out.println("Full   : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
		System.out.println("Long   : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
		System.out.println("Medium : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		System.out.println("Short  : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
		
		System.out.println("France  : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.FRANCE)));
		System.out.println("India   : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("hin", "IN"))));
		System.out.println("Brazil  : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"))));
		System.out.println("Japan   : " +
			    date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.JAPAN)));
		
		Locale loc = new Locale.Builder()
				.setLanguage("sr")
				.setScript("Latn")
				.setRegion("RS")
				.build();
		System.out.println("Serbia  : " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(loc)));
		
		LocalDateTime dateTime2 = LocalDateTime.of(2020, Month.FEBRUARY, 5, 13, 20, 22);
		System.out.println("Full    : " + dateTime2.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault())));
		System.out.println("Long    : " + dateTime2.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.systemDefault())));
		System.out.println("Medium  : " + dateTime2.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.systemDefault())));
		System.out.println("Short   : " + dateTime2.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault())));
		
		ZonedDateTime zdt = ZonedDateTime.of(LocalDate.of(1969, Month.JULY, 20),
				LocalTime.of(20, 18, 20) , ZoneId.of("UTC"));
		System.out.println(zdt);
		System.out.println(zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MMMM/dd hh:mm:ss a zzz GG");
		System.out.println(zdt.format(formatter));
		
		formatter = DateTimeFormatter.ofPattern("uuuu/MMMM/dd hh:mm:ss a VV xxxxx");
		System.out.println(zdt.format(formatter));
		
		// In the United States, daylight savings moves the clocks forward at 2 A.M. on March 11, 2018, in the Eastern time zone.
		//Example 8-32. Move the clocks forward
		//overload of the 'of' method 
		ZonedDateTime zdt1 = ZonedDateTime.of(2018, 3, 11, 2, 30, 0, 0, ZoneId.of("America/New_York"));
		System.out.println(zdt1.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)));
		//Sunday, March 11, 2018 3:30:00 AM EDT
		
		//8.6 Finding Time Zones with Unusual O sets
		//Use Case
		//You want to find all the time zones with non-integral hour offsets.
		//Solution
		//Get the time zone offset for each time zone and determine its remainder when divid‐ ing the total seconds by 3,600.
		//FunnyOffsets.java
			    
			    
		//8.7 Finding Region Names from O sets
		//Use Case
		//You want to know the ISO 8601 region name given an offset from UTC.
		//The ISO 8601 specification defines time zone IDs two ways:
		// By region name, like “America/Chicago”
		// By offset from UTC in hours and minutes, like “+05:30”
		
		//8.8 Time Between Events
		//Use Case
		//You need to know the amount of time between two events.

		System.out.println(getRegionNamesForZoneId(ZoneId.systemDefault()));
		
		//use the between or until methods on the temporal classes or between method on Period to generate a Period object.
		//Otherwise use the Duration class for seconds and nanoseconds on the timeline.
		
		//Interface java.time.temporal.TemporalUnit,
		//enum ChronoUnit
		//long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive)
		
		//
		LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
		LocalDate today = LocalDate.now();
		
		System.out.printf("%d day(s) to go... %n", ChronoUnit.DAYS.between(today, electionDay));
		//Other constants in ChronoUnit include HOURS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, and more.11
		//Period Class
		// In java.time.LocalDate 
		//Period until(ChronoLocalDate endDateExclusive)
		
		//Using the Period class
		getYearsMonthsDaysFromPeriod();
		//The Period class is used when you need to deal with human-readable times, like days, months, and years.
		
		//Using the Duration class
		//The Duration class represents an amount of time in terms of seconds and nanoseconds
		Instant start = Instant.now();
		// ... call method to be timed ...
		Instant end = Instant.now(); 
		System.out.println(getTiming(start, end) + " seconds");
		
		//The Duration class has conversion methods: toDays, toHours, toMillis, toMinutes, and toNanos, 
		//which is why the getTiming method in Exercise 8-42 used toMillis and divided by 1,000.
	}
	
	//Exercise 8-18. Converting java.util.Date to java.time.LocalDate via Instant
	public static LocalDate convertFromUtilDateUsingInstant(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	//Exercise 8-35. Getting region names given an o set
	public static List<String> getRegionNamesForOffset(ZoneOffset offset){
		LocalDateTime now = LocalDateTime.now();
		return ZoneId.getAvailableZoneIds().stream()
				.map(ZoneId::of)
				.filter(zoneId -> now.atZone(zoneId).getOffset().equals(offset))
				.map(ZoneId::toString)
				.sorted()
				.collect(Collectors.toList());
	}
	
	public static List<String> getRegionNamesForZoneId(ZoneId zoneId){
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zdt = now.atZone(zoneId);
		ZoneOffset offset = zdt.getOffset();
		return getRegionNamesForOffset(offset);
		
	}
	
	//Exercise 8-38. Getting region names given an hour and minute o set
	public static List<String> getRegionNamesForOffset(int hours, int minutes) { 
		ZoneOffset offset = ZoneOffset.ofHoursMinutes(hours, minutes);
		return getRegionNamesForOffset(offset);
	}
	
	//Exercise 8-41. Using Period to get days, months, and years
	public static void getYearsMonthsDaysFromPeriod() {
		LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
		LocalDate today = LocalDate.now();
		Period until = today.until(electionDay);
		int years = until.getYears();
		int months = until.getMonths();
		int days = until.getDays();
		System.out.printf("%d year(s), %d month(s), and %d day(s)%n", years, months, days);
	}
	
	//A primitive timing mechanism using Duration is shown in Example 8-42. Example 8-42. Timing a method
	public static double getTiming(Instant start, Instant end) { 
		return Duration.between(start, end).toMillis()/1000.0;
	}


}
