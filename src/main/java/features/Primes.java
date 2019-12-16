/**
 * 
 */
package features;

import java.util.stream.IntStream;

/**
 * @author nsa
 *
 */
public class Primes {

	//Exercise 3-51. Prime number check
    public boolean isPrime(int num) {
    int limit = (int) (Math.sqrt(num) + 1);
    return num == 2 || num > 1 && IntStream.range(2, limit)
     .noneMatch(divisor -> num % divisor == 0);
    }
}
