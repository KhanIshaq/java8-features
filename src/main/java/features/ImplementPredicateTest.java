/**
 * 
 */
package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
/**
 * @author nsa
 *
 */
public class ImplementPredicateTest{

	//Static import to make using constants simpler
	//Since it is inside the same package no need to import
	private ImplementPredicate demo = new ImplementPredicate();
	private String[] names;
	
	@Before
	public void setUp() {
		
		names = Stream.of("Yal", "Yash", "Kaylee", "Inara", "Zoë",
				"Jayne", "Yukon", "River", "Shepherd Book")
				.sorted().toArray(String[]::new);
				
	}
	
	@Test
	public void getNamesofLength5() throws Exception {
		assertEquals("Inara, Jayne, River, Yukon", demo.getNamesOfLength(5, names));
	}
	
	@Test
	public void getNamesStartingWithY() throws Exception {
		assertEquals("Yal, Yash, Yukon", demo.getNamesStartingWith("Y", names));
	}
	
	@Test
	public void getNamesSatisfyingCondition() throws Exception {
		
		assertEquals("Inara, Jayne, River, Yukon", demo.getNamesSatisfyingCondition(s -> s.length() == 5, names));
		
		assertEquals("Yal, Yash, Yukon",
	            demo.getNamesSatisfyingCondition(s -> s.startsWith("Y"),
	            names));
		
		
		assertEquals(demo.getNamesSatisfyingCondition(ImplementPredicate.LENGTH_FIVE, names), "Inara, Jayne, River, Yukon");
		
		assertEquals(demo.getNamesSatisfyingCondition(ImplementPredicate.STARTS_WITH_Y, names), "Yal, Yash, Yukon");

	}
	
	@Test
	public void composedPredicate() throws Exception { 
		
		
		assertEquals("Yukon",
	            demo.getNamesSatisfyingCondition(
	                demo.LENGTH_FIVE.and(demo.STARTS_WITH_Y), names));
		
		//Composition = OR
	    assertEquals("Inara, Jayne, River, Yal, Yash, Yukon",
	            demo.getNamesSatisfyingCondition(
	            		demo.LENGTH_FIVE.or(demo.STARTS_WITH_Y), names));
	    
	    //Negation
	    assertEquals("Kaylee, Shepherd Book, Yal, Yash, Zoë",
	            demo.getNamesSatisfyingCondition(demo.LENGTH_FIVE.negate(), names));
	}
	

	//Exercise 3-32. Doubling integers,  ltering, and summing
	@Test
	public void sumDoublesDivisibleBy3() throws Exception { 
		assertEquals(1554, demo.sumDoublesDivisibleBy3(100, 120));
	}
	
	//Exercise 3-59. Concatenating two streams
	//First elements followed by second elements
	@Test
	public void concat() throws Exception {
	Stream<String> first = Stream.of("a", "b", "c").parallel();
	
	Stream<String> second = Stream.of("X", "Y", "Z");
	    List<String> strings = Stream.concat(first, second)
	            .collect(Collectors.toList());
	    List<String> stringList = Arrays.asList("a", "b", "c", "X", "Y", "Z");
	    assertEquals(stringList, strings);
	}
	
	//Exercise 3-60. Concatenating multiple streams
	@Test
	public void concatThree() throws Exception {
	Stream<String> first = Stream.of("a", "b", "c").parallel(); Stream<String> second = Stream.of("X", "Y", "Z"); Stream<String> third = Stream.of("alpha", "beta", "gamma");
	    List<String> strings = Stream.concat(Stream.concat(first, second), third)
	            .collect(Collectors.toList());
	    List<String> stringList = Arrays.asList("a", "b", "c",
	        "X", "Y", "Z", "alpha", "beta", "gamma");
	    assertEquals(stringList, strings);
	}
	
	
	//Exercise 3-61. Concatenating with reduce
	@Test
	public void reduce() throws Exception {
	Stream<String> first = Stream.of("a", "b", "c").parallel(); Stream<String> second = Stream.of("X", "Y", "Z"); Stream<String> third = Stream.of("alpha", "beta", "gamma"); Stream<String> fourth = Stream.empty();
	    List<String> strings = Stream.of(first, second, third, fourth)
	            .reduce(Stream.empty(), Stream::concat)
	            .collect(Collectors.toList());
	    
	    List<String> stringList = Arrays.asList("a", "b", "c",
	        "X", "Y", "Z", "alpha", "beta", "gamma");
	    assertEquals(stringList, strings);
	}
	//Using reduce with an empty stream and a binary operator
	
	//Exercise 3-62. Using  FlatMap to concatenate streams
	@Test
	public void flatMap() throws Exception {
	Stream<String> first = Stream.of("a", "b", "c").parallel(); Stream<String> second = Stream.of("X", "Y", "Z"); Stream<String> third = Stream.of("alpha", "beta", "gamma"); Stream<String> fourth = Stream.empty();
	    List<String> strings = Stream.of(first, second, third, fourth)
	            .flatMap(Function.identity())
	            .collect(Collectors.toList());
	    List<String> stringList = Arrays.asList("a", "b", "c",
	        "X", "Y", "Z", "alpha", "beta", "gamma");
	    assertEquals(stringList, strings);
	}
	
	//Exercise 3-63. Parallel or not?
	@Test
	public void concatParallel() throws Exception {
	Stream<String> first = Stream.of("a", "b", "c").parallel(); Stream<String> second = Stream.of("X", "Y", "Z"); Stream<String> third = Stream.of("alpha", "beta", "gamma");
	    Stream<String> total = Stream.concat(Stream.concat(first, second), third);
	    assertTrue(total.isParallel());
	}
	
	
	//Exercise 3-64. Making a  atMap stream parallel
	@Test
	public void flatMapParallel() throws Exception { Stream<String> first = Stream.of("a", "b", "c").parallel(); Stream<String> second = Stream.of("X", "Y", "Z"); Stream<String> third = Stream.of("alpha", "beta", "gamma"); Stream<String> fourth = Stream.empty();
	    Stream<String> total = Stream.of(first, second, third, fourth)
	            .flatMap(Function.identity());
	    assertFalse(total.isParallel());
	    total = total.parallel();
	    assertTrue(total.isParallel());
	}
	
	
	
	//Exercise 3-38. Testing the palindrome checker
	@Test
	public void isPalindrome() throws Exception { 
		
		assertTrue(Stream.of("Madam, in Eden, I'm Adam",
	                  "Go hang a salami; I'm a lasagna hog",
	                  "Flee to me, remote elf!",
	                  "A Santa pets rats as Pat taps a star step at NASA").allMatch(ImplementPredicate::isPalindrome));
	    
		assertFalse(demo.isPalindrome2("This is NOT a palindrome"));
	}
}
