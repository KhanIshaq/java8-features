/**
 * 
 */
package client;

import supplier.NamesSupplier;
import java.util.stream.Stream;

/**
 * @author ishaqkhan
 * 
 */
public class Main {

	public static void main(String[] args) {
		NamesSupplier supplier = new NamesSupplier();
		
		try(Stream<String> lines = supplier.get()){
			lines.forEach(line -> System.out.printf("Hello, %s ! %n", line));
		}
	}
}
