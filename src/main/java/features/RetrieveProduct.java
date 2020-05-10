/**
 * 
 */
package features;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author ishaqkhan
 *
 */
public class RetrieveProduct {

	//Example 9-19. Retrieving a product
	private Map<Integer, Product> cache = new HashMap<>();
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private Product getLocal(int id) {
		return cache.get(id);
	}

	private Product getRemote(int id) {
		try {
			Thread.sleep(100);
			if (id == 666) {
				throw new RuntimeException("Evil request");
			}
		} catch (InterruptedException ignored) {
		}
		return new Product(id, "name");
	}
	
//	private static Chapter9 c9 = new Chapter9();
//	public static void main(String[] args) {
//		CompletableFuture.supplyAsync(() -> c9::sleepThenReturnString)
//		.thenApply(Integer::parseInt)
//		.thenApply(n -> n*2)
//		.thenAccept(System.out::println)
//		.join();
//		System.out.println("Processing...");
//	}
	
}
