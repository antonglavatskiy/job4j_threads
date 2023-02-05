package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void whenWorkTwoThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    int value = 1;
                    queue.offer(value);
                    System.out.println(Thread.currentThread().getName() + " add value " + value);
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    int value = queue.poll();
                    System.out.println(Thread.currentThread().getName() + " consumes " + value);
                },
                "Consumer"
        );
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
    }

}