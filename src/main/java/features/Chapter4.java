/**
 * 
 */
package features;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ishaqkhan
 *
 */
public class Chapter4 {

	//Exercise 4-1. Sorting strings lexicographically
	private static List<String> sampleStrings =
			Arrays.asList("this", "is", "a", "list", "of", "strings");
		
	//Exercise 4-5. Sorting golfers
	private static final List<Golfer> golfers = Arrays.asList( 
			new Golfer("Jack", "Nicklaus", 68),
			new Golfer("Tiger", "Woods", 70),
			new Golfer("Tom", "Watson", 70),
			new Golfer("Ty", "Webb", 68),
			new Golfer("Bubba", "Watson", 70));
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(defaultSort());
		System.out.println(defaultSortUsingStreams());
		
		//Exercise 4-14. A collection of books
		List<Book> books = Arrays.asList(
		new Book(1, "Modern Java Recipes", 49.99),
		new Book(2, "Java 8 in Action", 49.99),
		new Book(3, "Java SE8 for the Really Impatient", 39.99),
		new Book(4, "Functional Programming in Java", 27.64),
		new Book(5, "Making Java Groovy", 45.99),
		new Book(6, "Gradle Recipes for Android", 23.76)
		);
		
		//Exercise 4-6. Sorted golfers
		System.out.println(sortByScoreThenLastThenFirst().toString());
		//		
		
		//4.2 Converting a Stream into a Collection
		// List, Set or other linear collection 
		//Exercise 4-7.  e collect method in Stream<T>
		//<R,A> R collect(Collector<? super T,A,R> collector) 
		//<R> Rcollect(Supplier<R>supplier,
		//					BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
		
//		Collector is an interface therefore cannot be instantiated.
		
		//The Java 8 API frequently uses a static method called 'of' as a factory method.
		
		//Exercise 4-8. Creating a List
		List<String> superHeroes =
		    Stream.of("Mr. Furious", "The Blue Raja", "The Shoveler",
		              "The Bowler", "Invisible Boy", "The Spleen", "The Sphinx")
		          .collect(Collectors.toList());
		
		//Exercise 4-9. Creating a Set
		Set<String> villains =
		    Stream.of("Casanova Frankenstein", "The Disco Boys",
		     "The Not-So-Goodie Mob", "The Suits", "The Suzies",
		    "The Furriers", "The Furriers")
		.collect(Collectors.toSet());
		
		//Exercise 4-10. Creating a linked list
		List<String> actors =
			    Stream.of("Hank Azaria", "Janeane Garofalo", "William H. Macy",
			"Paul Reubens", "Ben Stiller", "Kel Mitchell", "Wes Studi").collect(Collectors.toCollection(LinkedList::new));
		
		//default data structures—ArrayList for List, and HashSet for Set
		
		//two overloads of the toArray method:
        //Object[] toArray();
        //<A> A[]      toArray(IntFunction<A[]> generator);
		

		//Exercise 4-11. Creating an array
		String[] wannabes =
		    Stream.of("The Waffler", "Reverse Psychologist", "PMS Avenger")
		    .toArray(String[]::new);
		Arrays.stream(wannabes).forEach(System.out::println);
		
		Actor a1 = new Actor("Janeane Garofalo","The Bowler");
		Actor a2 = new Actor("Greg Kinnear","Captain Amazing");
		Actor a3 = new Actor("William H. Macy","The Shoveler");
		Actor a4 = new Actor("Paul Reubens","The Spleen");
		Actor a5 = new Actor("Wes Studi","The Sphinx");
		Actor a6 = new Actor("Kel Mitchell","Invisible Boy");
		Actor a7 = new Actor("Geoffrey Rush","Casanova Frankenstein");
		Actor a8 = new Actor("Ben Stiller","Mr. Furious");
		Actor a9 = new Actor("Hank Azaria","The Blue Raja");
		List<Actor> mysteryMen = new ArrayList<Actor>();
		mysteryMen.add(a1);mysteryMen.add(a2);mysteryMen.add(a3);
		mysteryMen.add(a4);mysteryMen.add(a5);mysteryMen.add(a6);
		mysteryMen.add(a7);mysteryMen.add(a8);mysteryMen.add(a9);
		
		Set<Actor> actors1 = mysteryMen.stream().collect(Collectors.toSet());
		
		Map<String, String> actorMap = actors1.stream().collect(Collectors.toMap(Actor::getName, Actor::getRole));
		
		actorMap.forEach((K,V) -> System.out.printf("%s played %s%n", K,V));

		//4.3 Adding a Linear Collection to a Map
		
		//Exercise 4-14. A collection of books
		List<Book> books1 = Arrays.asList(
		new Book(1, "Modern Java Recipes", 49.99),
		new Book(2, "Java 8 in Action", 49.99),
		new Book(3, "Java SE8 for the Really Impatient", 39.99), 
		new Book(4, "Functional Programming in Java", 27.64), 
		new Book(5, "Making Java Groovy", 45.99),
		new Book(6, "Gradle Recipes for Android", 23.76)
		);
		
		//In many situations, instead of a List you might want a Map, where the keys are the book IDs and the values are the books themselves. This is really easy to accomplish using the toMap method in Collectors, as shown two different ways in Exercise 4-15.
		//Exercise 4-15. Adding the books to a Map
		Map<Integer, Book> bookMap = books.stream()
		    .collect(Collectors.toMap(Book::getId, b -> b));
		//Identity lambda, given an element & return it
		
		bookMap.forEach((k,v) -> System.out.println(k + " -  " + v));
		
		bookMap = books.stream()
		    .collect(Collectors.toMap(Book::getId, Function.identity()));
		//Static identity method in Function

		bookMap.forEach((k,v) -> System.out.println(k + ": " + v));
		
		
		//4.4 Sorting Maps
		//Table 4-1. Static methods in Map.Entry (from Java 8 docs)
		//Method									Description
		//comparingByKey() 							Returns a comparator that comparesMap.Entry in natural order on key
		//comparingByKey(Comparator<? super K> cmp) Returns a comparator that comparesMap.Entry by key using the given Comparator.
		//comparingByValue() 						Returns a comparator that comparesMap.Entry 	in natural order on value
		//comparingByValue(Comparator<?superV>cmp)	Returns a comparator that comparesMap.Entry by value using the given Comparator
		
		//Exercise 4-18. Reading the dictionary  le into a Map
		System.out.println("\n Number of words of each length");
		try(Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Downloads/LeetCode/behavioural_questions.txt"))){
			
				lines.filter(s -> s.length() > 20)
				.collect(Collectors.groupingBy(String::length, Collectors.counting()))
				.forEach((len,num) -> System.out.printf("%d: %d%n",len, num));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//the return type is Map<Integer,Long>, where the keys are the word lengths and the values are the number of words of that length in the dictionary.
		// Without the downstream collector Collectors.counting()
		//groupingBy(String::length) would have pro‐ duced a Map<Integer,List<String>> where the keys are the word lengths and the values are lists of words of that length.

		System.out.println("\nNumber of words of each length using ID & Object):"); 
		try (Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Downloads/LeetCode/behavioural_questions.txt"))){
		    Map<Integer, List<String>> map = lines.filter(s -> s.length() > 20)
		        .collect(Collectors.groupingBy(
		            String::length));
		    //map.entrySet().forEach(e -> System.out.printf("ID %d: %s%n", e.getKey(), e.getValue()));
		    map.forEach((k,v) -> System.out.printf("ID %d: %s%n", k, v));
		}catch (Exception e) {
			e.printStackTrace();
		}
		            
		//Exercise 4-19. Sorting the map by key
		System.out.println("\nNumber of words of each length (desc order):"); 
		try (Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Downloads/LeetCode/behavioural_questions.txt"))){
		    Map<Integer, Long> map = lines.filter(s -> s.length() > 20)
		        .collect(Collectors.groupingBy(
		            String::length, Collectors.counting()));
		    
		    map.entrySet().stream()
		        .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
		        .forEach(e -> System.out.printf("Length %d: %2d words%n",
		        		e.getKey(), e.getValue())); 
		    
		    //The sorted method on Stream produces a new, sorted stream that does not modify the source. The original Map is unaffected.
		    
		}catch (IOException e) {
		    e.printStackTrace();
		}
		
		//4.5 Partitioning and Grouping
		
		//Exercise 4-20. Partitioning strings by even or odd lengths
		List<String> strings = Arrays.asList("this", "is", "a", "long", "list", "of",
		        "strings", "to", "use", "as", "a", "demo");
		//Partitioning by even or odd length
		Map<Boolean, List<String>> lengthMap = strings.stream().collect(Collectors.partitioningBy(s -> (s.length() % 2 == 0)));
		lengthMap.forEach((key,value) -> System.out.printf("%5s: %s%n", key, value));
		
		//The signature of the two partitioningBy methods are:
		//static <T> Collector<T,?,Map<Boolean,List<T>>> partitioningBy( Predicate<? super T> predicate)
		//static <T,D,A> Collector<T,?,Map<Boolean,D>> partitioningBy( Predicate<? super T> predicate, Collector<? super T,A,D> downstream)
		
		//The second overloaded version of the method takes a second argument of type Collector, called a downstream collector. 
		//This allows you to postprocess the lists returned by the partition
		
		/**
		 * If you are getting your data from a database, by all means do any grouping operations there. 
		 * The new API methods are convenience methods for data in memory.
		 */
		
		//The signature for the groupingBy method is:
		//static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy(Function<? super T,? extends K> classifier)
	
		//Exercise 4-21. Grouping strings by length
		List<String> strings1 = Arrays.asList("this", "is", "a", "long", "list", "of",
		        "strings", "to", "use", "as", "a", "demo");
		Map<Integer, List<String>> lengthMap1 = strings1.stream().collect(Collectors.groupingBy(String::length));
		lengthMap1.forEach((k,v) -> System.out.printf("%d: %s%n", k, v)); 
		//Grouping strings by length
		// key is the length & values is the list of string
	
		//4.6 Downstream Collectors
		
		List<String> strings2 = Arrays.asList("this", "is", "a", "long", "list", "of",
		        "strings", "to", "use", "as", "a", "demo");
		
		Map<Boolean, List<String>> lengthMap2 = strings2.stream().collect(Collectors.partitioningBy(s -> s.length() % 2 == 0));
		lengthMap2.forEach((k,v) -> System.out.printf("%5s: %s%n", k, v));
		
		//Exercise 4-23. Counting the partitioned strings
		Map<Boolean, Long> numberLengthMap = strings2.stream()
				.collect(Collectors.partitioningBy(s -> s.length() % 2 == 0, Collectors.counting()));
		
		numberLengthMap.forEach((k,v) -> System.out.printf("%5s: %d%n", k, v));
		
		//static <T,K,A,D> Collector<T,?,Map<K,D>> groupingBy(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream)
		//T = Type
		//K = key type of Map
		//A = Accumulator
		//D = downstream reduction

		//Stream					Collectors
		//count						counting
		//map						mapping
		//max						maxBy
		//min						minBy
		//IntStream.sum				summingInt
		//DoubleStream.sum			summingDouble
		//LongStream.sum			summingLong
		//IntStream.summarizing		summarizingInt
		//DoubleStream.summarizing	summarizingDouble
		//LongStream.summarizing	summarizingLong
		
		//Again, the purpose of a downstream collector is to postprocess the collection of objects produced by an upstream operation, like partitioning or grouping.
		
		//4.7 Finding Max and Min Values
		//BinaryOperator interface adds two static methods:
		//static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) 
		//static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator)
		
		//Collection of employees
		List<Employee> employees = Arrays.asList(
				new Employee("Cersei", 250_000, "Lannister"),
				new Employee("Jamie", 150_000, "Lannister"),
				new Employee("Tyrion", 1_000, "Lannister"),
				new Employee("Tywin", 1_000_000, "Lannister"),
				new Employee("Jon Snow", 75_000, "Stark"),
				new Employee("Robb", 120_000, "Stark"),
				new Employee("Eddard", 125_000, "Stark"),
				new Employee("Sansa", 0, "Stark"),
				new Employee("Arya", 1_000, "Stark"));
		
		List<Employee> employees2 = new ArrayList<>();
		//Default for when the stream is empty
		Employee defaultEmployee = new Employee("A man (or woman) has no name", 0, "Black and White");


		//Exercise 4-25. Using BinaryOperator.maxBy
		Optional<Employee> optionalEmp = employees.stream()
				.reduce(BinaryOperator.maxBy(Comparator.comparingInt(Employee::getSalary)));
						   
		System.out.println("Emp with max salary: " +
			    optionalEmp.orElse(defaultEmployee));	  
		
		Optional<Employee> optionalEmp1 = employees2.stream()
				.reduce(BinaryOperator.maxBy(Comparator.comparingInt(Employee::getSalary)));
						   
		System.out.println("Emp with max salary: " +
			    optionalEmp1.orElse(defaultEmployee));
				  
		//Exercise 4-26. Using Stream.max
		Optional<Employee> optionalEmp2 = employees.stream().max(Comparator.comparingInt(Employee::getSalary));
		System.out.println("Emp with max salary: " +
			    optionalEmp2.orElse(defaultEmployee));
		
		//Primitive
		OptionalInt maxSalary = employees.stream().mapToInt(Employee::getSalary).max();
		System.out.println("The max salary is " + maxSalary);
		
		//Exercise 4-28. Using Collectors.maxBy
		optionalEmp = employees.stream()
			    .collect(Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)));
		System.out.println("Max Salary Employee : " + optionalEmp.toString());
		
		//Exercise 4-29. Using Collectors.maxBy as a downstream collector
		Map<String, Optional<Employee>> maxEmpMap = employees.stream().
				collect(Collectors.groupingBy(Employee::getDepartment,
												Collectors.maxBy(Comparator.comparingInt(Employee::getSalary))));
		
		maxEmpMap.forEach((house, e) -> System.out.println(house + " <-> " + e.orElse(defaultEmployee)));
		//The minBy method in each of these classes works the same way.
		
		//4.8 Creating Immutable Collections
		
		//Promised not to corrupt the input array type.
//		@SafeVarargs
//		public final <T> List<T> createImmutableListJava7(T... elements) { 
//			return Collections.unmodifiableList(Arrays.asList(elements));
//		}
//		@SafeVarargs
//		public final <T> Set<T> createImmutableSetJava7(T... elements) {
//			return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
//		}
		
		// Check the program UnmodifiableSample.java

		//The Collectors.collectingAndThen method takes two arguments: a downstream Collector and a Function called a  finisher.
		
		//Exercise 4-30. Unmodi able methods in the Collections class
		//static <T> List<T> unmodifiableList(List<? extends T> list) 
		//static <T> Set<T> unmodifiableSet(Set<? extends T> s)
		//static <K,V> Map<K,V> unmodifiableMap(Map<? extends K,? extends V> m)
		
		//Exercise 4-33. Creating an immutable Map
		Map<String, Integer> map = Collections.unmodifiableMap( 
		new HashMap<String, Integer>() {{
		    put("have", 1);
		    put("the", 2);
		    put("high", 3);
		    put("ground", 4);
		}});
		
		//4.9 Implementing the Collector Interface
		//java.util.stream.Collectors;
		//Taking the characteristics function first, it represents an immutable Set of elements of an enum type Collector.Characteristics.
		//The three possible values are CONCURRENT, IDENTITY_FINISH, and UNORDERED. CONCURRENT
		
		//The purpose of each of the required methods is:
		//supplier()  -  Create the accumulator container using a Supplier<A>
		//accumulator() -  Add a single new data element to the accumulator container using a BiCon sumer<A,T>
		//combiner() - Merge two accumulator containers using a BinaryOperator<A>
		//finisher() -  Transform the accumulator container into the result container using a Function <A,R>
		//characteristics() -		A Set<Collector.Characteristics> chosen from the enum values
		
		// Samples are below in methods
//		static <T,A,R> Collector<T,A,R> of(Supplier<A> supplier, BiConsumer<A,T> accumulator,
//				BinaryOperator<A> combiner,
//				Function<A,R> finisher, Collector.Characteristics... characteristics)
//		static<T,R>Collector<T,R,R> of(Supplier<R>supplier, BiConsumer<R,T> accumulator,
//				BinaryOperator<R> combiner, Collector.Characteristics... characteristics)
		
	
	}
	
			
	//Java 7
	public static List<String> defaultSort() {
		Collections.sort(sampleStrings);
		return sampleStrings;
	}

	// Java 8 - Functional principles of Java 8 supports immutability
	public static List<String> defaultSortUsingStreams() {
		return sampleStrings.stream().sorted().collect(Collectors.toList());
	}
	
	//Exericse 4-2. Sorting strings by length
	//Using Lambda for sorting
	public static List<String> lengthSortUsingSorted() { 
		return sampleStrings.stream()
	        .sorted((s1, s2) -> s1.length() - s2.length())
	        .collect(Collectors.toList());
	}
	
	//Using comparator
	public static List<String> lengthSortUsingComparator() {
		return sampleStrings.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
	}
	
	//Exericse 4-3. Sorting by length, then equal lengths lexicographically
	public static List<String> lengthSortThenAlphaSort() {
		return sampleStrings.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).collect(Collectors.toList());			
	}
	
	public static List<Golfer> sortByScoreThenLastThenFirst() {
		return golfers.stream().sorted(Comparator.comparingInt(Golfer::getScore).thenComparing(Golfer::getLast).thenComparing(Golfer::getFirst)).collect(Collectors.toList());
		
	}
	
	//The static identity method in Function has the signature static <T> Function<T,T> identity()
	//The implementation in the standard library is shown in 
	//Exercise 4-16.  e static identity method in Function
	static <T> Function<T, T> identity() { 
	return t -> t;
	}
	
	//Its implementation in the standard library is essentially the same, as shown in Exercise 4-17.
	//Exercise 4-17.  e static identity method in UnaryOperator
	static <T> UnaryOperator<T> identity1() { 
		return t -> t;
	}
	
	//Exercise 4-34. Using collect to return a List
	public List<String> evenLengthStrings(String... strings) { 
		return Stream.of(strings)
		.filter(s -> s.length() % 2 == 0)
		.collect(Collectors.toList());
	}
	
	//Exercise 4-35. How the Collector methods are used
//	R container = collector.supplier.get(); 
//	for(T t:data){
//	    collector.accumulator().accept(container, t);
//	}
//	return collector.finisher().apply(container);
	//Create the accumulator container
	//Add each element to the accumulator container
	//Convert the accumulator container to the result container using the finisher
	
	//Exercise 4-36. Using collect to return an unmodi able SortedSet
	public SortedSet<String> oddLengthStringSet(String... strings){
		
		Collector<String, ?, SortedSet<String>> intoSet = null;
//				Collector.of(TreeSet<String>::new,
//							SortedSet::add,
//							(left,right) -> {
//								left.addAll(right);
//								return left;
//							},
//							Collections::unmodifiableSet);
		
		return Stream.of(strings).filter(s -> s.length()%2 != 0).collect(intoSet);
		// Supplier to create TreeSet
		//Biconsumer to add each string to TreeSet
		//Binary Operator to combine two SortedSet
		//finisher to create unmodified set
	}

}
