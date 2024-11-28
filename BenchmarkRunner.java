package org.example;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {

        // Get and print available CPU cores before running the benchmark
        int availableCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available CPU cores: " + availableCores);

        // Set up benchmarking options
        Options options = new OptionsBuilder()
                .include(MatrixMultiplicationBenchmark.class.getSimpleName()) // Reference your benchmark class
                .forks(1) // Number of forks (separate JVM instances)
                .warmupIterations(3) // Warmup iterations before actual benchmarking
                .measurementIterations(5) // Number of iterations to measure
                .build();

        // Run the benchmark
        new Runner(options).run();

    }
}


