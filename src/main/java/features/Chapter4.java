/**
 * 
 */
package features;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author nsa
 *
 */
public class Chapter4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		//Exercise 4-14. A collection of books
		List<Book> books = Arrays.asList(
		new Book(1, "Modern Java Recipes", 49.99),
		new Book(2, "Java 8 in Action", 49.99),
		new Book(3, "Java SE8 for the Really Impatient", 39.99),
		new Book(4, "Functional Programming in Java", 27.64),
		new Book(5, "Making Java Groovy", 45.99),
		new Book(6, "Gradle Recipes for Android", 23.76)
		);
		
		//In many situations, instead of a List you might want a Map, where the keys are the book IDs and the values are the books themselves. This is really easy to accomplish using the toMap method in Collectors, as shown two different ways in Example 4-15.
		//Example 4-15. Adding the books to a Map
		Map<Integer, Book> bookMap = books.stream()
		    .collect(Collectors.toMap(Book::getId, b -> b));
		
		bookMap.forEach((k,v) -> System.out.println(k + " -  " + v));
		
		bookMap = books.stream()
		    .collect(Collectors.toMap(Book::getId, Function.identity()));

		bookMap.forEach((k,v) -> System.out.println(k + ": " + v));
		
	}

}
