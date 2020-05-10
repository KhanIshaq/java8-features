package features;

//Exercise 5-15. First attempt at CompanyEmployee (WON’T COMPILE)
public class CompanyEmployees implements Company, Employees {
	//FIRST ATTEMPT
	private String first;
	private String last;

	@Override
	public void convertCaffeineToCodeForMoney() {
		System.out.println("Coding...");
	}

	@Override
	public String getFirst() {
		return first;
	}

	@Override
	public String getLast() {
		return last;
	}
	//FIRST ATTEMPT
	
	//SECOND ATTEMPT = WORKING
	//Implement getName
	//Access default implementations using super
	@Override
	public String getName() {
		return String.format("%s working for %s", Employees.super.getName() + ", " + Company.super.getName());
	}
	//SECOND ATTEMPT = WORKING

//	@Override
//	public String getName() {
//		return Company.super.getName();
//	}

//	@Override
//	public String getName() {
//		return Employees.super.getName();
//	}
	
	
}