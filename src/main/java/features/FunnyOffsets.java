package features;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;

//Example 8-33. Finding the o set seconds for each zone ID
public class FunnyOffsets {

	public static void main(String[] args) {
		Instant instant = Instant.now();
		
        ZonedDateTime current = instant.atZone(ZoneId.systemDefault());
        
        System.out.printf("Current time is %s%n%n", current);
        
        System.out.printf("%10s %20s %13s%n", "Offset", "ZoneId", "Time");
        //10    Offset
        //13          Time
        //10       Time
        ZoneId.getAvailableZoneIds().stream()
        .map(ZoneId::of)
        .filter(zoneId ->
        {
        	ZoneOffset offset = instant.atZone(zoneId).getOffset();
        	return offset.getTotalSeconds() % ( 60 * 60 )!=0; //Only use zone IDs whose offsets are not divisible by 3,600
        }).sorted(Comparator.comparingInt(zoneId ->
            instant.atZone(zoneId)
            .getOffset()
            .getTotalSeconds()))
        	.forEach(zoneId -> 
        	{
        		ZonedDateTime zdt = current.withZoneSameInstant(zoneId);
        		System.out.printf("%10s %25s %10s%n", zdt.getOffset(),zoneId,zdt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        	});
        }
}