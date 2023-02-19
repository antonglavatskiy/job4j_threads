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

    private int linearSearch() {
        int rsl = -1;
        for (int index = from; index <= to; index++) {
            if (value.equals(array[index])) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, value, from, middle);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, value, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    public static <T> int indexOf(T[] array, T value) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchIndex<>(array, value, 0, array.length - 1));
    }
}
