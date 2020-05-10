package features;
//Exercise 5-39. A functional interface based on Function that throws Exception
@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> { 
	R apply(T t) throws E;
}
