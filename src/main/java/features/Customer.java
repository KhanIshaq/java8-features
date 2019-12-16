/**
 * 
 */
package features;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nsa
 *
 */
public class Customer {

	//Exercise 3-54. A one-to-many relationship
	private String name;
	private List<Order> orders = new ArrayList<>();
	public Customer(String name) { this.name = name;
	}
	public String getName() { return name; }
	public List<Order> getOrders() { return orders; }
	public Customer addOrder(Order order) { 
		orders.add(order);
		return this;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", orders=" + orders + "]";
	} 
	
}
