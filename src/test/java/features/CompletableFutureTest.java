/**
 * 
 */
package features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CompletableFuture;
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
	
	//Exercise 9-25. Composing two Futures together
	@Test
	public void compose() throws InterruptedException, ExecutionException {
		int x = 5;
		int y = 7;
		CompletableFuture<Integer> completablFuture = CompletableFuture
														.supplyAsync(() -> x)
														.thenCompose(n -> CompletableFuture.supplyAsync(() -> n+y));
		assertTrue(12 == completablFuture.get());
	}
	
	//Futures are independent
	//Exercise 9-26: Combining two Futures
	@Test
	public void combine() throws InterruptedException, ExecutionException {
		int x = 6;
		int y = 7;
		CompletableFuture<Integer> completableFuture = CompletableFuture
														.supplyAsync(() -> x)
														.thenCombine(CompletableFuture.supplyAsync(() -> y), (n1, n2) -> n1+n2);
		assertTrue(13 == completableFuture.get());	
				
	}
	
	private CompletableFuture<Integer> getIntegerCompletableFuture(String num){
		return CompletableFuture.supplyAsync(() -> Integer.parseInt(num))
								.handle((val, exec) -> val!=null?val:0);
		//If Parsing is successful hanlde will return the number 
		//If Parsing throws ParsingException handle will return 0
	}
	
	@Test
	public void handleException() throws InterruptedException, ExecutionException {
		String num = "abc";
		CompletableFuture<Integer> value = getIntegerCompletableFuture(num);
		assertTrue(value.get() == 0);
	}
	
	@Test
	public void handleWithoutException() throws InterruptedException, ExecutionException {
		String num = "42";
		CompletableFuture<Integer> value = getIntegerCompletableFuture(num);
		assertTrue(value.get() == 42);
	}
}
