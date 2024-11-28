package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Matrix {

    // Basic matrix multiplication
    public static double[][] basicMatrixMultiplication(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }

    // Parallel matrix multiplication with improved workload distribution
    public static double[][] parallelMatrixMultiplication(double[][] matrixA, double[][] matrixB, int numThreads) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        // Adjust the number of threads based on matrix size
        if (rowsA * colsB < 1000000) {  // For small matrices, use fewer threads
            numThreads = Math.min(numThreads, 2); // Limit to 2 threads for smaller matrices
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Determine chunk size (rows per thread)
        int chunkSize = (rowsA + numThreads - 1) / numThreads;

        // Submit tasks to process chunks of rows
        for (int t = 0; t < numThreads; t++) {
            final int startRow = t * chunkSize;
            final int endRow = Math.min(startRow + chunkSize, rowsA);

        // Iterate over the assigned chunk
            executor.submit(() -> {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < colsB; j++) {
                        for (int k = 0; k < colsA; k++) {
                            result[i][j] += matrixA[i][k] * matrixB[k][j];
                        }
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Parallel matrix multiplication
    public static double[][] parallelMultiplication(double[][] matrixA, double[][] matrixB, int numThreads) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        // Adjust the number of threads based on matrix size
        if (rowsA * colsB < 1000000) {  // For small matrices, use fewer threads
            numThreads = Math.min(numThreads, 2); // Limit to 2 threads for smaller matrices
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Split the workload by rows
        for (int i = 0; i < rowsA; i++) {
            final int row = i;
            executor.submit(() -> {
                for (int j = 0; j < colsB; j++) {
                    for (int k = 0; k < colsA; k++) {
                        result[row][j] += matrixA[row][k] * matrixB[k][j];
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }



}
