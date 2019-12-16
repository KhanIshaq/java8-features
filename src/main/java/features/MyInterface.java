package features;

@FunctionalInterface
public interface MyInterface {
	int myMethod();
	// int myOtherMethod(); // If we add this it will no longer be Functional Interface
	
	
	//Can have default & static method implementation
	default String sayHello() {
		return "Hello, World!";
	}

	static void myStaticMethod() {
		System.out.println("I'm a static method in an interface");
	}

}