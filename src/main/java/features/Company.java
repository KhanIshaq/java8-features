package features;

//Exercise 5-13.  e Company interface with a default method
public interface Company { 
	default String getName() {
			return "Initech"; 
	}
    // other methods
}
