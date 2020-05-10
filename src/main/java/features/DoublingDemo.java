/**
 * 
 */
package features;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author ishaqkhan
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class DoublingDemo {

	public int doubleIt(int n) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
		}
		return n * 2;
	}
	
	@Benchmark
	public int doubleAndSumSequential() { 
		return IntStream.of(3, 1, 4, 1, 5, 9)
				.map(this::doubleIt) 
				.sum();
	}
	@Benchmark
	public int doubleAndSumParallel() { 
		return IntStream.of(3, 1, 4, 1, 5, 9)
				.parallel()
				.map(this::doubleIt)
				.sum();
	}
	
	public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DoublingDemo.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
	}
	

	private static int N = 1000;
	//Exercise 9-9. Iteratively summing numbers in a loop
	public long iterativeSum() {
		long result = 0;
		for (long i = 1L; i <= N; i++) {
			result += i;
		}
		return result;
	}
	
	//Example 9-10. Summing generic streams
	public long sequentialStreamSum() {
		return Stream.iterate(1L, i -> i + 1)
				.limit(N)
				.reduce(0L, Long::sum);
	}

	public long parallelStreamSum() {
		return Stream.iterate(1L, i -> i + 1)
				.limit(N)
				.parallel()
				.reduce(0L, Long::sum);
	}
	
	//Exercise 9-11. Using LongStream
	public long sequentialLongStreamSum() {
		return LongStream.rangeClosed(1, N).sum();
	}

	public long parallelLongStreamSum() {
		return LongStream.rangeClosed(1, N).parallel().sum();
	}
}
