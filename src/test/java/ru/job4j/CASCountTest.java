package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenWorkOneThread() {
        CASCount test = new CASCount(0);
        test.increment();
        test.increment();
        assertThat(test.get()).isEqualTo(2);
    }

    @Test
    public void whenWorkConcurrentTwoThreads() throws InterruptedException {
        CASCount test = new CASCount(0);
        Runnable work = () -> {
            for (int i = 0; i < 5; i++) {
                test.increment();
            }
        };
        Thread first = new Thread(work);
        Thread second = new Thread(work);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(test.get()).isEqualTo(10);
    }
}