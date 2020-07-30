/**
 * 
 */
package features;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author ishaqkhan
 *
 */
public class Chapter3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//A stream is a sequence of elements that does not save the elements or modify the origi‐ nal source
	
		//Generating a stream from some source of data, passing the elements through a series of intermediate operations (called a pipeline), and completing the process with a terminal expression.
		//Streams are also lazy. A stream will only process as much data as is necessary to reach the terminal condition.
		
		
		// static factory methods in the Stream interface, or stream methods on Iterable or Arrays.
		//static methods Stream.of, Stream.iterate, and Stream.generate
		
		//static <T> Stream<T> of(T... values)
		//Exercise 3-1. Reference implementation of Stream.of
		//@SafeVarargs
		//public static<T> Stream<T> of(T... values) { 
		//	return Arrays.stream(values);
		//}
		
		//Terminal Expression
		// collect or forEach
		
		
		//Exercise 3-2. Creating a stream using Stream.of
		String names = Stream.of("Gomez", "Morticia", "Wednesday", "Pugsley")
			    .collect(Collectors.joining(","));
		System.out.println(names);
		
		//Exercise 3-3. Creating a stream using Arrays.stream
		String[] munsters = { "Herman", "Lily", "Eddie", "Marilyn", "Grandpa" };
		names = Arrays.stream(munsters)
		.collect(Collectors.joining(",")); 
		System.out.println(names);
		
		//Another static factory method in the Stream interface is iterate. The signature of the iterate method is:
		//static <T> Stream<T> iterate(T seed, UnaryOperator<T> f)
		
		//Exercise 3-4. Creating a stream using Stream.iterate
		//produce the next value of the stream from the current value,
		
		List<BigDecimal> nums = Stream.iterate(BigDecimal.ONE, n->n.add(BigDecimal.ONE))
				.limit(10)
				.collect(Collectors.toList());
		System.out.println(nums);
		
		
		Stream.iterate(LocalDate.now(), ld->ld.plusDays(1L))
		.limit(10)
		.forEach(System.out::println);
		
		//The other factory method in the Stream class is generate, whose signature is: 
		//static <T> Stream<T> generate(Supplier<T> s)
		//This method produces a sequential, unordered stream by repeatedly invoking the Supplier
		
		//Exercise 3-5. Creating a stream of random doubles
		Stream.generate(Math::random)
		.limit(10)
		.forEach(System.out::println);
		System.out.println("--");
		
		
		//Exercise 3-6. Creating a stream from a collection
		List<String> bradyBunch = Arrays.asList("Greg", "Marcia", "Peter", "Jan",
			    "Bobby", "Cindy");
		names = bradyBunch.stream()
		    .collect(Collectors.joining(","));
		System.out.println(names);
		
		//Child Interfaces of Streams
//		static IntStream range(int startInclusive, int endExclusive)
//		static IntStream rangeClosed(int startInclusive, int endInclusive) 
//		static LongStream range(long startInclusive, long endExclusive) 
//		static LongStream rangeClosed(long startInclusive, long endInclusive)
				
		//Exercise 3-7.  e range and rangeClosed methods
		List<Integer> ints = IntStream.range(10,15)
				.boxed().collect(Collectors.toList());
		System.out.println(ints);
		
		List<Long> longs = LongStream.rangeClosed(10L,15L).
				boxed().collect(Collectors.toList());
		System.out.println(longs);
		// summarize, here are the methods to create streams:
//		• Stream.of(T... values) and Stream.of(T t)
//		• Arrays.stream(T[] array), with overloads for int[], double[], and long[] • Stream.iterate(T seed, UnaryOperator<T> f)
//		• Stream.generate(Supplier<T> s)
//		• Collection.stream()
//		• UsingrangeandrangeClosed:
//		— IntStream.range(int startInclusive, int endExclusive)
//		— IntStream.rangeClosed(int startInclusive, int endInclusive) — LongStream.range(long startInclusive, long endExclusive)
//		— LongStream.rangeClosed(long startInclusive, long endInclusive)
	
		
		//Exercise 3-8. Converting a stream of strings to a list
		List<String> strings = Stream.of("this", "is", "a", "list", "of", "strings")
		    .collect(Collectors.toList());
		
		//Exercise 3-9. Converting a stream of int to a list of Integer (DOES NOT COMPILE)
		//IntStream.of(3, 1, 4, 1, 5, 9).collect(Collectors.toList()); // does not compile
		
		//Exercise 3-10. Using the boxed method
		List<Integer> ints1 = IntStream.of(3, 1, 4, 1, 5, 9)
		    .boxed()
		.collect(Collectors.toList());
		
		List<Integer> ints2 = IntStream.of(3, 1, 4, 1, 5, 9)
				.mapToObj(Integer::valueOf)
				.collect(Collectors.toList());
		
		//mapToInt
		//mapToDouble
		//mapToLong
		
		//Another alternative is to use the three-argument version of collect, whose signature is:
	    //<R> R collect(Supplier<R> supplier,
        //        ObjIntConsumer<R> accumulator,
        //        BiConsumer<R,R> combiner)
		
		
		//Exercise 3-12. Using the three-argument version of collect
		//mutable reduction
		List<Integer> ints3 = IntStream.of(3, 1, 4, 1, 5, 9)
				.collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
		
		
		//Exercise 3-13. Convert an IntStream to an int array
		int[] intArray = IntStream.of(3, 1, 4, 1, 5, 9).toArray();
		// or
		//int[] intArray = IntStream.of(3, 1, 4, 1, 5, 9).toArray(int[]::new);
		
		//The functional paradigm in Java often uses a process known as map-filter-reduce.
		//Table 3-1. Reduction operations in the IntStream class
//		Method									Return type
//		average									OptionalDouble
//		count									long
//		max										OptionalInt
//		min										OptionalInt
//		sum										int
//		
//		summaryStatistics						IntSummaryStatistics
//		collect(Supplier<R>supplier,			R
//				ObjIntConsumer<R> accumulator,
//				BiConsumer<R,R> combiner)
//		
//		reduce			int, OptionalInt
		
		
		//Exercise 3-14. Reduction operations on IntStream
		String[] strings1 = "this is an array of strings".split(" ");
		long count = Arrays.stream(strings1).map(String::length).count();
		System.out.println("There are " + count + " strings");
		
		int totalLength = Arrays.stream(strings1).mapToInt(String::length).sum();
		System.out.println("The total length is " + totalLength);
		

		OptionalDouble ave = Arrays.stream(strings1)
		        .mapToInt(String::length)
		        .average();
		System.out.println("The average length is " + ave.getAsDouble());
		
		OptionalInt max = Arrays.stream(strings1).mapToInt(String::length).max();
		
		OptionalInt min = Arrays.stream(strings1).mapToInt(String::length).min();

		System.out.println("The max and min lengths are " + max.getAsInt() + " and " + min);
		//The average length is OptionalDouble[3.6666666666666665]
		//The max and min lengths are OptionalInt[7] and OptionalInt[2]
		
		
		//There are two overloaded versions of the reduce method in IntStream: OptionalInt reduce(IntBinaryOperator op)
		//int reduce(int identity, IntBinaryOperator op)
		
		//Exercise 3-15. Summing numbers using reduce
		int sum = IntStream.rangeClosed(1, 10).reduce((x,y)-> x+y).orElse(0);
		System.out.println(sum);
		
		
		//first argument of the binary operator as an accumulator, and the second argument as the value of each element
		int sum1 = IntStream.rangeClosed(1, 10).reduce((x,y)->{
			System.out.printf("x=%d, y=%d%n",x ,y);
			return x+y;
		}).orElse(0);
		System.out.println(sum1);
		
		//Exercise 3-18. Doubling the values during the sum (NOTE: NOT CORRECT)
		int doubleSum = IntStream.rangeClosed(1, 10)
				.reduce((x, y) -> x + 2 * y).orElse(0);
	
		System.out.println(doubleSum);
		//The value of doubleSum is 109 (oops! off by one!)
	
		//Exercise 3-19. Doubling the values during the sum (WORKS)
		int doubleSum1 = IntStream.rangeClosed(1, 10)
				.reduce(0, (x,y) -> {
					System.out.printf("x=%d, y=%d%n",x ,y);
					return x + 2*y;});
		System.out.println(doubleSum1);
		
		//For addition, the identity is zero. 
		//For multiplication, the identity is 1. 
		//For string concatenation, the identity is the empty string.
		
		//Exercise 3-21. Performing a reduce with a binary operator
		int sum3 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).reduce(0, Integer::sum);
		System.out.println(sum3);
	
		//Exercise 3-22. Finding the max using reduce
		Integer max3 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).reduce(Integer.MIN_VALUE, Math::max);
		System.out.println("The max value is " + max);
		// The identity for max is the minimum integer
		
		//Exercise 3-23. Concatenating strings from a stream using reduce
		String s = Stream.of("this", "is", "a", "list")
		        .reduce("", String::concat);
		System.out.println(s);
		
		
		//Exercise 3-24. Collecting strings using a StringBuilder
		String s1 = Stream.of("this", "is", "a", "list")
				.collect(() -> new StringBuilder(), //Result Supplier
                (sb, str) -> sb.append(str),   		//Add a single value to the result
                (sb1, sb2) -> sb1.append(sb2))		//Combine two results
        .toString();
		System.out.println(s1);
		
		//Exercise 3-25. Collecting strings, with method references
		String s2 = Stream.of("this", "is", "a", "list")
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		System.out.println(s2);
		
		//Exercise 3-26. Joining strings using Collectors
		String s3 = Stream.of("this", "is", "a", "list")
		        .collect(Collectors.joining());
		System.out.println(s3);
		
		//<U> U reduce(U identity, BiFunction<U,? super T,U> accumulator,BinaryOperator<U> combiner)
		
		
		//Exercise 3-27. A simple Book class
		
		//The below exercise can also be solved using Collectors.toMap
		
		Book b1 = new Book(1, "Modern Java Recipes");
		Book b2 = new Book(2, "Making Java Groovy");
		Book b3 = new Book(3, "Gradle Recipes for Android");
//		Book[] bookArr = new Book[3];
//		bookArr[0] = b1;
//		bookArr[1] = b2;
//		bookArr[2] = b3;
//		List<Book> books = Arrays.asList(bookArr);
		List<Book> books = new ArrayList<Book>();
		books.add(b1);
		books.add(b2);
		HashMap<Integer, Book> bookMap = books.stream()
				.reduce(new HashMap<Integer, Book>(), // Identity
						(map, book) -> {   				//Bifunction accumulator
							map.put(book.getId(), book);
							return map;
						}, 
						(map1, map2) -> {				//Bifunction combiner
							map1.putAll(map2);
							return map1;
						});
		bookMap.forEach((k,v) -> System.out.println(k + ": " + v));
		
		//(like inject in Groovy or fold in Scala). They all work the same way.

		//3.4 Check Sorting Using Reduce
		//use the reduce method to check each pair of elements
		
		//Optional<T> reduce(BinaryOperator<T> accumulator)
		//Input & Output Type is same
		
		//Exercise 3-29. Summing BigDecimals with reduce
		BigDecimal total1 = Stream.iterate(BigDecimal.ONE, n->n.add(BigDecimal.ONE))
				.limit(10)
				.reduce(BigDecimal.ZERO, (acc,val) -> acc.add(val));
		System.out.println("The total is " + total1);

		//Using the add method in BigDecimal as a BinaryOperator
		
		List<String> strings4 = Arrays.asList(
			    "this", "is", "a", "list", "of", "strings");
		
		List<String> sorted1 = strings4.stream()
				.sorted(Comparator.comparingInt(String::length))
				.collect(Collectors.toList());
	
		System.out.println(sorted1);
		
		//Exercise 3-31. Testing that strings are sorted properly
//		sorted1.stream()
//		.reduce((prev,curr) -> {
//			assertTrue(prev.length() <= curr.length());
//			return curr; //curr becomes the next value of prev
//		});
		System.out.println("Junit Test Asserted True that Array is sorted");
		
		//3.5 Debugging Streams with peek
		//stream of integers, doubles each number, and then sums up only the resulting values divisible by 3
		//Exercise 3-32. Doubling integers,filtering, and summing
		System.out.println("Div = " + sumDoublesDivisibleBy3(1,10));
		
		//Exercise 3-33. Adding an identity map for printing
		System.out.println("Div Verbose = " + sumDoublesDivisibleByThree(1,10));
		
		//Stream<T> peek(Consumer<? super T> action)
		//Exercise 3-34. Using multiple peek methods
		System.out.println("Div Verbose Peek = " + sumDoublesDivisibleByThreeP(1,10));
		
	
		//3.6	Converting Strings to Streams and Back		
//		String is not part of the Collections framework, and therefore does not implement Iterable,
//		so there is no stream factory method to convert one into a Stream. 
//		Arrays.stream is not there for char[]
		
//		The String class implements the CharSequence interface, & the interface has two methods
//		Exercise 3-35. Stream methods in java.lang.CharSequence
//		default IntStream chars()   --> UTF-16- encoded characters 
//		default IntStream codePoints() --> full Unicode set of code points
		
		
		"toto".toLowerCase().codePoints().forEach(System.out::println);
				
		boolean isPal = isPalindrome("roor");
		System.out.println( isPal);
		
		boolean isPal2 = isPalindrome2("roor");
		System.out.println( isPal2);
		
		boolean isPal3 = isPalindrome2("Madam, in Eden, I'm Adam");
		System.out.println(isPal3);
		
//		The codePoints method returns an IntStream, which can then be filtered using the same condition as in Exercise 3-37. The interesting part is in the collect method, whose signature is:
//			<R> R collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator,
//			BiConsumer<R,R> combiner)
		
		
		//3.7	Counting Elements & PartitioningBy

		
		//The Stream interface has a default method called count that returns a long
		//Exercise 3-39. Counting elements in a stream
		long counting = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5).count(); 
		System.out.printf("There are %d elements in the stream%n", counting);
		
		//One interesting feature of the count method is that the Javadocs show how it is
		//implemented. The docs say, “this is a special case of a reduction and is equivalent to”:
		//return mapToLong(e -> 1L).sum();
		
		
		//Exercise 3-40. Counting the elements using Collectors.counting
		count = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
			    .collect(Collectors.counting());
		System.out.printf("There are %d elements in the stream%n", count);
			
		//Downstream collectors are discussed in detail separately in 4.6
			
		//Map<Boolean, List<String>>, where the keys would be the values true and false, 
		//and the values would be lists of even- and odd-length strings.
		Map<Boolean, Long> numberLengthMap = bradyBunch.stream()
				.collect(Collectors.partitioningBy(
				s0 -> s0.length() % 2 == 0, // Predicate 
				Collectors.counting())); // Downstream Collector
		
		numberLengthMap.forEach((k,v) -> System.out.printf("%5s: %d%n", k, v));
				
		Map<Boolean, Long> countLenghtMap = bradyBunch.stream()
				.collect(Collectors.partitioningBy(
						s0 -> s0.length() % 3 == 0, Collectors.counting()));
		
		countLenghtMap.forEach((k,v) -> System.out.printf("%5s: %d%n", k, v));
		//The Collectors methods are intended for downstream post- processing of a partitioningBy or groupingBy operation.
	
		
		//3.8	**Summary Statistics**
		
		//Exercise 3-42. SummaryStatistics
		DoubleSummaryStatistics stats = DoubleStream.generate(Math::random)
		    .limit(1_000_000)
		    .summaryStatistics();
		System.out.println(stats);
		System.out.println("count: " + stats.getCount());
		System.out.println("min  : " + stats.getMin());
		System.out.println("max  : " + stats.getMax());
		System.out.println("sum  : " + stats.getSum());
		System.out.println("ave  : " + stats.getAverage());
		//Java 7 added the capability to use underscores in numerical literals, as in 1_000_000.
		// 1_000_000 = 1,000,000
		
		//There are two other interesting methods in the DoubleSummaryStatistics class: 
		//void accept(double value)
		//void combine(DoubleSummaryStatistics other)
		
		//Exercise 3-43. Team class contains id, name, and salary
		LoadTeamRepository ltr = new LoadTeamRepository();
		List<Team> teams = new ArrayList<Team>();
		teams.add(ltr.t1);
		teams.add(ltr.t2);
		teams.add(ltr.t3);
		teams.add(ltr.t4);
		teams.add(ltr.t5);
		teams.add(ltr.t6);
		teams.add(ltr.t7);
		teams.add(ltr.t8);
		teams.add(ltr.t9);
		teams.add(ltr.t10);
		teams.add(ltr.t11);
		
		System.out.println(teams.toString());
//		Exercise 3-44. Collect with a Supplier, accumulator, and combiner
		
		DoubleSummaryStatistics teamStats = teams.stream()
				.mapToDouble(Team::getSalary)
				.collect(DoubleSummaryStatistics::new,
						DoubleSummaryStatistics::accept,
						DoubleSummaryStatistics::combine);
		
		System.out.println(teamStats);
		
//		Exercise 3-45. Collect using summarizingDouble
		DoubleSummaryStatistics teamStats1 = teams.stream()
				.collect(Collectors.summarizingDouble(Team::getSalary));
		
		System.out.println(teamStats1);
		//3.9	Finding the First Element in a Stream
		
		//The findFirst and findAny methods in java.util.stream.Stream return an Optional describing the first element of a stream. 
		//Exercise 3-46. Finding the  rst even integer
		Optional<Integer> firstEven = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
				.filter(n -> n% 2 == 0)
				.findFirst();
		
		System.out.println(firstEven);
		//Prints Optional[4]
		
		Optional<Integer> firstEvenGT10 = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
				.filter(n -> n > 10)
				.filter(n -> n % 2 == 0)
				.findFirst();
		
		System.out.println(firstEvenGT10);
		
		//Exercise 3-48. Using  rstEven in parallel
		Optional<Integer> firstEven1 = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
		    .parallel()
		    .filter(n->n%2==0)
		    .findAny();
		System.out.println(firstEven1);
		//encounter order is parallel
		//Prints Optional.empty
		//**** A List and an array both have an encounter order, but a Set does not.
		
		//There is also a method called unordered in BaseStream (which Stream extends) that (optionally!) returns an unordered stream
		
		
		List<String> wordList = Arrays.asList(
		        "this", "is", "a", "stream", "of", "strings");
		
		Set<String> words = new HashSet<>(wordList);
		Set<String> words2 = new HashSet<>(wordList);
		
		 // Now add and remove enough elements to force a rehash
		IntStream.rangeClosed(0, 50).forEachOrdered(i->words2.add(String.valueOf(i)));
		System.out.println(words.toString());
		words2.retainAll(wordList);
		System.out.println(words2.toString());
		
		 // The sets are equal, but have different element ordering
	    System.out.println(words.equals(words2));
	    System.out.println("Before: " + words);
	    System.out.println("After : " + words2);
	    
//	    The outputs will be something like:
//	        true
//	        Before: [a, strings, stream, of, this, is]
//	        After : [this, is, strings, stream, of, a]
		
	    
	    Optional<Integer> any = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
	    	    .unordered()
	    	    .parallel() // common fork-join pool in parallel
	    	    .map(Chapter3::delay) // introduce a dely
	    	    .findAny(); //Return the first element, regardless of encounter order
	    System.out.println("Any: " + any);
	    
	    Optional<Integer> any1 = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
	    		.unordered()
	    		.map(Chapter3::delay)
	    		.findAny();
	    
	    //Sequential stream (by default)
	    System.out.println("Sequential Any: " + any);
	    
	    Optional<Integer> any3 = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
	    		.unordered()
	    		.parallel()
	    		.map(Chapter3::delay)
	    		.findAny();
	    		
	    
	    System.out.println("Parallel Any: " + any3);
	    
	   
		//3.10 	Using anyMatch, allMatch, ad noneMatch
//	    boolean anyMatch(Predicate<? super T> predicate) 
//	    boolean allMatch(Predicate<? super T> predicate) 
//	    boolean noneMatch(Predicate<? super T> predicate)
	    //Exercise 3-51. Prime number check
	    System.out.println("Prime : "+ isPrime(13));
	    
	    //Interestingly, the java.math.BigInteger class has the method isProbablyPrime, which has the following signature:
//	    boolean isProbablyPrime(int certainty);
	    
//	    The anyMatch, allMatch, and noneMatch methods don’t necessarily behave intuitively on empty streams, 
		
//	    The allMatch and noneMatch methods return true and the any Match method returns false on an empty stream regardless of the supplied predicate.
	    //3.11  Stream  atMap Versus map
		//Use map if each element is transformed into a single value. 
	    //Use flatMap if each element will be transformed to multiple values and the resulting stream needs to be “flattened.”
		
	    //signature for map is:
	    //<R> Stream<R> map(Function<? super T,? extends R> mapper)
	    //A Function takes a single input and transforms it into a single output.
	    
	    //Exercise 3-55. Sample customers with orders
	    Customer sheridan = new Customer("Sheridan"); 
	    Customer ivanova = new Customer("Ivanova"); 
	    Customer garibaldi = new Customer("Garibaldi");
	    
	    sheridan.addOrder(new Order(1))
	    .addOrder(new Order(2))
	    .addOrder(new Order(3)); 
	    
	    ivanova.addOrder(new Order(4))
	    .addOrder(new Order(5));
	    List<Customer> customers = Arrays.asList(sheridan, ivanova, garibaldi);
	    
	    customers.stream()
	    		.map(Customer::getName)
	    		.forEach(System.out::println);
	    //Stream<Customer> 
	    //Stream<String>
	    //Sheridan, Ivanova, Garibaldi
	    
	    
	    //Exercise 3-57. Using map on Customer to orders
	    customers.stream()
	            .map(Customer::getOrders)
	            .forEach(System.out::println);
	    
	    customers.stream()
	            .map(customer -> customer.getOrders().stream())
	            .forEach(System.out::println);
	    
	    //The mapping operation results in a Stream<List<Order>>, where the last list is empty. 
	    //If you invoke the stream method on the lists of orders, you get a Stream<Stream<Order>>, where the last inner stream is an empty stream.
	    
	    //Flat Map
	    //<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
	    //For each generic argument T, the function produces a Stream<R> rather than just an R. 
	    //The flatMap method then “flattens” the resulting stream by removing each element from the individual streams and adding them to the output.
	    
	    
	    //Exercise 3-58. Using  atMap on Customer orders
	    customers.stream()
        .flatMap(customer -> customer.getOrders().stream())
        .forEach(System.out::println);
	    
	    //The two key concepts for flatMap are:
	    //1) The Function argument to flatMap produces a Stream of output values.
	    //2) The resulting stream of streams is flattened into a single stream of results.
	    
	    //As a final note, the Optional class also has a map method and a flatMap method. 
	    
		//3.12 Concatenating Streams
		//You want to combine two or more streams into a single one.
		//concat	- for small streams
		//flatMap	- for big streams
		
		//static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
		
		//Exercise 3-59. Concatenating two streams
		//First elements followed by second elements
		
		//Avoid stackOverflow
		//The idea is that the concat method essentially builds a binary tree of streams, which can grow unwieldy if too many are used.
		
		//Exercise 3-60. Concatenating multiple streams
		Stream<String> first = Stream.of("a", "b", "c").parallel(); 
		Stream<String> second = Stream.of("X", "Y", "Z"); 
		List<String> strings04 = Stream.concat(first, second)
	            .collect(Collectors.toList());
		System.out.println(strings04);
		
		Stream<String> third = Stream.of("alpha", "beta", "gamma"); 

		first = Stream.of("a", "b", "c").parallel(); 
		second = Stream.of("X", "Y", "Z"); 
	    List<String> strings5 = Stream.concat(Stream.concat(first, second), third)
	            .collect(Collectors.toList());
	    System.out.println(strings5);
	    
	    first = Stream.of("a", "b", "c").parallel(); 
		second = Stream.of("X", "Y", "Z"); 
		third = Stream.of("alpha", "beta", "gamma"); 
	    //Exercise 3-61. Concatenating with reduce
		Stream<String> fourth = Stream.empty();
	    List<String> strings6 = Stream.of(first, second, third, fourth)
	            .reduce(Stream.empty(), Stream::concat)
	            .collect(Collectors.toList());
	    System.out.println(strings6);
	    
	    
	    first = Stream.of("a", "b", "c").parallel(); 
		second = Stream.of("X", "Y", "Z"); 
		third = Stream.of("alpha", "beta", "gamma"); 
		fourth = Stream.empty();
	    //To avoid stack overflow problem use flatMap
	    //Exercise 3-62. Using  atMap to concatenate streams
	    List<String> strings7 = Stream.of(first, second, third, fourth)
	    		.flatMap(Function.identity())
	    		.collect(Collectors.toList());
	    System.out.println("7. " + strings7);
	    
	    first = Stream.of("a", "b", "c").parallel(); 
		second = Stream.of("X", "Y", "Z"); 
		third = Stream.of("alpha", "beta", "gamma"); 
		fourth = Stream.empty();
	    //Using concat creates a parallel stream if any input stream is parallel
	    //Whereas flatMap does not create any of parallel stream if any input stream is parallel
	    //Exercise 3-63. Parallel or not?
	    Stream<String> strings8 = Stream.of(first, second, third, fourth)
	            .flatMap(Function.identity());
	    System.out.println("8.After" + strings8.isParallel());
	    
	    //Exercise 3-64. Making a  atMap stream parallel
	   
	    first = Stream.of("a", "b", "c").parallel(); 
		second = Stream.of("X", "Y", "Z"); 
		third = Stream.of("alpha", "beta", "gamma");
		fourth = Stream.empty();
	    Stream<String> strings9 = Stream.of(first, second, third, fourth)
                .flatMap(Function.identity());
        System.out.println("9Before: " + strings9.isParallel());
        strings9 = strings9.parallel();
        System.out.println("9After: " + strings9.isParallel());
        
	    //See the excellent blog post online at http://bit.ly/e cient-multistream-concatentation for details, performance considerations, and more.
		//3.13 Lazy Streams
		
		//Exercise 3-65. First double between 200 and 400 divisible by 3
		OptionalInt firstEvenDoubleDivBy3 = IntStream.range(100, 200)
				.map(n -> n*2)
				.filter(n -> n%3==0)
				.findFirst();
		System.out.println(firstEvenDoubleDivBy3);
		
		
		//

		firstEvenDoubleDivBy3 = IntStream.range(100, 200)
				.map(ImplementPredicate::multByTwo)
				.filter(ImplementPredicate::divByThree)
				.findFirst();
		
	}
	
	//Exercise 3-51. Prime number check
    public static boolean isPrime(int num) {
    int limit = (int) (Math.sqrt(num) + 1);
    return num == 2 || num > 1 && IntStream.range(2, limit)
     .noneMatch(divisor -> num % divisor == 0);
    }
	
	//Exercise 3-49. Using  ndAny in parallel a er a random delay
	public static Integer delay(Integer n) { try {
	Thread.sleep((long) (Math.random() * 100)); } catch (InterruptedException ignored) {
	}
	return n;
	}
	
	
	public static int sumDoublesDivisibleBy3(int start, int end) { 
		return IntStream.rangeClosed(start, end)
			.map(n->n*2)
			.filter(n->n%3==0)
			.sum();
	}
	

	//Exercise 3-33. Adding an identity map for printing
	public static int sumDoublesDivisibleByThree(int start, int end) {
		return IntStream.range(start, end)
				.map(n -> {
					System.out.println(n);
					return n;
				}).map(n -> n*2)
				.filter(n -> (n%3 == 0)).sum();
	}
	
	//Exercise 3-34. Using multiple peek methods
	public static int sumDoublesDivisibleByThreeP(int start, int end) {
		return IntStream.range(start, end)
				.peek(n -> System.out.printf("original: %d%n", n)) //Print value before doubling
				.map(n->n*2)
				.peek(n -> System.out.printf("double : %d%n", n))//rint value after doubling but before filtering
				.filter(n -> n%3==0)
				.peek(n -> System.out.printf("filtered : %d%n", n))//Print value after filtering but before summing
				.sum();
		
	}
	
	//Exercise 3-66. Explicit processing of each stream element
	public static int multByTwo(int n) {
		System.out.printf("Inside multByTwo with arg %d%n", n); return n * 2;
	}
	
	public static boolean divByThree(int n) { 
		System.out.printf("Inside divByThree with arg %d%n", n); 
		return n%3==0;
	}
	
	
	//Exercise 3-36. Checking for palindromes in Java 7 or earlier
	public static boolean isPalindrome(String s) { 
		StringBuilder sb = new StringBuilder(); 
		for (char c : s.toCharArray()) {
			if (Character.isLetterOrDigit(c)) { 
				sb.append(c);
			}
		}
		String forward = sb.toString().toLowerCase();
		String backward = sb.reverse().toString().toLowerCase();
		return forward.equals(backward);
	}
	
	//Exercise 3-37. Checking for palindromes using Java 8 streams
	public static boolean isPalindrome2(String s) {
		String forward = s.toLowerCase().codePoints()  //Returns an IntStream
				.filter(Character::isLetterOrDigit)
				.collect(StringBuilder::new, 
						StringBuilder::appendCodePoint,
						StringBuilder::append).toString();
		
		String backward = new StringBuilder(forward).reverse().toString();
		return forward.equals(backward);
	}
}
