package features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
/**
 * 
 * @author ishaqkhan
 *
 */
//Exercise 5-4. A trivial Swing user interface
public class MyGUI extends JFrame {
	private JTextField name = new JTextField("Please enter your name");
	private JTextField response = new JTextField("Greeting");
	private JButton button = new JButton("Say Hi");

	public MyGUI() {
		// ... unrelated GUI setup code ...
		String greeting = "Hello, %s!"; 
		//ActionListener is an interface implemented as in annonymous inner class
//		button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) { 
//				response.setText(String.format(greeting, name.getText())); 
//						// greeting = "Anything else";
//			}
//		});
		
		//Exercise 5-5. Lambda expression for the listener
		//greetings must be effectively final, because any code trying to change the local variable will not compile
		button.addActionListener(e -> response.setText(String.format(greeting, name.getText())));
	}
}
