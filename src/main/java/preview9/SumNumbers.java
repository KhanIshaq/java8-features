package preview9;

//Exercise 10-8. Private method in an interface
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public interface SumNumbers {
	default int addEvens(int... nums) {
		return add(n -> n % 2 == 0, nums);
	}

	default int addOdds(int... nums) {
		return add(n -> n % 2 != 0, nums);
	}

	//private int add(IntPredicate predicate, int... nums) {
	default int add(IntPredicate predicate, int... nums) {
		return IntStream.of(nums).filter(predicate).sum();
	}
}