package features;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.IntStream;

//Example 8-13. Utility class with adjusters
public class Adjusters {

	public static Temporal adjustInto(Temporal input) {
		LocalDate date = LocalDate.from(input); 
		// ... implementation as before ...
		int day;
		if(date.getDayOfMonth() < 15) {
			day = 15;
		}else {
			day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		}
		date = date.withDayOfMonth(day);
		if(date.getDayOfWeek() == DayOfWeek.SATURDAY 
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
		}
		return input.with(date);
	} 
}
//Does not implement TemporalAdjuster
//static method so no instantiation required