/**
 * 
 */
package features;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author ishaqkhan
 * 
 * 
 */
public class SampleFuture {
	
	public static boolean sleep(int ms) {
		try {
			Thread.sleep(ms);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static int compute() {
		//System.out.println("In Compute = " + Thread.currentThread());
		//sleep(1000);
		throw new RuntimeException("Something went wrong");
		//return 2;
	}

	public static CompletableFuture<Integer> create(){
		//return CompletableFuture.supplyAsync(() -> 2);
		ForkJoinPool pool = new ForkJoinPool(10);
		//Run this asyn job in the FJP not in the common pool
		return CompletableFuture.supplyAsync(() -> compute(), pool);
	}
	
	public static void printIt(int value) {
		System.out.println(value + " -- " + Thread.currentThread());
	}
	
	public static <T> T handleException(Throwable throwable) {
		System.out.println("ERROR : " + throwable);
		throw new RuntimeException("It is beyond recovery");
	}
	
	public static int handleException2(Throwable throwable) {
		System.out.println("ERROR : " + throwable);
		return -1;
	}
	
	public static CompletableFuture<Integer> create2(int number){
		return CompletableFuture.supplyAsync(() -> number);
	}
	
	public static int func1(int value) {
		return value*2;
	}
	
	public static Object[] func2(int value) {
		return new Object[] {value-1, value+1};
	}
	
	public static CompletableFuture<Integer> inc(int number){
		return CompletableFuture.supplyAsync(() -> number+1);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//System.out.println("Main thread = "+ Thread.currentThread());
		
//		CompletableFuture<Integer> future = create();
//		
//		sleep(100);
//		future.thenAccept(data -> printIt(data));
//		sleep(1000);
//		System.out.println("End!");
		
		
//		create().thenAccept(data -> System.out.println(data))
//				.thenRun(() -> System.out.println("That went well"));//Terminal Operation went well
		
//		create()
//			.thenApply(data -> data * 2.0)
//			.thenAccept(data -> System.out.println(data))
//			.thenRun(() -> System.out.println("That went well"));//Terminal Operation went well
//		sleep(2000);
		
		/*
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();
														
		future.thenApply(data -> data+1)
			   .thenApply(data -> data*2)
			   .thenAccept(data -> System.out.println(data));
		
		System.out.println("Built the pipeline");
		
		System.out.println("Prepared to print");
		
		sleep(1000);
		
		future.complete(10);
		
		sleep(1000);
		*/
		
		/*
		
		//IT WILL TAKE THE NEAREST EXCEPTION
		create().thenApply(data -> data+1)
				.exceptionally(throwable -> handleException2(throwable))
			    .thenApply(data -> data*2)
			    .thenAccept(data -> System.out.println(data))
			    .exceptionally(throwable -> handleException(throwable));
		*/
		
		/*
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();
		future.thenApply(data -> data+1)
		.exceptionally(throwable -> handleException2(throwable))
	    .thenApply(data -> data*2)
	    .thenAccept(data -> System.out.println(data))
	    .exceptionally(throwable -> handleException(throwable));
		
		sleep(1000);
		
		if(Math.random() > 0.5)
			future.completeExceptionally(new RuntimeException("Dont tell the boss"));
		else
			future.complete(5);
		
		*/
		
		/*
		//States of Completable Future
		//Pending
		//Resolved
		//Rejected
		
		CompletableFuture<Integer> future = new CompletableFuture<Integer>();
		future.thenApply(data -> data * 2)
			  .exceptionally(throwable -> handleException(throwable))
			  .thenApply(data -> data + 1)
			  .thenAccept(System.out::println);
		
		System.out.println("Built the pipeline");
		//Available in Java 9 not in Java 8
		//future.completeOnTimeout(-1, 1, TimeUnit.SECONDS);
		//future.orTimeout(2, TimeUnit.SECONDS);
		sleep(1000);
		sleep(1000);
		System.out.println("DONE");
		*/
		
		//Java is statically typed
		//combine
		create2(2).thenCombine(create2(3), (result1, result2) -> result1 + result2)
				  .thenAccept(data -> System.out.println(data));
		//Output = 5
		
		
		// ((( zip )))
		//compose
//		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
//		numbers.stream()
//		       //.map(e -> func1(e))  //func1 is one to one mapping
//		       //.map(e -> func2(e))//func2 is one to many mapping
//				.flatMap(e -> Stream.of(func2(e)))
//		       .forEach(System.out::println);
		
		
//		function return data => map
//		function return Stream => flatMap
//		function return data =>  thenApply/thenAccept
//		function return a CompletableFuture - thenCompose
		
		//map     :  Stream<T> ==> Stream<Y>  One to One
		//flatMap : Stream<T> ==> Stream<Y>  One to Many
		
		
		//create2(2)//.thenApply(data -> inc(data))
		create2(2).thenCompose(data -> inc(data))
				 .thenAccept(result -> System.out.println(result));
		
		
//		CompletableFuture<Integer> future = create();
		
//		create().thenAccept(data -> System.out.println(data))
//				.thenRun(() -> System.out.println("this always live"))
//				.thenRun(() -> System.out.println("Really, this always live"));
//		System.out.println("Here it is");
		
		//System.out.println(create().get()); // bad idea
		//the best thing is to do with get is forget because it is a blocking statement.

		//System.out.println(create().getNow(0)); // Non-blocking BUT DO NOT use it
		System.out.println("End!");
		//CompletableFuture<Void> future2 = future.thenAccept(data -> System.out.println(data));
		
		//Popular functional interface
		//Consumer<T> void accept(T);	    - forEach
		//Supplier<T> T get();				-factory
		//Predicate<T> boolean test(T);		-filter
		//Function<T, R> R apply();         -map
		
		//Stream 							CompletableFuture
		//pipeline							pipeline
		//lazy								lazy
		//0 or 1 or more					0 or 1
		//only data channel					data and error channel
		//forEach							thenAccept
		//map								thenApply  (Transforms one stream to another stream)
		//exception=oops					error channel
		
		
	
	}

}
