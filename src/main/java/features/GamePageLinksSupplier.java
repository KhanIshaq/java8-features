/**
 * 
 */
package features;

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
		try {
			//Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
			//Elements newsHeadlines = doc.select("#mp-itn b a");
			//for (Element headline : newsHeadlines) {
			//  System.out.printf("%s \t %s %n", headline.attr("title"), headline.absUrl("href"));
			//}
			
			Document doc = Jsoup.connect("http://gd2.mlb.com/components/game/mlb/").get();
			Elements links = doc.select("ul li a");
			for(Element link : links) {
				System.out.printf("%s \t %s %n", link.attr("href") , link.text());
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
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
