/**
 * 
 */
package features;

/**
 * @author ishaqkhan
 *
 */
public class Actor {

	private String name;
	private String role;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Actor(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}
	@Override
	public String toString() {
		return "Actor [name=" + name + ", role=" + role + "]";
	}
	
	
}
