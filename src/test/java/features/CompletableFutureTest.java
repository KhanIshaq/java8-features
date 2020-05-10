/**
 * 
 */
package features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
/**
 * @author ishaqkhan
 *
 */
public class CompletableFutureTest {

	private Chapter9 demo = new Chapter9();
	
	@Test(expected = ExecutionException.class)
	public void testException() throws InterruptedException, ExecutionException {
		demo.getProduct(666).get();
		//The get method declares an ExecutionException, which is a checked exception.
	}
	
	@Test
	public void testExceptionWithCause() throws Exception {
		try {
			demo.getProduct(666).get();
			fail("Houston, we have a problem...");
		} catch (ExecutionException e) {
			assertEquals(ExecutionException.class, e.getClass());
			assertEquals(RuntimeException.class, e.getCause().getClass());
			//The join method is the same as get except that it throws an unchecked CompletionException if completed excep‐ tionally
		}
	}
}
