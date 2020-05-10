/**
 * 
 */
package features;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * @author ishaqkhan
 *
 */
public class Chapter6 {
	
	private static List<Employee6> employees = Arrays.asList(
			new Employee6(1,"Cersei", 250_000, "Lannister"),
			new Employee6(2,"Jamie", 150_000, "Lannister"),
			new Employee6(3,"Tyrion", 1_000, "Lannister"),
			new Employee6(4,"Tywin", 1_000_000, "Lannister"),
			new Employee6(5,"Jon Snow", 75_000, "Stark"),
			new Employee6(6,"Robb", 120_000, "Stark"),
			new Employee6(7,"Eddard", 125_000, "Stark"),
			new Employee6(8,"Sansa", 0, "Stark"),
			new Employee6(9,"Arya", 1_000, "Stark"));

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//The precedence of character-class operators is as follows, from highest to lowest:
		//1    	Literal escape    	\x
		//2    	Grouping	[...]
		//3    	Range	a-z
		//4    	Union	[a-e][i-u]
		//5    	Intersection	[a-z&&[aeiou]]
		//Character classes
		//[abc]	a, b, or c (simple class)
		//[^abc]	Any character except a, b, or c (negation)
		//[a-zA-Z]	a through z or A through Z, inclusive (range)
		//[a-d[m-p]]	a through d, or m through p: [a-dm-p] (union)
		//[a-z&&[def]]	d, e, or f (intersection)
		//[a-z&&[^bc]]	a through z, except for b and c: [ad-z] (subtraction)
		//[a-z&&[^m-p]]	a through z, and not m through p: [a-lq-z](subtraction)
		 
		//Predefined character classes
		// s.	Any character (may or may not match line terminators)
		// \d	A digit: [0-9]
		// \D	A non-digit: [^0-9]
		// \h	A horizontal whitespace character: [ \t\xA0\u1680\u180e\u2000-\u200a\u202f\u205f\u3000]
		// \H	A non-horizontal whitespace character: [^\h]
		// \s	A whitespace character: [ \t\n\x0B\f\r]
		// \S	A non-whitespace character: [^\s]
		// \v	A vertical whitespace character: [\n\x0B\f\r\x85\u2028\u2029]
		// \V	A non-vertical whitespace character: [^\v]
		// \w	A word character: [a-zA-Z_0-9]
		// \W	A non-word character: [^\w]
		
		/*s Greedy quantifiers
			X?	X, once or not at all
			X*	X, zero or more times
			X+	X, one or more times
			X{n}	X, exactly n times
			X{n,}	X, at least n times
			X{n,m}	X, at least n but not more than m times
		 */

		 Pattern p = Pattern.compile("a+b");
		 Matcher m = p.matcher("ab");
		 boolean b = m.matches();
		 System.out.println(b);
		 
		 b = Pattern.matches("a*b", "aaaaab");
		 System.out.println(b);

		//The Optional Type
		 
		// Class -> java.util.Optional<T> 
		//Stream API that return Optional -> reduce, min, max, findFirst, findAny.
		 //An instance of Optional can be in one of two states: a reference to an instance of type T, or empty. 
		 //The former case is called present, and the latter is known as empty
		 
		 //6.1 Creating an Optional
		 //Use Case
		 //You need to return an Optional from an existing value.
		 //Use Optional.of , Optional.ofNullable , Optional.empty
		 //instances of Optional are immutable.
		 //Optional is value based class
		 // are final
		 //have no public constructors
		 //implementations of equals, hashCode & toString
		 
		 //Exercise 6-1. Are Optionals immutable?
		 
		 AtomicInteger counter = new AtomicInteger();
		 Optional<AtomicInteger> opt = Optional.ofNullable(counter);
		 System.out.println(opt);
		 counter.incrementAndGet();
		 System.out.println(opt);
		 opt.get().incrementAndGet();
		 System.out.println(opt);
		 
		 opt = Optional.ofNullable(new AtomicInteger());
		 System.out.println(opt);
		 
		 //static factory methods
		 //static <T> Optional<T> empty()
		 //static <T> Optional<T> of(T value)
		 //static <T> Optional<T> ofNullable(T value)
		 
		 //Wrapping the Primitive
		 //static OptionalInt of(int value) 
		 //static OptionalLong of(long value) 
		 //static OptionalDouble of(double value)
		 //getAsInt
		 //getAsLong
		 //getAsDouble
		 
		 //6.2 Retrieving Values from an Optional
		 //Use Case
		 //To extract a contained value from an Optional.
		 
		 //If Optional is Empty() get throws = NoSuchElementException
		 
		 Optional<String> firstEvent = Stream.of("five", "even", "length", "string", "values")
				 .filter(s -> s.length()%2==0)
				 .findFirst();
		System.out.println(firstEvent.get()); // Don't do this, even if it works
		 
		//Exercise 6-5. Retrieving the  rst odd-length string
		Optional<String> firstOdd =
			    Stream.of("five", "even", "length", "string", "values")
			        .filter(s -> s.length() % 2 != 0)
			        .findFirst();
		
		//System.out.println(firstOdd.get()); // Dont do this because it throws NoSuchElementException
		
		
		//Exercise 6-6. Retrieving the  rst even-length string with a protected get
		Optional<String> firstEven =
		    Stream.of("five1", "even1", "length1", "string1", "values1")
		          .filter(s -> s.length() % 2 == 0)
		          .findFirst();
		System.out.println(
				firstEven.isPresent() ? firstEven.get() : "No even length strings");
		
		//Exercise 6-7. Using orElse
		Optional<String> firstOddStr =
		    Stream.of("five", "even", "length", "string", "values")
		        .filter(s -> s.length() % 2 != 0)
		        .findFirst();
		System.out.println(firstOddStr.orElse("No odd length strings"));
		
		// orElse(T other) returns the value if present, otherwise it returns the default value, other
		//orElseGet(Supplier<? extends T> other) returns the value if present, other‐wise it invokes the Supplier and returns the result
		//orElseThrow(Supplier<? extends X> exceptionSupplier) returns the value if present, otherwise throws the exception created by the Supplier
		
		//the argument to orElse is a complex object, orElseGet with a Supplier ensures the object is only created when needed
		
		List<String> values = Stream.of("ishaq", "kamran", "raisa", "younus").collect(Collectors.toList());
		//Exercise 6-8. Using a Supplier in orElseGet
//		Optional<ComplexObject> val = values.stream().findFirst();
//		
//		val.orElse(new ComplexObject());
//		val.orElseGet(() -> new ComplexObject());
		
//		Exercise 6-10. Using orElse row as a Supplier
		Optional<String> first =
		    Stream.of("five", "even", "length", "string", "values")
		        .filter(s -> s.length() % 2 == 0)
		        .findFirst();
		System.out.println(first.orElseThrow(NoSuchElementException::new));
		
		//Exercise 6-11. Using the ifPresent method
		Optional<String> firstP =
			    Stream.of("five", "even", "length", "string", "values")
			        .filter(s -> s.length() % 2 == 0)
			        .findFirst();
		firstP.ifPresent(val -> System.out.println("Found and even length string"));
		
		firstP = Stream.of("five", "even", "length", "string", "values")
			    .filter(s -> s.length() % 2 != 0)
			    .findFirst();
		firstP.ifPresent(val -> System.out.println("Found an odd-length string"));
		
		//6.3 Optional in Getters and Setters
		//Use Case
		//You wish to use Optional in accessors and mutators.
		
		//but because Optional is not serializable, neither would be Department.
		//The approach in this recipe violates that pattern. The getter and the setter are no longer symmetrical.
		
		
		//6.4 Optional  flatMap Versus map
		//Use Case
		//You want to avoid wrapping an Optional inside another Optional.
		//Use the flatMap method in Optional.
		
		//Signature
		//<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
		
		//Exercise 6-14. Returning an Optional
		Manager mrSlate = new Manager("Mr. Slate");
		Department d = new Department(); 
		d.setBoss(mrSlate); 
		System.out.println("Boss: " + d.getBoss());
		
		Department d1 = new Department(); 
		System.out.println("Boss: " + d1.getBoss());
		
		//Exercise 6-15. Extract a name from an Optional manager
		System.out.println("Name: " +
				d.getBoss().orElse(new Manager("Unknown")).getName());
		
		System.out.println("Name: " + d.getBoss().map(Manager::getName));
		System.out.println("Name: " + d1.getBoss().map(Manager::getName));
		
		CompanyC co = new CompanyC();
		co.setDepartment(d);
		System.out.println("Company Dept: " + co.getDepartment());
		System.out.println("Company Dept Manager: " + co.getDepartment().map(Department::getBoss));
		
		//Exercise 6-18. Using  atMap on a company
		System.out.println(co.getDepartment() // Optional<Department>
			        .flatMap(Department::getBoss) // Optional<Manager>
			        .map(Manager::getName));  //Optional<String>
		
		//Exercise 6-19. Using  atMap on an optional company
		Optional<CompanyC> company = Optional.of(co);
		System.out.println(company.flatMap(CompanyC::getDepartment)
		.flatMap(Department::getBoss)
		.map(Manager::getName));
		//Optional.flatMap
		
		//6.5 Mapping Optionals
		//Use Case
		//You want to apply a function to a collection of Optional instances, but only if they contain a value.
		
		//<U> Optional<U> map(Function<? super T,? extends U> mapper)
		System.out.println(findEmployeesById(Stream.of(1,3,5,7).collect(Collectors.toList())));
		
	}
	
	 //Exercise 6-2. Creating an Optional with “of ”
	 public static <T> Optional<T> createOptionalTheHardWay(T value) { 
		 return value == null ? Optional.empty() : Optional.of(value);
	 }
	 
	 //Exercise 6-3. Creating an Optional with “ofNullable”
	 public static <T> Optional<T> createOptionalTheEasyWay(T value){
		 return Optional.ofNullable(value);
	 }
	 
	 //Exercise 6-9. Implementation of Optional.orElseGet in the JDK
//	 public T orElseGet(Supplier<? extends T> other) { 
//		 return value != null ? value : other.get();
//	 }
//	 value is a final attribute of type T in Optional
//	 <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
	 
	 public static Optional<Employee6> findEmployeeById(int id){
		 return Optional.ofNullable(employees.get(id));
	 }
	 
	 //Exercise 6-20. Finding Employees by ID
	 public static List<Employee6> findEmployeesById(List<Integer> ids){
		 return ids.stream()
				 .map(Chapter6::findEmployeeById)
				 .filter(Optional::isPresent)
				 .map(Optional::get)
				 .collect(Collectors.toList());
		 
				 
	 }
	 
	 //Exercise 6-21. Using Optional.map
	 public static List<Employee6> findEmployeesByIds2(List<Integer> ids) {
		 return ids.stream()
				 .map(Chapter6::findEmployeeById)    //Stream<Optional<Employee>>
				 .flatMap(opt -> opt.map(Stream::of) //Turns nonempty Optional<Employee> into Optional<Stream<Employee>>
						 .orElseGet(Stream::empty))
				 		//Extracts the Stream<Employee> from the Optional
				 .collect(Collectors.toList());
	 }
	 
}
