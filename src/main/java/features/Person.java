package features;

import java.util.Arrays;
import java.util.stream.Collectors;

//Exercise 1-14. A Person class
public class Person{
		String name;

		public Person(String name) {
			super();
			this.name = name;
		}

		//Exercise 1-16. A copy constructor for Person
		public Person(Person p) { 
			this.name = p.name;
		}
		
		// Exercise 1-19. A Person constructor that takes a variable argument list of String
		//This constructor takes zero or more string arguments and concatenates them together with a single space as the delimiter.
		public Person(String... names) {
			System.out.println("Varargs ctor, names=" + Arrays.asList(names));
			this.name = Arrays.stream(names).collect(Collectors.joining(" "));
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}