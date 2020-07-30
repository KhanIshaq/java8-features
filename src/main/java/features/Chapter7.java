/**
 * 
 */
package features;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author ishaqkhan
 *
 */
public class Chapter7 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		//File I/O
		//java.nio.file
		//nonblocking (or “new”) input/output package,
		
		//New added in Java 8
		//java.nio.file.Files
		//java.nio.file.DirectoryStream 
		
		//Static methods of --> java.nio.file.Files
		//Method			Return Type
		//lines			Stream<String>
		//list			Stream<Path>
		//walk			Stream<Path>
		//find			Stream<Path>
		//7.1 Process Files
		//Use Case
		//You want to process contents of a text file using stream
		
		//Exercise 7-1. Finding the 10 longest words in the java8 dictionary
		// java.io.UncheckedIOException: java.io.IOException: Is a directory
		try (Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Documents/eclipseWorkspace_2018_09/java8/chapter7.txt"))) { 
			
			lines.filter(s -> s.length() > 20)
		         .sorted(Comparator.comparingInt(String::length).reversed())
		         .limit(10)
		         .forEach(w -> System.out.printf("%s (%d)%n", w, w.length()));
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		//Streams and AutoCloseable
		//Stream interface extends BaseStream, which is a subinterface of AutoCloseable
		//When exiting the block, the system will automatically invoke the close method, which will not only close the stream, 
		//it will also call any close handlers from the stream pipeline to release any resources.
		//try-with-resources ensures that the dictio‐ nary file is also closed

		
		//Exercise 7-3. Determining number of words of each length
		try (Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Documents/eclipseWorkspace_2018_09/java8/chapter7.txt"))) { 
			lines.filter(s -> s.length() > 20)
		         .collect(Collectors.groupingBy(String::length, Collectors.counting()))
		         .forEach((len, num) -> System.out.println(len + ": " + num));
			//Collectors.counting as a downstream collector
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		//to create a Map where the keys are the word lengths and values are the number of words of each length
		
		//Exercise 7-4. Number of words of each length, in descending order
		try(Stream<String> lines = Files.lines(Paths.get("/Users/nsa/Documents/eclipseWorkspace_2018_09/java8/chapter7.txt"))){
			
			Map<Integer, Long> map = lines.filter(s -> s.length()>20)
					.collect(Collectors.groupingBy(String::length, Collectors.counting()));
			
			map.entrySet().stream()
			.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
			.forEach(e -> System.out.printf("Length %d: %d words%n", e.getKey(), e.getValue()));
					
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//7.2 Retrieving Files as a Stream
		//Use Case
		//You want to process all the files in a directory as a Stream.
		//try-with-resources block
		//Exercise 7-6. Using Files.list(path)
		System.out.println("Joining with more");
		try(Stream<Path> list = Files.list(Paths.get("src","main","java"))){
			list.forEach(System.out::println);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Without Joining");
		try(Stream<Path> list = Files.list(Paths.get("src/main/java"))){
			list.forEach(p -> System.out.println(p.getFileName()));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("------DirectoryNames------");
		try(Stream<Path> list = Files.list(Paths.get("src/main/java"))){
			list.forEach(System.out::println);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//public static Stream<Path> list(Path dir) throws IOException
		//NotDirectoryException
		
		//weakly consistent & thread safe
		
		//7.3 Walking the Filesystem
		//Use Case
		//You need to perform a depth-first traversal of the filesystem.
		//Use static Files.walk method
		
		//public static Stream<Path> walk(Path start, FileVisitOption... options)throws IOException
		
		//Exercise 7-7. Walking the tree
		System.out.println("------Walking------");
		try (Stream<Path> paths = Files.walk(Paths.get("src/main/java"))) { 
			paths.forEach(System.out::println);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		//FileVisitOption is an enum, added in Java 1.7, whose only defined value is FileVisitOption.FOLLOW_LINKS
		//If a cycle is detected, a FileSystemLoopException is thrown.
		//public static Stream<Path> walk(Path start, int maxDepth, FileVisitOption... options) throws IOException
		// maxDepth = 0 means use only starting level.
		//The version of this method without a maxDepth parameter uses a value of Integer.MAX_VALUE, meaning all levels should be visited.
				
		
		//7.4 Searching the Filesystem
		
		//Use Case
		//You want to find files in a file tree that satisfy given properties
		//public static Stream<Path> find(Path start, int maxDepth, BiPredicate<Path, BasicFileAttributes> matcher, FileVisitOption... options) throws IOException
		
		System.out.println(">----Starting-Non-Directory----<");
		//Exercise 7-8. Finding the nondirectory  files in the fileio package
		try(Stream<Path> paths =  Files.find(Paths.get("src/main/java"),
				Integer.MAX_VALUE,
				(path, attributes) -> !attributes.isDirectory() && path.toString().contains("fileio"))){
			
			paths.forEach(System.out::println);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(">----Ended-Non-Directory----<");
		
		
	}

}
