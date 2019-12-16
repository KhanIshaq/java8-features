/**
 * 
 */
package features;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
/**
 * @author nsa
 *
 */
public class PrimesTest {

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

}
