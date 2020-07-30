/**
 * 
 */
package features;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author ishaqkhan
 * 
 * 
 */
public class SampleExecutor {

	public static void main(String[] args) {
		/*
		ExecutorService service = Executors.newCachedThreadPool();
		try {
			CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
				System.out.println("<>" + Thread.currentThread());
				return "Running Supply Asynchronously";
			} , service);
			
			System.out.println("=> " + f.get());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		try {
			
			CompletableFuture<String> yourName = CompletableFuture.supplyAsync(new Supplier<String>() {
				@Override
				public String get() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return "Ishaq";
				}
				
			});
			
			CompletableFuture<String> cf = yourName.thenApply(new Function<String, String>() {
				@Override
				public String apply(String x) {
					return "Hello, " + x;
				}
			});
			
			try {
				System.out.println(cf.get());
			} catch (InterruptedException | ExecutionException e) {			
				e.printStackTrace();
			}
			
			CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Ishaq Khan")
															 .thenApply(data -> "Hello " + data)
															 .thenApply(data -> data + ". Welcome to Future! ");
			
			System.out.println(cf1.get());
			
			//<U> CompletableFuture<U> thenApply(Function<? super T , ? extends U> fn);
			
			Executor executor1 = Executors.newFixedThreadPool(2);
			CompletableFuture.supplyAsync(() -> {
				System.out.println("<" + Thread.currentThread());
			    return "Some result";
			}).thenApplyAsync(result -> {
			    // Executed in a thread obtained from the executor
				System.out.println(">" + Thread.currentThread());
			    return result + "Processed Result";
			}, executor1);
			
			
			
			//Combine two dependent CompletableFutures using thenCompose
			CompletableFuture<CompletableFuture<Double>> result1 = getUsersDetail(1)
																	.thenApply(actor -> getCreditRating(actor));
			
			try {
				System.out.println(result1.get().get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
			//In Completable Future Chain if you need a flattened result then use  thenCompose()
			CompletableFuture<Double> result2 = getUsersDetail(1).thenCompose(actor -> getCreditRating(actor));
			
			System.out.println("r2 -> " + result2.get());
			
			
			//Combine two Independent CompletableFutures using thenCombine
			CompletableFuture<Double> wtFuture = CompletableFuture.supplyAsync(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 73.0;
			});
			
			CompletableFuture<Double> htFuture = CompletableFuture.supplyAsync(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 173.0;
			});
			
			CompletableFuture<Double> bmi = wtFuture.thenCombine(htFuture, (wtFut, htFut) -> {
				Double htInMetres = htFut / 100;
				return wtFut / (htInMetres * htInMetres);
			});
			
			System.out.println("<-->" + bmi.get());
			
			//Combining multiple CompletableFutures together
			//allOf
			/*
			List<String> webpageLinks = Arrays.asList(new String[] 
					{"https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jar.html",
					 "https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javap.html"}); //2 or more;
			
			List<CompletableFuture<String>>  pageContentsFuture = webpageLinks.stream()
											    .map(webpageLink -> SampleExecutor::downloadWebPage)
											    .collect(Collectors.toList());
			
			CompletableFuture<Void> allFutures = CompletableFuture.allOf(
					pageContentsFuture.toArray(new CompletableFuture[pageContentsFuture.size()])
			);
			
			CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
				   return pageContentsFuture.stream()
				           .map(pageContentFuture -> pageContentFuture.join())
				           .collect(Collectors.toList());
			});
			
			CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents -> {
			    return pageContents.stream()
			            .filter(pageContent -> pageContent.contains("CompletableFuture"))
			            .count();
			});

			System.out.println("Number of Web Pages having CompletableFuture keyword - " + 
			        countFuture.get());
			
			*/
			//anyOf
			CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			    try {
			        TimeUnit.SECONDS.sleep(2);
			    } catch (InterruptedException e) {
			       throw new IllegalStateException(e);
			    }
			    return "Result of Future 1";
			});
	
			CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			    try {
			        TimeUnit.SECONDS.sleep(5);
			    } catch (InterruptedException e) {
			       throw new IllegalStateException(e);
			    }
			    return "Result of Future 2";
			});
	
			CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			    try {
			        TimeUnit.SECONDS.sleep(3);
			    } catch (InterruptedException e) {
			       throw new IllegalStateException(e);
			    }
			    return "Result of Future 3";
			});

			CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
			System.out.println(anyOfFuture.get());// Result of Future 2
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static  CompletableFuture<Actor> getUsersDetail(int actorId) {
		
		return CompletableFuture.supplyAsync(() -> {
			return getActorDetails1(actorId);
		});	
	}

	public static CompletableFuture<Double> getCreditRating(Actor actor) {
		return CompletableFuture.supplyAsync(() -> {
			return getCreditRating1(actor);
		});
	}
	
	public static Actor getActorDetails1(int actorId) {
		return new Actor("Ishaq", "Artist");
	}
	
	public static Double getCreditRating1(Actor actor) {
		return 2.0;
	}
	
	public static CompletableFuture<String> downloadWebPage(String pageLink) {
		return CompletableFuture.supplyAsync(() -> {
			return "Page Content has information";
		});
	} 
}
