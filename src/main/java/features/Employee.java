package features;

public class Employee {
	private String name;
	private Integer salary;
	private String department;
	
	
	
	public Employee(String name, Integer salary, String department) {
		super();
		this.name = name;
		this.salary = salary;
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}