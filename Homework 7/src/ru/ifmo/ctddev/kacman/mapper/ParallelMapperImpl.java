package ru.ifmo.ctddev.kacman.mapper;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.Queue;

/**
 * An {@link info.kgeorgiy.java.advanced.mapper.ParallelMapper} implementation.
 * <p>
 * Implements <code>map</code> function from {@link info.kgeorgiy.java.advanced.mapper.ParallelMapper}
 * in order to implement any function from {@link ru.ifmo.ctddev.kacman.mapper.IterativeParallelism}.
 *
 * @see ru.ifmo.ctddev.kacman.mapper.IterativeParallelism
 * @see info.kgeorgiy.java.advanced.mapper.ParallelMapper
 * @author Alexey Katsman
 */
public class ParallelMapperImpl implements ParallelMapper {

    private class PoolWorker implements Runnable {
        private final TaskQueue taskQueue;
        PoolWorker(TaskQueue taskQueue) {
            this.taskQueue = taskQueue;
        }
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    taskQueue.getNextTask().process();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private class Task<T, R> {
        private T argument;
        private Function<? super T, ? extends R> function;
        private R result;
        Task(T argument, Function<? super T, ? extends R> function) {
            this.argument = argument;
            this.function = function;
        }
        public synchronized void process() {
            result = function.apply(argument);
            notifyAll();
        }
        public synchronized R getResult() throws InterruptedException {
            while (result == null) {
                wait();
            }
            return result;
        }
    }

    private class TaskQueue {
        private final Queue<Task<?, ?>> taskQueue;
        TaskQueue() {
            taskQueue = new LinkedList<>();
        }
        public synchronized void addTask(Task<?, ?> task) {
            taskQueue.add(task);
            notifyAll();
        }
        public synchronized Task<?, ?> getNextTask() throws InterruptedException {
            while (taskQueue.isEmpty()) {
                wait();
            }
            return taskQueue.poll();
        }
    }

    private List<Thread> rooms;
    private TaskQueue taskQueue;

    /**
     * Creates required number of threads.
     *
     * @param threads number of threads
     */
    public ParallelMapperImpl(int threads) {
        rooms = new ArrayList<>();
        taskQueue = new TaskQueue();
        for (int i = 0; i < threads; i++) {
            Thread room = new Thread(new PoolWorker(taskQueue));
            rooms.add(room);
            room.start();
        }
    }

    /**
     * For the given list of elements we apply function to it and return result list.
     *
     * @param function function applied to the list
     * @param list list of elements
     * @param <T> given type
     * @param <R> result type
     * @return list of elements after applying given function to it
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> list) throws InterruptedException {
        List<Task<? super T, ? extends R>> tasks = new ArrayList<>();
        for (T arg : list) {
            Task<? super T, ? extends R> task = new Task<>(arg, function);
            taskQueue.addTask(task);
            tasks.add(task);

        }
        List<R> totalResult = new ArrayList<>();
        for (Task<? super T, ? extends R> task : tasks) {
            totalResult.add(task.getResult());
        }
        return totalResult;
    }

    /**
     * Closes all running threads.
     *
     * @throws InterruptedException if error occurs
     */
    @Override
    public void close() throws InterruptedException {
        rooms.forEach(java.lang.Thread::interrupt);
    }
}