package features;

// Exercise 1-24. Extending a functional interface—no longer functional
// It cannot, however, be the target of a lambda expression.
public interface MyChildInterface extends MyInterface {
	int anotherMethod();
}