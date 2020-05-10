package features;

import java.util.Optional;

//Exercise 6-16. A company may have a department (only one, for simplicity)
public class CompanyC {
	private Department department;

	public Optional<Department> getDepartment() {
		return Optional.ofNullable(department);
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}