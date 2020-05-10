/**
 * 
 */
package features;

/**
 * @author ishakhan
 *
 */
public interface IEmployee {

	String getFirst();
	
	String getLast();
	
	void convertCaffeineToCodeForMoney();
	
	//Default method with an implementation
	default String getName() {
		return String.format("%s %s", getFirst(), getLast());
	}
	
//	For example, java.util.Collection now contains the following default methods:
//		  default boolean
//		default Stream<E>
//		default Stream<E>
//		default Spliterator<E> spliterator()
}
