package features;

import java.util.Optional;

//Exercise 6-12. Using Optional in a DAO layer
public class Department {
	private Manager boss;

	//Might be null, so wrap getter return in an Optional, but not setter
	public Optional<Manager> getBoss() {
		return Optional.ofNullable(boss);
	}

	//Never wrap the arguments of setters with Optionals
	public void setBoss(Manager boss) {

		this.boss = boss;
	}

	@Override
	public String toString() {
		return "Department [boss=" + boss + "]";
	}
	
	
}