/**
 * 
 */
package features;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ishaqkhan
 *
 */
public class ImplementPredicate {

	public static final Predicate<String> LENGTH_FIVE = (s -> s.length() == 5);
	public static final Predicate<String> STARTS_WITH_Y = (s -> s.startsWith("Y"));
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	//Exercise 2-7. Finding strings of a given length
	public static String getNamesOfLength(int length, String... names) { 
		return Arrays.stream(names).filter( s -> s.length() == length).collect(Collectors.joining(", "));
	}
	
	//Exercise 2-8. Finding strings that start with a given string
	public static String getNamesStartingWith(String s, String... names) {
		return Arrays.stream(names).filter(n -> n.startsWith(s)).collect(Collectors.joining(", "));
	}
	
	//Exercise 2-9. Finding strings that satisfy an arbitrary predicate
	public String getNamesSatisfyingCondition(Predicate<String> condition, String... names) {
		
		return Arrays.stream(names).filter(condition).collect(Collectors.joining(", "));
	}

	//Exercise 3-32. Doubling integers,  ltering, and summing
	public int sumDoublesDivisibleBy3(int start, int end) { 
		return IntStream.rangeClosed(start, end)
			.map(n->n*2)
			.filter(n->n%3==0)
			.sum();
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
