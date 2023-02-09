package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
       final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
       final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(List.of(0, 1, 2, 3, 4));
    }
}