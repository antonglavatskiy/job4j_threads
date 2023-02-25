package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    public void whenSum() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        Sums[] rsl = RolColSum.sum(matrix);
        assertThat(rsl[0].rowSum()).isEqualTo(3);
        assertThat(rsl[0].colSum()).isEqualTo(4);
        assertThat(rsl[1].rowSum()).isEqualTo(7);
        assertThat(rsl[1].colSum()).isEqualTo(6);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl[0].rowSum()).isEqualTo(3);
        assertThat(rsl[0].colSum()).isEqualTo(4);
        assertThat(rsl[1].rowSum()).isEqualTo(7);
        assertThat(rsl[1].colSum()).isEqualTo(6);
    }

}