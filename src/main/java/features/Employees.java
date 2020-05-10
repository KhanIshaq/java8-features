package features;
//Exercise 5-14. Employee interface with a default method
public interface Employees {
	String getFirst();

	String getLast();

	void convertCaffeineToCodeForMoney();

	default String getName() {
		return String.format("%s %s", getFirst(), getLast());
	}
}