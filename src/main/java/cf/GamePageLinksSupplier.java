/**
 * 
 */
package cf;

import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author ishaqkhan
 * 
 * Exercise 9-28. Get the game links for a range of dates
 * 
 */
public class GamePageLinksSupplier implements Supplier<List<String>>{

	private static Logger logger = Logger.getLogger(GamePageLinksSupplier.class.getName());
	
	private static final String BASE = "http://gd2.mlb.com/components/game/mlb/";
	private LocalDate startDate;
	private int days;
	
	public GamePageLinksSupplier(LocalDate startDate, int days) {
		this.startDate = startDate;
		this.days = days;
	}
	
	public static List<String> getGamePageLinks(LocalDate localDate) {
		// Use the JSoup library to parse the HTML web page and // extract the links that start with "gid"
		String formattedDate = String.format("year_%4s/month_%02d/day_%02d%n", 
				localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
		try {
			
			Document doc = Jsoup.connect(BASE + formattedDate).get();
			Elements links = doc.select("a");
			return links.stream()
						.filter(link -> link.attr("href").contains("gid"))
						.map(link -> link.attr("href"))
						.collect(Collectors.toList());
			
			//for(Element link : links) {
			//	System.out.printf("%s \t %s %n", link.attr("href") , link.text());
			//}
			
			
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<String> get() {
		return Stream.iterate(startDate, d->d.plusDays(1))
					 .limit(days)
					 .map(GamePageLinksSupplier::getGamePageLinks)
					 .flatMap(list -> list.isEmpty()? Stream.empty():list.stream())
					 .collect(Collectors.toList());
	}

	
}
