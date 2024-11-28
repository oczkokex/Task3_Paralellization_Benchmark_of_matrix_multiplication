package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)  // Measure average execution time
@OutputTimeUnit(TimeUnit.MILLISECONDS)  // Report results in milliseconds
@State(Scope.Thread)  // Each thread gets its own state
public class MatrixMultiplicationBenchmark {

    @Param({"500","1000","2000","3000","5000"})
    private int size;

    private double[][] matrixA;
    private double[][] matrixB;

    @Setup(Level.Invocation)
    public void setup() {
        matrixA = generateMatrix(size, size);
        matrixB = generateMatrix(size, size);
    }

    // Benchmark for basic matrix multiplication
    /*
    @Benchmark
    public double[][] basicMatrixMultiplication() {
        return Matrix.basicMatrixMultiplication(matrixA, matrixB);
    }
    */

    // Benchmark for parallel matrix multiplication
    @Benchmark
    public double[][] parallelMatrixMultiplication() {
        return Matrix.parallelMatrixMultiplication(matrixA, matrixB, Runtime.getRuntime().availableProcessors());
    }

    // Generate a random matrix
    private double[][] generateMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = ThreadLocalRandom.current().nextDouble(1.0, 100.0);
            }
        }
        return matrix;
    }

}
