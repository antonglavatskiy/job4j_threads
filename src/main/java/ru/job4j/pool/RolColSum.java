package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    private static Sums getSums(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix[index].length; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }
        return new Sums(rowSum, colSum);
    }

    private static CompletableFuture<Sums> getTaskSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(
                () -> getSums(matrix, index)
        );
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = getSums(matrix, i);
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = getTaskSums(matrix, i).get();
        }
        return rsl;
    }
}
