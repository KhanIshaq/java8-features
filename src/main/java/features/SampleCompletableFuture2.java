/**
 * 
 */
package features;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ishaqkhan
 * 
 * 
 */
public class SampleCompletableFuture2 {

	private static CompletableFuture<Order> future5;
	//Independent Order Processing Flows
	public static void main(String[] args) {
		//ExecutorService service = Executors.newFixedThreadPool(10);
		
		try {
			/*
			Future<Integer> future = service.submit(getOrderTask());
			Integer order = future.get(); //blocking 
			
			Future<Integer> future1 = service.submit(enrichOrderTask(order));
			order = future1.get(); //blocking 
			
			Future<Integer> future2 = service.submit(performOrderPaymentTask(order));
			order = future2.get(); //blocking 
			
			Future<Integer> future3 = service.submit(dispatchOrderTask(order));
			order = future3.get(); //blocking 
			
			Future<Integer> future4 = service.submit(emailOrderTask(order));
			order = future4.get(); //blocking 
			
			
			System.out.println(order);
			*/
			/**/
			for(int i = 0; i<5; i++) {
				System.out.println("->" + Thread.currentThread());
				//ExecutorService cpuBound = Executors.newFixedThreadPool(4);
				//ExecutorService ioBound = Executors.newCachedThreadPool();
				//Chaining of dependent tasks
				
				
				System.out.println(CompletableFuture.supplyAsync(() -> getOrderTask1())
									.thenApplyAsync(o1 -> enrichOrderTask1(o1))
									//.thenApplyAsync(o1 -> enrichOrderTask1(o1)) // Asynchronous different thread to process the task
									.thenApplyAsync(o1 -> performOrderPaymentTask1(o1))
									.thenApplyAsync(o1 -> dispatchOrderTask1(o1))
									.thenApplyAsync(o1 -> emailOrderTask1(o1)).get().get().toString());
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Callable<Integer> getOrderTask() {
		return new Task();
	}
	public static Callable<Integer> enrichOrderTask(Integer order) {
		return new Task();
	}
	public static Callable<Integer> performOrderPaymentTask(Integer order) {
		return new Task();
	}
	public static Callable<Integer> dispatchOrderTask(Integer order) {
		return new Task();
	}
	public static Callable<Integer> emailOrderTask(Integer order) {
		return new Task5();
	}
	
	
	public static CompletableFuture<Order> getOrderTask1() {
		System.out.println("-" + Thread.currentThread());
		return CompletableFuture.supplyAsync(() -> new Order(1));
	}
	public static CompletableFuture<Order> enrichOrderTask1(CompletableFuture<Order> order) {
		System.out.println("--" + Thread.currentThread());
		try {
			Order o = order.get();
			o.setUserName("ishaqkhan");
			return CompletableFuture.supplyAsync(() -> o);
		} catch (InterruptedException | ExecutionException e) {
		}
		throw new RuntimeException("Unable to enrich the order");
	}
	public static CompletableFuture<Order> performOrderPaymentTask1(CompletableFuture<Order> order) {
		System.out.println("---" + Thread.currentThread());
		try {
			Order o = order.get();
			o.setAmount(new Double(1000.00));
			return CompletableFuture.supplyAsync(() -> o);
		} catch (InterruptedException | ExecutionException e) {
		}
		throw new RuntimeException("Unable to perform the order payment");
	}
	public static CompletableFuture<Order> dispatchOrderTask1(CompletableFuture<Order> order) {
		System.out.println("----" + Thread.currentThread());
		try {
			Order o = order.get();
			o.setDate(new Date());
			return CompletableFuture.supplyAsync(() -> o);
		} catch (InterruptedException | ExecutionException e) {
		}
		throw new RuntimeException("Unable to dispatch the order");
	}
	public static CompletableFuture<Order> emailOrderTask1(CompletableFuture<Order> order) {
		System.out.println("-----" + Thread.currentThread());
		try {
			Order o = order.get();
			LocalDate ld = LocalDate.now();
			LocalDate ld5 = ld.plusDays(5);
			o.setDeliveryDate(Date.from(ld5.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			return CompletableFuture.supplyAsync(() -> o);
		} catch (InterruptedException | ExecutionException e) {
		}
		throw new RuntimeException("Unable to email the order");
	}
	
	static class Task implements Callable<Integer>{
		@Override
		public Integer call() throws Exception {
			return 1;
		}
		
	}
	
	static class Task5 implements Callable<Integer>{
		@Override
		public Integer call() throws Exception {
			return 5;
		}
		
	}
}
