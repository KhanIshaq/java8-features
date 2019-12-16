/**
 * 
 */
package features;

/**
 * @author nsa
 *
 */

//Exercise 1-22. A Palindrome Checker interface
@FunctionalInterface
public interface PalindromeChecker {
	//Single abstract method (SAM)
	boolean isPalindrome(String s);
}
