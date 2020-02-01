/**
 * 
 */
package features;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author nsa
 * Chapter 5 : Issues with Streams, Lambdas, and Method References
 */
public class Chapter5 {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	//Exercise 5-1. Returning a collection and  ltering out nulls
	List<String> strings = Arrays.asList(
	"this", null, "is", "a", null, "list", "of", "strings", null);
	List<String> nonNullStrings = strings.stream()
	    .filter(Objects::nonNull)
	    .collect(Collectors.toList());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		5.1 The java.util.Objects Class
//		static boolean deepEquals(Object a, Object b)
//		static boolean equals(Object a, Object b)
//		static int hash(Object... values)
//		static String toString(Object o)
//		static String toString(Object o, String nullDefault)
//		static <T> T requireNotNull(T obj)
//		static <T> T requireNotNull(T obj, String message)
//		static <T> T requireNotNull(T obj, Supplier<String> messageSupplier)
//		static boolean isNull(Object obj)
//		static boolean nonNull(Object obj)
		
		//5.2 Lambdas and Effectively Final
		//Local variables accessed inside lambda expressions must be final or “effectively final.” 
		//Attributes can be both accessed and modified.
		
		//Exercise 5-6. Sum the values in a List
		List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9);
		int total = 0;	
		//for-each loop
		for(int n : nums) {
			total+=n;
		}
		//forEach method defined on Iterable takes a Consumer as an argument,
		total = 0;
		//nums.forEach(n -> total += n);
		// a function along with the accessible variables defined in its environment is called a closure
		//Lambdas are closure
		nums.stream().mapToInt(Integer::valueOf).sum();
		
		//5.3 Streams of Random Numbers
		// IntStream    ints()
	    //LongStream   longs()
	    //DoubleStream doubles()
		//DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound)
		
		//Exercise 5-7. Generating streams of random numbers
		Random r = new Random();
		r.ints(5)
		.sorted()
		.forEach(System.out::println);
		
		r.doubles(5, 0, 0.5)
		 .sorted()
		 .forEach(System.out::println);
		
		//Primitive to Wrapper Classes
		List<Long> longs = r.longs(5).boxed().collect(Collectors.toList());
		System.out.println(longs);
		
		List<Integer> listOfInts = r.ints(5, 10, 20).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
		System.out.println(listOfInts);
		
		//SecureRandom is a subclass of Random. 
		//provides a crypto‐ graphically strong random number generator
		
		//new default methods in the java.util.Map interface, like
		//compute					Compute a new value based on the existing key value
		//computeIfAbsent			return the value of the key if present or else use the supplied method and store
		//computeIfPresent			Compute a new value to replace existing
		//forEach					Iterate through the map
		//getOrDefault				If key exists return the value or else default
		//merge						If key is not present return the supplied value or else compute new
		//putIfAbsent				If key is not present associate it with the value
		//remove					Remove entry of the key
		//replace					replace the key value with new value
		//replaceAll				replace all based on applying given function.
		
		//V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
		
		//Fibonacci
		//The way to fix this is to use a cache, a technique known as memoization in functional programming.
		
		//V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
		
		//Exercise 5-11. Calling the countWords method
		String passage = "NSA agent walks into a bar. Bartender says, " +
			    "'Hey, I have a new joke for you.' NSA; Agent says, 'heard it'.";
		//Arrays.stream(passage.toLowerCase().replaceAll("\\W", " ").split("\\s+")).forEach(System.out::println);
		//space is also a non-word character
		Map<String, Integer> counts = countWords(passage, "NSA", "agent", "joke");
		counts.forEach((word, count) -> System.out.println(word + "=" + count));
		
		Map<String, Integer> fCounts = fullWordCounts(passage);
		fCounts.forEach((word, count) -> System.out.println(word + "=" + count));
		
		//Construct	Description
		//	.	Any character (may or may not match line terminators)
		//	\d	A digit: [0-9]
		//	\D	A non-digit: [^0-9]
		//	\s	A whitespace character: [ \t\n\x0B\f\r]
		//	\S	A non-whitespace character: [^\s]
		//	\w	A word character: [a-zA-Z_0-9]
		//	\W	A non-word character: [^\w]
		
		//Quantifiers 
		//Greedy	Reluctant	Possessive	Meaning
		//X?		X??			X?+			X, once or not at all
		//X*		X*?			X*+	X, 		zero or more times
		//X+		X+?			X++	X, 		one or more times
		//X{n}		X{n}?		X{n}+		X, exactly n times
		//X{n,}		X{n,}?		X{n,}+		X, at least n times
		//X{n,m}	X{n,m}?		X{n,m}+		X, at least n but not more than m times
		
		//Capturing groups
		//	((A)(B(C)))
		//	(A)
		//	(B(C))
		//	(C)

			
		//V replace(K key, V value) 
		//boolean replace(K key, V oldValue, V newValue)
		//The first one replaces the value of the key if it exists in the map at all. 
		//The second only does the replacement if the current value is the one specified.
		
		//The getOrDefault method solves the occasionally annoying fact that calling get on a Map with a key that doesn’t exist returns null.
		//V getOrDefault(Object key, V defaultValue)
			
		//V merge(K key, V value,BiFunction<? super V, ? super V, ? extends V> remappingFunction)
		
		//5.5 Default Method Conflict
		//Your implementation can still use the provided defaults from the interfaces through the super keyword.
		
		//three possibilities when this occurs:
		//1) In any conflict between a method in a class and a default method in an interface, the class always wins.
		//2) If the conflict comes between two interfaces where one is a descendant of the other, then the descendant wins, the same way they do in classes.
		//3) If there is no inheritance relationship between the two defaults, the class will not compile. Implement in the class itself
		
		//Check Company.java & Employees.java
		//Of course, if the same method appears in both interfaces and neither is a default, then this is the pre-Java 8 situation. There’s no conflict, but the class must provide an implementation.
		
		//5.6 Iterating Over Collections and Maps
		//default void forEach(Consumer<? super T> action)
		//referential transparency, where a function can be replaced by its corresponding value.

		//ArrayList to LinkedHashSet
		
		//Exercise 5-17. Iterating over a linear collection
		List<Integer> integers = Arrays.asList(3, 1, 4, 1, 5, 9);
		integers.forEach(new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				System.out.println(t);
				
			}
		});
		// block lambda
		integers.forEach((Integer n) -> {
			System.out.println(n);
		});
		//expression lambda
		integers.forEach(n -> System.out.println(n));
		//method reference
		integers.forEach(System.out::println);
		
		
		//default void forEach(BiConsumer<? super K, ? super V> action)
		
		//Exercise 5-18. Iterating over a Map
		Map<Long, String> map = new HashMap<>(); 
		map.put(86L, "Don Adams (Maxwell Smart)"); 
		map.put(99L, "Barbara Feldon"); 
		map.put(13L, "David Ketchum");
		map.forEach((num, agent) -> System.out.printf("Agent %d, played by %s%n", num, agent));

		//Exercise 5-20. Overloaded logging methods java.util.logging.Logger
//		void config(String msg)
//		void config(Supplier<String> msgSupplier)
//		void fine(String msg)
//		void fine(Supplier<String> msgSupplier)
//		void finer(String msg)
//		void finer(Supplier<String> msgSupplier)
//		void finest(String msg)
//		void finest(Supplier<String> msgSupplier)
//		void info(String msg)
//		void info(Supplier<String> msgSupplier)
//		void warning(String msg)
//		void warning(Supplier<String> msgSupplier)
//		void severe(String msg)
//		void severe(Supplier<String> msgSupplier)
		
		//Exercise 5-21. Implementation details of the Logger class
//		public void info(Supplier<String> msgSupplier) { 
//			log(Level.INFO, msgSupplier);
//		}
//		public void log(Level level, Supplier<String> msgSupplier) { 
//			if (!isLoggable(level)) {
//				return; 
//			}
//			LogRecord lr = new LogRecord(level, msgSupplier.get());
//			doLog(lr); 
//		}
		
		//Exercise 5-22. Using a Supplier in the info method
		
		List<String> data = new ArrayList<>();
		// ... populate list with data ...
		//logger.info("The data is " + data.toString());	//Argument always constructed
		//logger.info(() -> "The data is " + data.toString());	//Argument only constructed if log level shows info messages
		//The technique of replacing an argument with a Supplier of the same type is known as deferred execution
		
		//5.8 Closure Composition
		//Use the composition methods defined as defaults in the Function, Consumer, and Predicate interfaces.
		
		//Exercise 5-23. Composition methods in java.util.function.Function
		//default <V> Function<V,R> compose(Function<? super V,? extends T> before) 
		//default <V> Function<T,V> andThen(Function<? super R,? extends V> after)
		
		Function<Integer, Integer> add2 = x -> x + 2;
		Function<Integer, Integer> mult3 = x -> x * 3;
		//compose method applies its argument before the original function, 
		//andThen method applies its argument after the original function
		
		//first multiply(argument) & then add
		Function<Integer, Integer> mult3add2 = add2.compose(mult3);
		//first multiply & then add(argument)
		Function<Integer, Integer> add2mult3 = add2.andThen(mult3);
		
		
		System.out.println("mult3add2(1): " + mult3add2.apply(1));
		System.out.println("add2mult3(1): " + add2mult3.apply(1));
		
		//Exercise 5-25. Parse an integer from a string, then add 2
		Function<String, Integer> parseThenAdd2 = add2.compose(Integer::parseInt);
		System.out.println(parseThenAdd2.apply("1"));
		
		//Exercise 5-26. Add a number, then convert to a string
		Function<Integer, String> plus2ToString = add2.andThen(Object::toString);
		System.out.println(plus2ToString.apply(1));
		
		//Exercise 5-27. Closure composition with consumers
		//default Consumer<T> andThen(Consumer<? super T> after)
		
		//Exercise 5-28. Composed consumer for printing and logging
		Logger log = Logger.getLogger("...");
		Consumer<String> printer = System.out::println;
		Consumer<String> logged = log::info;
		
		Consumer<String> printThenLog = printer.andThen(logged);
		Stream.of("this", "is", "a", "stream", "of", "strings").forEach(printThenLog);
		//First sysout
		//Then Logger
		
		//Exercise 5-29. Composition methods in the Predicate interface
		//default Predicate<T> default Predicate<T> default Predicate<T>
		//and(Predicate<? super T> other) negate()
		//or(Predicate<? super T> other)
		
		IntPredicate triangular = CompositionDemo::isTriangular;
		IntPredicate perfect = CompositionDemo::isPerfect;
		IntPredicate both = triangular.and(perfect);
		
		IntStream.rangeClosed(1, 10_000)
		.filter(triangular)
		.forEach(System.out::print);
		//Exercises: 1, 3, 6, 10, 15, 21, 28, 36, 45, ... 
		
		IntStream.rangeClosed(1, 10_000)
		.filter(perfect)
		.forEach(System.out::print);
		//Exercises: 1, 4, 9, 16, 25, 36, 49, 64, 81, ... 
		
		IntStream.rangeClosed(1, 10_000)
				.filter(both)
				.forEach(System.out::println);
		//Both (between 1 and 10,000): 1, 36, 1225
		
		//5.9 Using an Extracted Method for Exception Handling
		//lambda is implementation of single abstract method in a functional interface.
		
		//		Exercise 5-32. Client code
		List<Integer> values = Arrays.asList(30, 10, 40, 10, 50, 90); 
		List<Integer> scaled = div(values, 10); 
		System.out.println(scaled);
		// prints: [3, 1, 4, 1, 5, 9]
		scaled = div(values, 0); System.out.println(scaled);
		// throws ArithmeticException: / by zero
		//Java 8 also adds a new class called java.io.UncheckedIOException just to avoid some of the issues discussed in this recipe
		//divisor are changed to Double instead of Integer, you don’t get an exception at all
		
		//5.10 Checked Exceptions and Lambdas
		
		
		//5.11 Using a Generic Exception Wrapper
		//Create special exception classes and add a generic method to accept them and return lambdas without exceptions.
		
	}
	
	public static List<String> encodeValuesWithWrapper(String... values) {
		return Arrays.stream(values)
				.map(wrapper(s -> URLEncoder.encode(s, "UTF-8")))
				.collect(Collectors.toList());
	}
	//Using the wrapper method
	//a separate generic wrapper, like ConsumerWithException, SupplierWithException, and so on, is needed for each functional interface you plan to use.
	
 	//Exercise 5-40. A wrapper method to deal with exceptions
	public static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
		return arg -> { 
			try {
				return fe.apply(arg); 
			} catch (Exception e) {
					throw new RuntimeException(e); 
			}
		}; 
	}

	public static List<Integer> div(List<Integer> values, Integer factor){
		
		return values.stream().map(n -> n/factor).collect(Collectors.toList());
		//Can throw an ArithmeticException
	}
	
	public static List<Integer> divException(List<Integer> values, Integer factor){
		
		return values.stream()
		.map( n -> {
				try{
					return n/factor;
				}catch(Exception e) {
					e.printStackTrace();
					return 0;
				}
			}).collect(Collectors.toList());
	}
	
	//Exercise 5-34. Extracting a lambda into a method
	public static Integer divide(Integer value, Integer factor) { 
		try {
			return value / factor;
		} catch (ArithmeticException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	public static List<Integer> divideUsingMethod(List<Integer> values, Integer factor){
	
		return values.stream().map(n -> divide(n,factor)).collect(Collectors.toList());
	}
	
	//Exercise 5-35. URL encoding a collection of strings (NOTE: DOES NOT COMPILE)
	public List<String> encodeValuesLambda(String... values) { 
		return Arrays.stream(values)
		 .map(s -> {
			try {
				return (URLEncoder.encode(s, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
		})
		.collect(Collectors.toList());
	}
	
	//Exercise 5-36. Declaring the exception (ALSO DOES NOT COMPILE)
	public List<String> encodeValuesLambdaThrows(String... values) throws UnsupportedEncodingException {
	 return Arrays.stream(values)
			 .map(s -> {
				try {
					return URLEncoder.encode(s, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "";
				}
			})
			 .collect(Collectors.toList());
	}
	
	//Exercise 5-37. URL encoding using try/catch (CORRECT)
	public List<String> encodeValuesAnonInnerClass(String... values){
		return Arrays.stream(values)
				.map(new Function<String, String>() {
					
					@Override 
					public String apply(String s) {
						try {
							return URLEncoder.encode(s, "UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
							return "";
						}
					}
				}).collect(Collectors.toList());
	}
	
	//Exercise 5-38. URL encoding delegating to a method
	private static String encodeString(String s) { 
		try {
			return URLEncoder.encode(s, "UTF-8"); 
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e); 
		}
	}
	//Extracted method for exception handling
	//Method reference to the extracted method
	public static List<String> encodeValuesUsingMethod(String... values) { 
		return Arrays.stream(values)	
			 .map(Chapter5::encodeString) 
			 .collect(Collectors.toList());
	}
	
	//Exercise 5-2. Testing the  later
	@Test
	public void testNonNulls() throws Exception { List<String> strings =
	        Arrays.asList("this", "is", "a", "list", "of", "strings");
		assertTrue(Objects.deepEquals(strings, nonNullStrings));
	}
	
	//Generalized
	//Exercise 5-3 filters nulls out of any list.
	public <T> List<T> getNonNullElements(List<T> list){
		return list.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	//Exercise 5-8. Recursive calculation of Fibonacci numbers
	long fib(long i) {
		if (i == 0) return 0;
		if (i == 1) return 1;
		return fib(i - 1) + fib(i - 2);
	}
	
	//Exercise 5-9. Fibonacci calculation with a cache
	private Map<Long, BigInteger> cache = new HashMap<>();
	public BigInteger fib2(long i) {
		if (i == 0) return BigInteger.ZERO; 
		if (i == 1) return BigInteger.ONE;
		return cache.computeIfAbsent(i, n -> fib2(n - 2).add(fib2(n - 1))); 
	}
	//Cache returns value if it exists, or computes and stores it if not
	
	//Exercise 5-10. Update the word counts only for specific words
	/*********************************************************
	 * Amazon Online Interview Question (Top N Buzzwords) 2019
	 * @param passage
	 * @param strings
	 * @return
	 * 
	 * Put the words we care about in the map with a count of zero
	 * Read the passage, updating the counts only for the words we care about
	 ********************************************************/
	public static Map<String, Integer> countWords(String passage, String... strings){ 
		Map<String, Integer> wordCounts = new HashMap<>();
		
		// initialize each word count with zero
		Arrays.stream(strings).forEach(s -> wordCounts.put(s, 0));
		
		Arrays.stream(passage.toLowerCase().replaceAll("\\W", " ").split("\\s+")).forEach(word -> wordCounts.computeIfPresent(word, (key,val) -> val+1 ));
		
		return wordCounts;
	
	}
	
	
	//Exercise 5-12. Using the merge method
	public static Map<String, Integer> fullWordCounts(String passage) { 
		Map<String, Integer> wordCounts = new HashMap<>();
		String testString = passage.toLowerCase().replaceAll("\\W"," ");
	    Arrays.stream(testString.split("\\s+")).forEach(word ->
	        wordCounts.merge(word, 1, Integer::sum));
	    return wordCounts; 
	}
	
}
