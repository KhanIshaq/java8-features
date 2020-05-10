/**
 * 
 */
package features;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ishaqkhan
 *
 */
public class UnmodifiableSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
//	@SafeVarargs
//	public final <T> List<T> createImmutableList(T... elements) { 
//		return Arrays.stream(elements)
//	        .collect(collectingAndThen(toList(),
//	                    Collections::unmodifiableList));
//	}
//	@SafeVarargs
//	public final <T> Set<T> createImmutableSet(T... elements) { 
//		return Arrays.stream(elements)
//	        .collect(collectingAndThen(toSet(),
//	                    Collections::unmodifiableSet));
//	}
	
	//“Finisher” wraps the generated collections
	//The Collectors.collectingAndThen method takes two arguments: a downstream Collector and a Function called a  finisher.
	//The idea is to stream the input elements and then collect them into a List or Set, and then the unmodifiable function wraps the resulting collection.

}
