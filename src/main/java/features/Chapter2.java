/**
 * 
 */
package features;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * @author ishaqkhan
 *
 *The interfaces in java.util.function fall into four categories: (1) consumers, (2) suppliers, (3) predicates, and (4) functions. 
 *Consumers take a generic argument and return nothing. 
 *Suppliers take no arguments and return a value. 
 *Predicates take an argument and return a boolean. 
 *Functions take a single argument and return a value.
 *
 */
public class Chapter2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//2.1 Consumers
		//Exercise 2-1. Methods in java.util.function.Consumer
		//void accept(T t)
		//default Consumer<T> andThen(Consumer<? super T> after)
	
		//Exercise 2-2.  e forEach method in Iterable
		//default void forEach(Consumer<? super T> action)
		List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
		
		strings.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
		
		strings.forEach(t -> System.out.println("1. "+ t));
		strings.forEach(System.out::println);
		
		//IntConsumer
		//DoubleConsumer
		//LongConsumer
		//BiConsumer
		
		//Optional.ifPresent(Consumer<? super T> consumer)
		//Stream.forEach(Consumer<? super T> action)
		//Stream.forEachOrdered(Consumer<? super T> action)
		//Stream.peek(Consumer<? super T> action)
		
		//andThen method in Consumer is used for composition
		
		//2.2 Suppliers
		//java.util.function.Supplier does not have any default or static methods
		
		//Exercise 2-4. Using Math.random() as a Supplier
		Logger logger = Logger.getLogger("...");
		System.out.println("....");
		//Anonymous inner class implementation
		DoubleSupplier supplier = new DoubleSupplier() {
			
			@Override
			public double getAsDouble() {
				
				return Math.random();
			}
		};
		System.out.println("1. " + supplier.getAsDouble());
		
		//Lambda
		supplier = () -> Math.random();
		System.out.println("2. " + supplier.getAsDouble());
		
		//Method reference
		supplier = Math::random;
		logger.info(supplier.getAsDouble()+"");
		System.out.println("3. " + supplier.getAsDouble());
		
		System.out.println("....");
		
		//Interface				Single abstract method
		//IntSupplier			int getAsInt() 
		//DoubleSupplier		doublegetAsDouble()
		//LongSupplier		l	ong getAsLong()
		//BooleanSupplier		boolean getAsBoolean()
		
		//example from the standard library is the orElseGet method in Optional, which also takes a Supplier
		//Optional is a nonnull object that either wraps a value or is empty
		
		List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
			    "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
		
		Optional<String> first = names.stream()
				.filter(name -> name.startsWith("C")).findFirst();
	
				
		System.out.println(first);
		System.out.println(first.orElse("None"));
		
		System.out.println(first.orElse(String.format("No result found in %s", 
				names.stream().collect(Collectors.joining(", ")))));
		
		// orElseGet will get invoked only if List is empty
		System.out.println(first.orElseGet(() -> String.format("No result found in %s", 
				names.stream().collect(Collectors.joining(", ")))));
	
		//Prints Optional.empty
		//Prints the string "None"
		//Forms the comma-separated collection, even when name is found 
		//Forms the comma-separated collection only if the Optional is empty
		
		//orElseThrow
		//Objects.requireNonNull(T obj, Supplier<String> messageSupplier)
		//CompletableFuture.supplyAsync(Supplier<U> supplier)
		//Logger is overloaded
		
		//2.3 Predicate
		//You want to filter data using the java.util.function.Predicate interface.
		
		//Exercise 2-6. Methods in java.util.function.Predicate
		//default Predicate<T> and(Predicate<? super T> other) 
		//static <T> Predicate<T> isEquals(Object targetRef)
		//default Predicate<T> negate()
		//default Predicate<T> or(Predicate<? super T> other)
		//boolean	test(T t) // SAM
		
		ImplementPredicate ip = new ImplementPredicate();
		//Exercise 2-7. Finding strings of a given length
		System.out.println("Len = " + ip.getNamesOfLength(5, "Ishaq", "Mohammed", "Younus", "Irfan", "Issaa", "Kamran", "Yusuf"));
		
		String[] namesArr = new String[] {"Ishaq", "Mohammed", "Younus", "Irfan", "Issaa", "Kamran", "Yusuf"};
		System.out.println("Len = " + ip.getNamesOfLength(5, namesArr));
		
		//Exercise 2-8. Finding strings that start with a given string
		System.out.println("Starting = " + ip.getNamesStartingWith("Y", "Ishaq", "Mohammed", "Younus", "Irfan", "Eesa", "Kamran", "Yusuf"));
		
		//Varargs
		//an array or as a sequence of arguments.
		//Varargs can be used only in the final argument position
		//when should you use varargs? As a client, you should take advantage of them whenever the API offers them. Important uses in core APIs include reflection, message formatting, and the new printf facility. 
		Object[] arguments = {
			    new Integer(7),
			    new Date(),
			    "a disturbance in the Force"
			};

		//format is static method of MessageFormat
		String result = MessageFormat.format(
		    "At {1,time} on {1,date}, there was {2} on planet "
		     + "{0,number,integer}.", arguments);
		System.out.println(result);
			
		String result1 = MessageFormat.format(
			    "At {1,time} on {1,date}, there was {2} on planet "
			    + "{0,number,integer}.",
			    7, new Date(), "a disturbance in the Force");
		System.out.println(result1);
		
		//Test.main(new String[] {"Predicate"});
		
		
		//Exercise 2-9. Finding strings that satisfy an arbitrary predicate
		//Exercise 2-10. Adding constants for common cases
		//ImplementPredicate
		
		//supplying a predicate as an argument is that you can also use the default methods and, or, and negate to create a composite predicate from a series of individual elements.
		//Exercise 2-11. JUnit test for predicate methods
		
		
		//Optional.filter(Predicate<? super T> predicate)
		//If a value is present, and the value matches the given predicate, returns an Optional describing the value, otherwise returns an empty Optional.
		//Collection.removeIf(Predicate<? super E> filter)
		//Removes all elements of this collection that satisfy the predicate.
		//Stream.allMatch(Predicate<? super T> predicate)
		//Returns true if all elements of the stream satisfy the given predicate. The methods anyMatch and noneMatch work similarly.
		//Collectors.partitioningBy(Predicate<? super T> predicate)
		//Returns a Collector that splits a stream into two categories: those that satisfy the predicate and those that do not.
		
		//2.4 Functions
		//Functions
		// lambda expression that implements the R apply(T t) method.
		//Function contains the single abstract method apply, which is invoked to transform a generic input parameter of type T into a generic output value of type R
		
		//Exercise 2-12. Methods in the java.util.function.Function interface
		
		//default <V> Function<T,V> andThen(Function<? super R,? extends V> after) 
		//R apply(T t)
		//default <V> Function<V,R> compose(Function<? super V,? extends T> before)
		//static <T> Function<T,T> identity()
		
		//Exercise 2-13. Mapping strings to their lengths
		List<String> names3 = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
		        "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
		
		// Anonymous inner class
		List<Integer> nameLengths = names.stream()
				.map(new Function<String, Integer>() {

					@Override
					public Integer apply(String t) {
						return t.length();
					}
					
		}).collect(Collectors.toList());
		System.out.println("13. " + nameLengths);
		
		//lambda expression
		nameLengths = names.stream().map(t -> t.length()).collect(Collectors.toList());
		System.out.println("14. " + nameLengths);
		
		//Method reference
		nameLengths = names.stream().map(String::length).collect(Collectors.toList());
		System.out.println("15. " + nameLengths);
		System.out.printf("nameLengths = %s%n", nameLengths);
		
		//selects the specified argument in the argument list.
		System.out.println(String.format("%2$s", 32, "Hello"));
		System.out.println(String.format("%1$s", 32, "Hello"));
		
		//Table 2-3. Additional Function interfaces
		//Interface					Single abstract method
//		IntFunction					R apply(int value)
//		DoubleFunction				R apply(double value)
//		LongFunction				R apply(long value)
//		ToIntFunction				int applyAsInt(T value)
//		ToDoubleFunction			double applyAsDouble(T value)
//		ToLongFunction				long applyAsLong(T value) 
//		DoubleToIntFunction			int applyAsInt(doublevalue) 
//		DoubleToLongFunction		long applyAsLong(double value) 
//		IntToDoubleFunction			double applyAsDouble(intvalue) 
//		IntToLongFunction			long applyAsLong(int value) 
//		LongToDoubleFunction		double applyAsDouble(long value) 
//		LongToIntFunction			int applyAsInt(long value)
//		BiFunction					void accept(T t, U u)
		
		//Map Function			Return type
		//Stream.mapToInt		Int Stream
		//Stream.mapToDouble	DoubleStream
		//Stream.mapToLong		LongStream
		
		//UnaryOperator
		//An example of a binary operator would be Math.max
		
		//IntUnaryOperator
		//DoubleUnaryOperator
		//LongUnaryOperator
		//input and output arguments are int, double, and long, respectively. 
		
		//BinaryOperator
		//IntBinaryOperator
		//DoubleBinaryOperator
		//LongBinaryOperator
		
		//Table 2-4. Additional BiFunction interfaces
		//Interface 				Single abstract method
		//ToIntBiFunction 			int applyAsInt(T t, U u) 
		//ToDoubleBiFunction 		double applyAsDouble(T t, U u) 
		//ToLongBiFunction 			long applyAsLong(T t, U u)
		
		//Map.computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) 
		//If the specified key does not have a value, use the provided Function to compute one and add it to a Map.
		
		//Comparator.comparing(Function<? super T,? extends U> keyExtractor) 
		//Discussed in Recipe 4.1, this method generates a Comparator that sorts a collection by the key generated from the given Function.
		//Comparator.thenComparing(Function<? super T,? extends U> keyExtractor)
		//An instance method, also used in sorting, that adds an additional sorting mechanism if the collection has equal values by the first sort.
		
	}

}
