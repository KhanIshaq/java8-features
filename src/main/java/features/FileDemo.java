/**
 * 
 */
package features;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ishaqkhan
 *
 */
public class FileDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File directory = new File("./src/main/java/lc/sort");
		String[] names = directory.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		});

		System.out.println(Arrays.asList(names));
		
		directory = new File("./src/main/java/lc/sort");		
		String[] n1 = directory.list((dir,name) -> name.endsWith(".java"));
		System.out.println(Arrays.asList(n1));
		
		// Lambda Expression with explicit datatypes
		String[] n2 = directory.list((File dir, String name) -> name.endsWith(".java"));
		System.out.println(Arrays.asList(n2));
		
		//Block Lambda
		String[] n3 = directory.list((dir,name) -> {
			return name.endsWith(".java");
		});
		System.out.println(Arrays.asList(n3));
		
		//Block Lambda with explicit datatypes
		String[] n4 = directory.list((File dir, String name) -> {
			return name.endsWith(".java");
		});
		System.out.println(Arrays.asList(n4));
		System.out.println("*****");
		Stream.of(3, 1, 4, 1, 5, 9).forEach(x->System.out.println(x));
		System.out.println("*****");
		Stream.of(3, 1, 4, 1, 5, 9).forEach(System.out::println);
		System.out.println("*****");
		Consumer<Integer> printer = System.out::println;
		Stream.of(3, 1, 4, 1, 5, 9).forEach(printer);
		System.out.println("*****");
		int t = (int) (Math.random()*10);
		System.out.println(t);
		
		//generate & random are static method
		Stream.generate(Math::random)
		.limit(10)
		.forEach(System.out::println);
		//forEach is instance method
		
		// equivalent to System.out::println
		Stream.of(3,2,6,1,5).forEach((x) -> System.out.println(x));
		
		// equivalent to Math::max
		//((x,y) -> Math.max(x,y));
		
		// equivalent to String::length
		Stream.of("this","how","do").map((x) -> x.length()).forEach(System.out::println);
		
		//Instance method via class name
		Stream.of("this","how","do").map(String::length).forEach(System.out::println);
		//Instance method via object reference
		
		List<String> strings =
			    Arrays.asList("this", "is", "a", "list", "of", "strings");
		
		List<String> sorted = strings.stream()
		        .sorted((s1, s2) -> s1.compareTo(s2))
		        .collect(Collectors.toList());
		System.out.println(sorted);
		
		List<String> sorted1 = strings.stream()
		        .sorted(String::compareTo)
		.collect(Collectors.toList());
		
		System.out.println(sorted1);	
		
		ArrayList<String> sorted2 = (ArrayList<String>) strings.stream()
		        .sorted(String::compareTo)
		.collect(Collectors.toList());
		
		System.out.println(sorted2);
		
		ArrayList<Widget> widgets = new ArrayList<>();
		Widget w1 = new Widget("RED", 10);
		Widget w2 = new Widget("BLUE", 20);
		Widget w3 = new Widget("RED", 30);
		widgets.add(w1);
		widgets.add(w2);
		widgets.add(w3);
		int sum = widgets.stream()
                .filter(w -> w.getColor().equals("RED"))
                .mapToInt(w -> w.getWeight())
                .sum();
		System.out.println(sum);
	}

}
