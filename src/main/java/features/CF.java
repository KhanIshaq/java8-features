package features;

import java.util.concurrent.CompletableFuture;

public class CF {

	public static void main(String[] args) {
		
		//Exercise 9-23
		CompletableFuture.supplyAsync(() -> {return "42";})
		 .thenApply(Integer::parseInt)
		 .thenApply(n -> n*2)
		 .thenAccept(System.out::println)
		 .join();
		System.out.println("CF is Running ...");

	}

}
