/**
 * 
 */
package features;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author nsa
 *
 */
public class ParallelStreamTest {

	//Example 9-2. Using the parallelStream method
	@Test
	public void parallelStreamMethodOnCollection() throws Exception { 
		List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9); 
		assertTrue(numbers.parallelStream().isParallel());
	}
	//This will be sequential if you create your own spliterator, which is pretty unusual.5
	
	@Test
	public void parallelMethodOnStream() throws Exception{
			//assertTrue(Arrays.asList(3,1,4,1,5,9).stream().parallel().isParallel());
			assertTrue(Stream.of(3,1,4,1,5,9).parallel().isParallel());
	}
	
	//Example 9-4. Converting a parallel stream to sequential
	@Test
	public void parallelStreamThenSequential()throws Exception{
		List<Integer> numbers = Arrays.asList(3,1,4,1,5,9);
		assertFalse(numbers.parallelStream().sequential().isParallel());
	}
}
