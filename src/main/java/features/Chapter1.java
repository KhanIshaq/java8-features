/**
 * 
 */
package features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import org.junit.Assert;

//diamond operator is not supported in -source 1.5 (use -source 7 or higher to enable diamond operator)
//lambda expressions are not supported in -source 1.5 (use -source 8 or higher to enable lambda expressions)
// method references are not supported in -source 1.5

/**
 * @author nsa
 *
 *	object::instanceMethod
 *	Refer to an instance method using a reference to the supplied object, as in
 *	System.out::println 
 *	Class::staticMethod
 *	Refer to static method, as in 
 *	Math::max
 *	Class::instanceMethod
 *	Invoke the instance method on a reference to an object supplied by the context, as in 
 *  String::length
 *
 *
 */
public class Chapter1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// 1-14. A Person class
		Person p1 = new Person("Mohammed Kamran");
		Person p2 = new Person("Ishaq Khan");
		Person p = new Person("Irfan Fatima Gulam");
		List<Person> people = new ArrayList<>();
		people.add(p1);
		people.add(p2);
		people.add(p);
		//Exercise 1-13. Converting a list of people to a list of names
		List<String> names = people.stream()
				.map(person -> person.getName())
				.collect(Collectors.toList());
		System.out.println(names);
		
		List<String> names1 = people.stream()
				.map(Person::getName)
				.collect(Collectors.toList());
		System.out.println(names1);
		
		//Exercise 1-15. Transforming strings into Person instances
		List<String> names2 =
		    Arrays.asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace",
		        "Karen Spärck Jones");
		
		//Using a lambda expression to invoke the constructor
		List<Person> people1 = names2.stream().map(name -> new Person(name))
				.collect(Collectors.toList());
		
		for(int k = 0; k<4;k++)	
			System.out.println(people1.get(k).getName());
		
		//Using a constructor reference instantiating Person
		List<Person> people2 = names2.stream().map(Person::new).collect(Collectors.toList());
		for(int k = 0; k<4;k++)	
			System.out.println(people2.get(k).getName());
	
		
		// Converting a list to a stream & back vice versa
		//the references are the same 
		Person before = new Person("Grace Hopper");
		List<Person> p3 = Stream.of(before).collect(Collectors.toList());
		Person after = p3.get(0);
		
		assertTrue(before == after);
		System.out.println("assertTrue = " + before.equals(after)); // Equals
		
		before.setName("Grace Murray Hopper");
		assertEquals("Grace Murray Hopper", after.getName());
		System.out.println("assertEquals = " + before.getName().equals(after.getName())); // Equals
		
		
		//Exercise 1-18. Using the copy constructor
		// reference is broken
		List<Person> p4 = Stream.of(before).map(Person::new)
		.collect(Collectors.toList());
		
		after = p4.get(0);
		//assertFalse(before == after); // Different Objects
		System.out.println("assertFalse = " + before.equals(after));
		//assertEquals(before, after); // Equivalent but different Objects
		before.setName("Rear Admiral Dr. Grace Murray Hopper");
		assertFalse(before.equals(after));
		System.out.println("assertFalse = " + before.equals(after));
		
		
		List<Person> p5 = names.stream()
		.map(name -> name.split(" "))
		.map(Person::new)
		.collect(Collectors.toList());
		//Create a stream of strings
		//Map to a stream of string arrays 
		//Map to a stream of Person 
		//Collect to a list of Person
		for(int k = 0; k<3;k++)	
			System.out.println("5. " + p5.get(k).getName());
		
		List<String[]> p6 = names.stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());
		
		for(int k = 0; k<3;k++)	
			System.out.println("6. " + String.join(" ", p6.get(k)));
			
		Person[] p7 = names.stream().map(Person::new).toArray(Person[]::new);
		for(int k = 0; k<3;k++)		
			System.out.println("7. " + p7[k].getName());
		
		
		List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9);
		//default method = removeIf
		boolean removed = nums.removeIf(n -> n<=0);
		System.out.println("Elements were " + (removed ? "" : "NOT") + " removed"); 
		//default method = forEach
		nums.forEach(System.out::println);
		
		
		//Static Methods in Interfaces
		// class-level utility method to an interface
		
		//java.util.Collections, which contains methods for sorting and searching, wrapping collections in synchronized or unmodiafiable types, and more. 
//		NIO package, java.nio.file.Paths is another exam‐ ple. It contains only static methods that parse Path instances from strings or URIs.
		
		
		//Exercise 1-28. Sorting strings
		//Natural order (lexicographical)
		List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore","Dalton", "Brosnan", "Craig");
		List<String> sorted = bonds.stream()
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());
		System.out.println("1. "+sorted);
		
		//Reverse Lexicographical
		sorted = bonds.stream()
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
		System.out.println("2. "+ sorted);
		
		//sort by lowercase
		sorted = bonds.stream()
				.sorted(Comparator.comparing(String::toLowerCase))
				.collect(Collectors.toList());
		System.out.println("3. "+ sorted);
		
		// 48 -56
		// 65 - 90
		// 97 - 122
		
		// sort by name length
		sorted = bonds.stream()
				.sorted(Comparator.comparing(String::length))
				.collect(Collectors.toList());
		System.out.println("4. "+ sorted);
		
		// sort by length then equal lengths lexicographically
		sorted = bonds.stream()
				.sorted(Comparator.comparing(String::length))
				.sorted(Comparator.comparing(String::toLowerCase))
				.collect(Collectors.toList());
		System.out.println("5. "+ sorted);
		
		sorted = bonds.stream()
			    .sorted(Comparator.comparingInt(String::length)
			        .thenComparing(Comparator.naturalOrder()))
			    .collect(Collectors.toList());
		
		System.out.println("6. "+ sorted);
	}
}
