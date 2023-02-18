package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (to - from <= 10) {
            for (int index = from; index <= to; index++) {
                if (value.equals(array[index])) {
                    rsl = index;
                }
            }
            return rsl;
        }
        int middle = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, value, from, middle);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, value, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int leftRsl = leftSearch.join();
        int rightRsl = rightSearch.join();
        if (leftRsl != -1) {
            rsl = leftRsl;
        }
        if (rightRsl != -1) {
            rsl = rightRsl;
        }
        return rsl;
    }

    public static <T> int indexOf(T[] array, T value) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchIndex<>(array, value, 0, array.length - 1));
    }
}
