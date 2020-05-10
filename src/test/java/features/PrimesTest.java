/**
 * 
 */
package features;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
/**
 * @author nsa
 *
 */
public class PrimesTest {

	//Exercise 5-1. Returning a collection and  ltering out nulls
	List<String> strings = Arrays.asList(
	"this", null, "is", "a", null, "list", "of", "strings", null);
	List<String> nonNullStrings = strings.stream()
	    .filter(Objects::nonNull)
	    .collect(Collectors.toList());
	
	//Exercise 3-52. Tests for the prime calculation
	private Primes calculator = new Primes();
	@Test
	public void testIsPrimeUsingAllMatch() throws Exception { 
		assertTrue(IntStream.of(2, 3, 5, 7, 11, 13, 17, 19)
	        .allMatch(calculator::isPrime));
	}
	//Use allMatch for simplicity
	
	@Test
	public void testIsPrimeWithComposites() throws Exception { 
		assertFalse(Stream.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20)
				.anyMatch(calculator::isPrime));
	}
	//Test with composites
	
	@Test
	public void emptyStreamsDanger() throws Exception { 
		assertTrue(Stream.empty().allMatch(e -> false)); 
		
		assertTrue(Stream.empty().noneMatch(e -> true)); 
		
		assertFalse(Stream.empty().anyMatch(e -> true));
	}
	
	//Exercise 5-2. Testing the  later
	@Test
	public void testNonNulls() throws Exception { 
		List<String> strings =
	        Arrays.asList("this", "is", "a", "list", "of", "strings");
		assertTrue(Objects.deepEquals(strings, nonNullStrings));
	}

}
