package features;
/**
 * 
 * @author nsa
 *
 */

public class Order { 
	
	private int id;
	public Order(int id) { 
		this.id = id;
	}
	public int getId() { 
		return id; 
	}
	@Override
	public String toString() {
		return "Order [id=" + id + "]";
	}
	
	
}