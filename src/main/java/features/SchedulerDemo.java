/**
 * 
 */
package features;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ishaqkhan
 * 
 */
public class SchedulerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		try {
			
			executor.execute(() -> System.out.println("Starting Thread 1 -> " + Thread.currentThread()));
			Thread.sleep(1000);
			executor.execute(() -> System.out.println("Starting Thread 2 -> " + Thread.currentThread()));
			Thread.sleep(1000);
			executor.execute(() -> System.out.println("Starting Thread 3 -> " + Thread.currentThread()));
			Thread.sleep(1000);
			executor.execute(() -> System.out.println("Starting Thread 4 -> " + Thread.currentThread()));
			Thread.sleep(1000);
			executor.execute(() -> System.out.println("Starting Thread 5 -> " + Thread.currentThread()));
			Thread.sleep(1000);
			executor.execute(() -> System.out.println("Starting Thread 6 -> " + Thread.currentThread()));
			
			executor.execute(() -> System.out.println("Starting Thread 7 -> " + Thread.currentThread()));
			
			executor.execute(() -> System.out.println("Starting Thread 8 -> " + Thread.currentThread()));
			
			executor.execute(() -> System.out.println("Starting Thread 9 -> " + Thread.currentThread()));
			
			executor.execute(() -> System.out.println("Starting Thread 10 -> " + Thread.currentThread()));
			System.out.println("Shutting down !!!");
			executor.shutdown();
			System.out.println("Awaiting Termination !!!");
			executor.awaitTermination(10, TimeUnit.SECONDS);
			System.out.println("Done!");
		} catch (Exception e) {
			
		}
		System.out.println("Completed");
	}

}
