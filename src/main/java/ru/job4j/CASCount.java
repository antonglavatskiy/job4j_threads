package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final static Integer STARTING_COUNT = 0;
    private final AtomicReference<Integer> count = new AtomicReference<>(STARTING_COUNT);

    public void increment() {
        Integer current;
        int rsl;
        do {
            current = count.get();
            rsl = current + 1;
        } while (!count.compareAndSet(current, rsl));
    }

    public int get() {
        return count.get();
    }
}
