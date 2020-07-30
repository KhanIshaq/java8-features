/**
 * 
 */
package features;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author ishaqkhan
 * 
 * 
 */
public class SampleCompletableFuture {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		Future<Integer> future = service.submit(new Task());
		
		try {
			//If by that time the task is completed then we will get the result
			Integer result = future.get(); //blocking operation will wait until the result is available
			System.out.println("Result = " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	static class Task implements Callable<Integer>{

		@Override
		public Integer call() throws Exception {
			return 1;
		}
		
	}

}
