/**
 * 
 */
package features;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import cf.GamePageParser;
import cf.GamePageLinksSupplier;

/**
 * @author ishaqkhan
 * CHAPTER 9 
 * Parallelism and Concurrency
 */
public class Chapter9 {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		@SuppressWarnings("unchecked")
		Map m = Collections.synchronizedMap(new HashMap());
		//especially the java.util.concurrent package added in Java 5
		//Concurrency is when multiple tasks can run in overlapping time periods
		// Parallelism is when multiple tasks run at literally the same time
		
		//A concurrent application is composed of independently executing processes. 
		
		//wait, notify, and notifyAll in Object, as well as the synchronized keyword
		
		//concurrency at a higher level of abstraction using classes like 
		//ExecutorService, ReentrantLock, and BlockingQueue.
		
		//9.1 Converting from Sequential to Parallel Streams
		//Use Case
		//You want to make a stream either sequential or parallel, regardless of the default.
		//Use the stream or parallelStream methods on Collection
		//Use the sequential or parallel methods on Stream.
		
		//By default the result of a stream is sequential
		//Stream
		
		//Example 9-5. Switching from parallel to sequential (NOT WHAT YOU MIGHT EXPECT)
		List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9);
		List<Integer> nums = numbers.parallelStream()
				.map(n -> n*2)
				.peek(n -> System.out.printf("%s processing %d%n", Thread.currentThread().getName(), n))
				.sequential()
				.sorted()
				.collect(Collectors.toList());
				
		//doubling function is stateless and associative, there’s no reason not to do it parallel.
		//Sorting, however, is inherently sequential.6
		//Remember that with streams, no processing is done until the terminal expression is reached,
		
		//9.2 When Parallel Helps
		//Use Case
		//You want to see a benefit from using parallel streams.
		
		//By default, Java 8 parallel streams use a common fork-join pool to distribute the work.
		
		// To Apply parallelStream For the additional overhead to be worthwhile, you need:
		// A large amount of data, or
		// A time-consuming process for each element, and
		// A source of data that is easy to divide, and
		// Operations that are stateless and associative
		
		System.out.println("<" + Runtime.getRuntime().availableProcessors() + ">");
		
		//Technically the pool size is processors minus one, but the main thread is still used as well.
		
		//**************************************************************************
		//N is the number of data elements and Q is the amount of computational time required for each element
		// N*Q Time is required
		//Finally, doing anything stateful or where order matters is clearly going to cause problems when going parallel.
		//**************************************************************************
		
		Instant before = Instant.now();
		int total = IntStream.of(3,1,4,1,5,9)
				.map(Chapter9::doubleIt)
				.sum();
		
		Instant after = Instant.now();
		Duration duration = Duration.between(before,after);
		System.out.println("Total of doubles = " + total);
		System.out.println("time = " + duration.toMillis() + " ms");
		
		//Example 9-7. Using a parallel stream are Faster
		before = Instant.now();
		total = IntStream.of(3, 1, 4, 1, 5, 9)
		    .parallel()
		    .map(Chapter9::doubleIt)
		    .sum();
		after = Instant.now();
		duration = Duration.between(before,after);
		//System.out.println("Total of doubles = " + total);
		System.out.println("time = " + duration.toMillis() + " ms");
		
		
		//Performance Measurement depends on caching, JVM startup times,
		// micro-benchmarking framework JMH (Java Micro-benchmark Harness, available at http://openjdk.java.net/projects/code-tools/jmh/).
		//JMH lets you use annotations to specify the 
		//timing mode,
		//scope,
		//JVM arguments, and more. 
		
		//9.3 Changing the Pool Size
		//control the construction of the common pool using three system properties:
		//• java.util.concurrent.ForkJoinPool.common.parallelism
		//• java.util.concurrent.ForkJoinPool.common.threadFactory
		//• java.util.concurrent.ForkJoinPool.common.exceptionHandler
		
		//Exercise 9-12. Specifying the common pool size programmatically
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "3");
		long total1 = LongStream.rangeClosed(1, 1_000_000)
				.parallel()
				.sum();
		System.out.println(total1);
		int poolSize = ForkJoinPool.commonPool().getPoolSize(); 
		System.out.println("Pool size: " + poolSize);
		//500000500000
		//Pool size: 3
		//setting the pool size to a number greater than the number of available cores is not likely to improve performance.

		//Exercise 9-13. Setting the common pool size using a system parameter
		//$ java -cp build/classes/main                                 \
	    //-Djava.util.concurrent.ForkJoinPool.common.parallelism=10 \
	    //concurrency.CommonPoolSize
	    //Pool size: 10
		//The programmatic setting will override the settings of command-line
	
		//Example 9-14. Creating your own ForkJoinPool
		ForkJoinPool pool = new ForkJoinPool(4); 
		ForkJoinTask<Long> task = pool.submit(
		    () -> LongStream.rangeClosed(1, 1_000_000)
		                    .parallel()
		                    .sum());
		
		try {
			total1 = task.get();
		} catch (InterruptedException | ExecutionException e) { 
			e.printStackTrace();
		}finally { 
			pool.shutdown();
		}
		poolSize = pool.getPoolSize();
		System.out.println("Total: " + total1);
		System.out.println("Pool size:: " + poolSize);

	
		//9.4 The Future Interface
		//Result from asynchronous operation, check if complete , cancel if required, get the results
		
		//Exercise 9-15. Submitting a Callable and returning the Future
		ExecutorService service = Executors.newCachedThreadPool();
		Future<String> future = service.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(100);
				return "hello ishaq!";
			}
			
		});
		System.out.println("Processing...");
		getIfNotCancelled(future);
		
		future = service.submit(() -> {
			Thread.sleep(10);
			return "Hello IshaqKhan";
		});
		System.out.println("More processing..");
		//isDone is invoked in while loop because to poll future when it is complete
		while(!future.isDone()) {
			System.out.println("Busy Waiting"); // Prints millions of times
		}
		getIfNotCancelled(future);
		
		future = service.submit(() -> {
			Thread.sleep(100);
			return "Welcome IshaqKhan";
		});
		
		future.cancel(true);
		System.out.println("Processing ...");
		getIfNotCancelled(future);
		
		//9.5 Completing a CompletableFuture
		//It has the below methods
		//1) completedFuture
		//2) complete
		//3) completeExceptionally methods.
		
		//CompletableFuture class implements the Future interface and also CompletionStage
		//benefit of CompletableFuture is that it allows you to coordinate activities without writing nested callbacks
		
		/****CompletableFuture.*****/ 
		//There are three methods relevant here:
		//boolean complete(T value) 
		//static <U> CompletableFuture<U> completedFuture(U value)
		//boolean completeExceptionally(Throwable ex)
		
		//supplyAsync, one of the other static factory methods available in CompletableFuture. 
		//The complete list is given by:
		//static CompletableFuture<Void> runAsync(Runnable runnable) 
		//static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
		//static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) 
		//static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)
		
		//runAsync methods are useful if you don’t need to return anything.
		// The supply Async methods return an object using the given Supplier
		
		try {
			getProduct(666).get();
		} catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getCause().getClass());
		}
		//9.6 Coordinating CompletableFutures, Part 1
		//Use Case :
		//You want the completion of one Future to trigger another action.
		//Solution
		//Use various instance methods in CompletableFuture that coordinate actions, like thenApply, thenCompose, thenRun, and more.
		
		
		//consider the following process:
		//• Ask a Supplier for a string holding a number
		//• Parse the number into an integer
		//• Double the number
		//• Print it
		//Exercise 9-23
		CompletableFuture.supplyAsync(() -> sleepThenReturnString())
		 .thenApply(Integer::parseInt)
		 .thenApply(n -> n*2)
		 //.thenAccept(System.out::println)
		 .thenAccept(n -> {Thread t = Thread.currentThread(); System.out.println(n + " executed using " + t.getName());});
		 //.join();
		System.out.println("ForkJoinPool CF is Running ...");
		
		//9-1: Coordinating methods of Completable Future
//		Modifier(s)			ReturnType							MethodName		Arguments
//		-----------			----------							----------		----------
//							CompletableFuture<Void>				acceptEither	CompletionStage<? extends T> other, Consumer<? super T> action
//		static				CompletableFuture<Void>				allOf			CompletableFuture<?>... cfs
//		static				CompletableFuture<Object>			anyOf			CompletableFuture<?>... cfs
//		<U>					CompletableFuture<U>				applyToEither	CompletionStage<? extends T> other, Function<? super T, U> fn
//							CompletableFuture<Void>				runAfterBoth	CompletionStage<?> other, Runnable action
//							CompletableFuture<Void>				runAfterEither	CompletionStage<?> other, Runnable action
//							CompletableFuture<Void>				thenAccept		Consumer<? super T> action
//		<U>					CompletableFuture<U>				thenApply		Function<? super T> action,<? extends U> fn
//		<U,V>				CompletableFuture<V>				thenCombine		CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn
//		<U>					CompletableFuture<U>				thenCompose		Function<? super T, ? extends CompletionStage<U>> fn
//							CompletableFuture<Void>				thenRun			Runnable action
//							CompletableFuture<T>				whenComplete	BiConsumer<? super T, ? super Throwable> action
		
		
//		runAsync and supplyAsync are factory methods
//		CompletableFuture<Void> thenAccept(Consumer<? super T>  action)
//		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action)
//		CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor)
		
		

//		Exercise 9-24. Running CompletableFuture tasks on a separate thread pool
		ExecutorService executor = Executors.newFixedThreadPool(4);
		CompletableFuture.supplyAsync(() -> sleepThenReturnString(), executor)
						 .thenApply(Integer::parseInt)
						 .thenApply(n -> n*2)
						 .thenAccept(n -> {Thread t = Thread.currentThread(); System.out.println(n + " executed using " + t.getName());})
						 .join();
		System.out.println("Executor Pool Running ...");
		

		//ForkJoinPool.commonPool().awaitQuiesence(long timeout, TimeUnit unit);
		//awaitQuiescence method tells the system to wait until all the worker threads are idle, or until the given time period elapses, whichever comes first
		
		//get() and join() methods are blocking until the Future is completed
		// get() throws checked ExecutionException
		// join() throw unchecked CompletionException
		
		//boolean cancel(boolean mayInterruptIfRunning)
		// uses CancellationException & other chained operations will get completed using CompletionException
		//9-25
		//9-26
		
		//<U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn)
		//<U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor)
		
		//handle - Returns Result of the Future if it completes normally or throws an exception 
		
		//9.7 Coordinating CompletableFutures, Part 2
		//Use Case
		//You want to see a larger example of coordinating CompletableFuture instances.
		
		//Solution
		//Access set of webpages for each data in baseball season
		//Contains links to games played
		//Download the score box information for each game & transform to Java Class
		//Asynchronously save the data
		//compute the result for each
		//find the highest total score
		//print the max score & game
		
		
		
		// http://gd2.mlb.com/components/game/mlb/year_2017/month_05/day_05/
		//GamePageLinksSupplier.getGamePageLinks(LocalDate.now());
		
		//The dateUntil() method added Java 9 to LocalDate produces a Stream<LocalDate>
		
		GamePageParser parser = new GamePageParser(); 
		parser.printGames(LocalDate.of(2017, Month.MAY, 5), 3);
		//TimeoutException
		
	}
	
	//Exercise 9-23. Coordinating tasks using Completable Future
	public static String sleepThenReturnString() { 
		try {
			Thread.sleep(1000);
		}catch (InterruptedException e) {
			
		}
		return "42";
	}
	
	//Exercise 9-22. Using supplyAsync to retrieve a product
	
	public CompletableFuture<Product> getProductAsync(int id) {
		try {
			Product product = getLocal(id);
			if(product!=null) {
				logger.info("getLocal with id=" + id);
				return CompletableFuture.completedFuture(product);
			}else {
				logger.info("getRemote with id=" + id);
				
				//same operation as before, but returns the product asynchronously
				return CompletableFuture.supplyAsync(() -> {
					Product p = getRemote(id);
					cache.put(id, p);
					return p;
				});
				
			}
		} catch (Exception e) {
			logger.info("Exception thrown");
			CompletableFuture<Product> future = new CompletableFuture<>(); 
			future.completeExceptionally(e);
			return future;

		}
	}
	
	//Exercise 9-6. Adding integers in a sequential stream
	public static int doubleIt(int n) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
		}
		return n*2;
	}
	
	
	//Exercise 9-16. Retrieving a value from a Future
	public static void getIfNotCancelled(Future<String> future) {
		try {
			if(!future.isCancelled()) {
				System.out.println(future.get()); // get is a blocking call
			}else {
				System.out.println("Cancelled");
			}
				
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	//Exercise 9-19. Retrieving a product
	private static Map<Integer, Product> cache = new HashMap<>();
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public static Product getLocal(int id) {
		return cache.get(id);
	}

	public static Product getRemote(int id) {
		try {
			Thread.sleep(100);
			if (id == 666) {
				throw new RuntimeException("Evil request");
			}
		} catch (InterruptedException ignored) {
		}
		return new Product(id, "New");
	}
	
	//Exercise 9-20. Completing a CompletableFuture
	public static CompletableFuture<Product> getProduct(int id){
		try {
			Product p = getLocal(id);
			if(p != null) {
				return CompletableFuture.completedFuture(p); 
			}else {
				CompletableFuture<Product> future = new CompletableFuture();
				Product p1 = getRemote(id); 
				cache.put(id, p1);
				future.complete(p1); //Complete after legacy retrieval (for async, see next example)
				return future;
			}
		} catch (Exception e) {
			CompletableFuture<Product> future = new CompletableFuture();
			future.completeExceptionally(e);
			return future;
		}
		
	}
	
	
}
