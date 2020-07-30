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
	
	/**
	 * 	Java Micro-benchmarking Harness (JMH) Framework
	 * 	http://openjdk.java.net/projects/code-tools/jmh/
	 *  # JMH version: 1.21
		# VM version: JDK 1.8.0_252, OpenJDK 64-Bit Server VM, 25.252-b09
		# VM invoker: /Users/nsa/.sdkman/candidates/java/8.0.252.hs-adpt/jre/bin/java
		# VM options: -Xms4G -Xmx4G
		# Warmup: 5 iterations, 10 s each
		# Measurement: 5 iterations, 10 s each
		# Timeout: 10 min per iteration
		# Threads: 1 thread, will synchronize iterations
		# Benchmark mode: Average time, time/op
		# Benchmark: features.DoublingDemo.doubleAndSumParallel
		
		# Run progress: 0.00% complete, ETA 00:06:40
		# Fork: 1 of 2
		# Warmup Iteration   1: 206.018 ms/op
		# Warmup Iteration   2: 205.453 ms/op
		# Warmup Iteration   3: 204.886 ms/op
		# Warmup Iteration   4: 205.650 ms/op
		# Warmup Iteration   5: 205.758 ms/op
		Iteration   1: 205.246 ms/op
		Iteration   2: 205.907 ms/op
		Iteration   3: 205.089 ms/op
		Iteration   4: 205.358 ms/op
		Iteration   5: 205.528 ms/op
		
		# Run progress: 25.00% complete, ETA 00:05:04
		# Fork: 2 of 2
		# Warmup Iteration   1: 205.259 ms/op
		# Warmup Iteration   2: 206.065 ms/op
		# Warmup Iteration   3: 204.791 ms/op
		# Warmup Iteration   4: 205.572 ms/op
		# Warmup Iteration   5: 205.632 ms/op
		Iteration   1: 205.474 ms/op
		Iteration   2: 205.276 ms/op
		Iteration   3: 205.534 ms/op
		Iteration   4: 205.238 ms/op
		Iteration   5: 205.570 ms/op
		
		
		Result "features.DoublingDemo.doubleAndSumParallel":
		  205.422 ±(99.9%) 0.351 ms/op [Average]
		  (min, avg, max) = (205.089, 205.422, 205.907), stdev = 0.232
		  CI (99.9%): [205.071, 205.773] (assumes normal distribution)
		
		
		# JMH version: 1.21
		# VM version: JDK 1.8.0_252, OpenJDK 64-Bit Server VM, 25.252-b09
		# VM invoker: /Users/nsa/.sdkman/candidates/java/8.0.252.hs-adpt/jre/bin/java
		# VM options: -Xms4G -Xmx4G
		# Warmup: 5 iterations, 10 s each
		# Measurement: 5 iterations, 10 s each
		# Timeout: 10 min per iteration
		# Threads: 1 thread, will synchronize iterations
		# Benchmark mode: Average time, time/op
		# Benchmark: features.DoublingDemo.doubleAndSumSequential
		
		# Run progress: 50.00% complete, ETA 00:03:22
		# Fork: 1 of 2
		# Warmup Iteration   1: 616.017 ms/op
		# Warmup Iteration   2: 615.849 ms/op
		# Warmup Iteration   3: 614.281 ms/op
		# Warmup Iteration   4: 615.237 ms/op
		# Warmup Iteration   5: 614.776 ms/op
		Iteration   1: 614.760 ms/op
		Iteration   2: 618.708 ms/op
		Iteration   3: 614.938 ms/op
		Iteration   4: 615.139 ms/op
		Iteration   5: 613.864 ms/op
		
		# Run progress: 75.00% complete, ETA 00:01:42
		# Fork: 2 of 2
		# Warmup Iteration   1: 615.516 ms/op
		# Warmup Iteration   2: 618.072 ms/op
		# Warmup Iteration   3: 617.549 ms/op
		# Warmup Iteration   4: 618.767 ms/op
		# Warmup Iteration   5: 618.220 ms/op
		Iteration   1: 616.872 ms/op
		Iteration   2: 613.759 ms/op
		Iteration   3: 614.875 ms/op
		Iteration   4: 615.389 ms/op
		Iteration   5: 616.740 ms/op
		
		
		Result "features.DoublingDemo.doubleAndSumSequential":
		  615.504 ±(99.9%) 2.299 ms/op [Average]
		  (min, avg, max) = (613.759, 615.504, 618.708), stdev = 1.521
		  CI (99.9%): [613.205, 617.803] (assumes normal distribution)
		
		
		# Run complete. Total time: 00:06:53
		
		REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
		why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
		experiments, perform baseline and negative tests that provide experimental control, make sure
		the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
		Do not assume the numbers tell you what you want them to tell.
		
		Benchmark                            Mode  Cnt    Score   Error  Units
		DoublingDemo.doubleAndSumParallel    avgt   10  205.422 ± 0.351  ms/op
		DoublingDemo.doubleAndSumSequential  avgt   10  615.504 ± 2.299  ms/op
	 */
}
