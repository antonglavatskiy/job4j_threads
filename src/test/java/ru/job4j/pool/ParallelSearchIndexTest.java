package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ParallelSearchIndexTest {
    @Test
    public void whenSearch() {
        Integer[] smallArray = {0, 1, 2, 3};
        Integer[] largeArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int value = 3;
        assertThat(ParallelSearchIndex.indexOf(smallArray, value)).isEqualTo(3);
        assertThat(ParallelSearchIndex.indexOf(largeArray, value)).isEqualTo(3);
    }


    @Test
    public void whenSearchIntegerType() {
        Integer[] array = {0, 1, 2, 3};
        int value = 2;
        assertThat(ParallelSearchIndex.indexOf(array, value)).isEqualTo(2);
    }

    @Test
    public void whenSearchStringType() {
        String[] array = {"0", "1", "2", "3"};
        String value = "1";
        assertThat(ParallelSearchIndex.indexOf(array, value)).isEqualTo(1);
    }

    @Test
    public void whenIndexNotFound() {
        String[] array = {"0", "1", "2", "3"};
        String value = "4";
        assertThat(ParallelSearchIndex.indexOf(array, value)).isEqualTo(-1);
    }
}