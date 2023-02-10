package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer current;
        Integer rsl;
        do {
            current = count.get();
            rsl = ++current;
        } while (!count.compareAndSet(current, rsl));
    }

    public int get() {
        return count.get();
    }
}
