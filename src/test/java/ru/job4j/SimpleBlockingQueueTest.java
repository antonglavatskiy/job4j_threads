package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenWorkTwoThreads() throws InterruptedException {
        int size = 1;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(size);
        Thread producer = new Thread(
                () -> {
                    try {
                        int value = 1;
                        queue.offer(value);
                        System.out.println(Thread.currentThread().getName() + " add value " + value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        int value = queue.poll();
                        System.out.println(Thread.currentThread().getName() + " consumes " + value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                },
                "Consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

}