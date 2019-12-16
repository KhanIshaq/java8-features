/**
 * 
 */
package features;

/**
 * @author nsa
 *
 */
public class RunnableDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Runnable annonymous inner class");
			}
		}).start();
		
		new Thread(()-> System.out.println("Lambda Expression")).start();

		Runnable r = () -> System.out.println("lambda expression implementing the run method");
		new Thread(r).start();
	}

}
