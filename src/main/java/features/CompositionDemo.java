/**
 * 
 */
package features;

/**
 * @author ishaqkhan
 *
 */
public class CompositionDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(6%1);
	}
	
	//Exercise 5-30. Triangle numbers that are perfect squares
	public static boolean isPerfect(int x) {
		return Math.sqrt(x)%1==0;
	}
	
	public static boolean isTriangular(int x) {
		double val = (Math.sqrt(8 * x + 1) - 1)/2;
		return val % 1 == 0;
	}

}
