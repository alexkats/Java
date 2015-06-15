package ru.ifmo.ctddev.kacman.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link info.kgeorgiy.java.advanced.concurrent.ListIP} implementation.
 * <p>
 * For the given number of threads it will find minimum or maximum, or
 * check if all or any elements satisfy to given predicate. Also it can
 * convert list of elements to {@link String}, filter elements which satisfy
 * given predicate or apply function to all elements in list.
 *
 * @author Alexey Katsman
 */

@SuppressWarnings("unchecked")
public class IterativeParallelism implements ListIP {

    private <T> List<List<? extends T>> parts(int threads, List<? extends T> list) {
        if (threads < 1) {
            threads = 1;
        } else if (threads > list.size()) {
            threads = list.size();
        }
        List<List<? extends T>> parts = new ArrayList<>();
        final int threadPart = list.size() / threads;
        for (int l = 0; l < list.size(); l += threadPart) {
            int r = Math.min(l + threadPart, list.size());
            parts.add(list.subList(l, r));
        }
        return parts;

    }

    private static class MyActionListPredicate<E> implements Runnable {
        private List<E> result;
        private List<E> list;
        Predicate<E> predicate;

        private MyActionListPredicate(List<E> list, Predicate<E> predicate) {
            this.list = list;
            this.predicate = predicate;
        }

        public List<E> getResult() {
            return result;
        }

        @Override
        public void run() {
            result = list.stream().filter(predicate::test).collect(Collectors.toList());
        }
    }

    private static class MyActionString<E> implements Runnable {
        private StringBuilder result;
        private List<E> list;

        private MyActionString(List<E> list) {
            this.list = list;
        }

        public StringBuilder getResult() {
            return result;
        }

        @Override
        public void run() {
            StringBuilder locRes = new StringBuilder();
            list.forEach(locRes::append);
            result = locRes;
        }
    }

    /**
     * For the given list of elements converts it to {@link String}.
     *
     * @param threads number of threads
     * @param list    list of elements
     * @return string representation of list
     * @throws InterruptedException if error occurs
     */
    @Override
    public String concat(int threads, List<?> list) throws InterruptedException {
        List<List<?>> parts = parts(threads, list);

        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyActionString[] actions = new MyActionString[threads];
        for (int i = 0; i < threads; i++) {
            actions[i] = new MyActionString(parts.get(i));
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }
        StringBuilder commonResult = new StringBuilder();
        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            commonResult.append(actions[i].getResult());
        }
        return commonResult.toString();


    }

    /**
     * For the given list of elements filter them to choose those, which
     * satisfy given predicate.
     *
     * @param threads   number of threads
     * @param list      list of elements
     * @param predicate predicate we need to satisfy
     * @param <T>       given type
     * @return list of elements satisfying predicate
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T> List<T> filter(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);

        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyActionListPredicate[] actions = new MyActionListPredicate[threads];
        for (int i = 0; i < threads; i++) {
            actions[i] = new MyActionListPredicate(parts.get(i), predicate);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }
        List<T> localResult = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            localResult.addAll(actions[i].getResult());
        }
        return localResult;

    }

    private static class MyActionListFunction<E, R> implements Runnable {
        private List<R> result;
        private List<E> list;
        Function<E, R> function;

        private MyActionListFunction(List<E> list, Function<E, R> function) {
            this.list = list;
            this.function = function;
        }

        public List<R> getResult() {
            return result;
        }

        @Override
        public void run() {
            result = list.stream().map(function::apply).collect(Collectors.toList());
        }
    }

    /**
     * For the given list of elements we apply function to it and return result list.
     *
     * @param threads  number of threads
     * @param list     list of elements
     * @param function function applied to the list
     * @param <T>      given type
     * @param <U>      result type
     * @return list of elements after applying given function to it
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T, U> List<U> map(int threads, List<? extends T> list, Function<? super T, ? extends U> function) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);

        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyActionListFunction[] actions = new MyActionListFunction[threads];
        for (int i = 0; i < threads; i++) {
            actions[i] = new MyActionListFunction(parts.get(i), function);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }
        List<U> localResult = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            localResult.addAll(actions[i].getResult());
        }
        return localResult;

    }


    private static class MyAction<T> implements Runnable {
        private int type;
        private T result;
        private List<? extends T> list;
        private Comparator<? super T> comparator;

        private MyAction(List<? extends T> list, Comparator<? super T> comparator, int type) {
            this.list = list;
            this.comparator = comparator;
            this.type = type;
        }

        public T getResult() {
            return result;
        }

        @Override
        public void run() {
            if (type == 0) {
                result = Collections.min(list, comparator);
            } else if (type == 1) {
                result = Collections.max(list, comparator);
            }
        }
    }

    /**
     * For the given list of elements it returns minimum of them.
     *
     * @param threads    number of threads
     * @param list       list of elements
     * @param comparator comparator we have to compare elements with
     * @param <T>        given type
     * @return minimum of all elements
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);

        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyAction[] actions = new MyAction[threads];

        for (int i = 0; i < threads; i++) {
            actions[i] = new MyAction<>(parts.get(i), comparator, 0);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }

        T commonResult = list.get(0);
        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T localResult = (T) actions[i].getResult();
            if (comparator.compare(commonResult, localResult) > 0) {
                commonResult = localResult;
            }
        }
        return commonResult;

    }

    /**
     * For the given list of elements it returns maximum of them.
     *
     * @param threads    number of threads
     * @param list       list of elements
     * @param comparator comparator we have to compare elements with
     * @param <T>        given type
     * @return maximum of all elements
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);
        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyAction[] actions = new MyAction[threads];
        for (int i = 0; i < threads; i++) {
            actions[i] = new MyAction<>(parts.get(i), comparator, 1);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }

        T commonResult = list.get(0);
        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            T localResult = (T) actions[i].getResult();
            if (comparator.compare(commonResult, localResult) < 0) {
                commonResult = localResult;
            }
        }
        return commonResult;

    }

    private static class MyActionBool<T> implements Runnable {
        private boolean result;
        private List<? extends T> list;
        private int type;
        Predicate<? super T> predicate;

        private MyActionBool(List<? extends T> list, Predicate<? super T> predicate, int type) {
            this.list = list;
            this.predicate = predicate;
            this.type = type;
        }

        public boolean getResult() {
            return result;
        }

        @Override
        public void run() {
            for (T aList : list) {
                if (type == 0) {
                    if (predicate.test(aList)) {
                        result = true;
                        return;
                    }
                } else {
                    if (!predicate.test(aList)) {
                        result = false;
                        return;
                    }
                }
            }
            result = type != 0;
        }
    }

    /**
     * For the given list of elements it checks whether all
     * elements from it satisfy given predicate.
     *
     * @param threads   number of threads
     * @param list      list of elements
     * @param predicate predicate we need to satisfy
     * @param <T>       given type
     * @return true if all elements satisfy given predicate
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T> boolean all(int threads,
                           List<? extends T> list,
                           Predicate<? super T> predicate) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);
        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyActionBool[] actions = new MyActionBool[threads];

        for (int i = 0; i < threads; i++) {
            actions[i] = new MyActionBool<>(parts.get(i), predicate, 1);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }

        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean localResult = actions[i].getResult();
            if (!localResult)
                return false;
        }
        return true;

    }

    /**
     * For the given list of elements it checks whether any
     * element from it satisfies given predicate.
     *
     * @param threads   number of threads
     * @param list      list of elements
     * @param predicate predicate we need to satisfy
     * @param <T>       given type
     * @return true if any element satisfies given predicate
     * @throws InterruptedException if error occurs
     */
    @Override
    public <T> boolean any(int threads,
                           List<? extends T> list,
                           Predicate<? super T> predicate) throws InterruptedException {
        List<List<? extends T>> parts = parts(threads, list);
        threads = parts.size();
        final Thread workers[] = new Thread[threads];
        final MyActionBool[] actions = new MyActionBool[threads];

        for (int i = 0; i < threads; i++) {
            actions[i] = new MyActionBool<>(parts.get(i), predicate, 0);
            workers[i] = new Thread(actions[i]);
            workers[i].start();
        }

        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean localResult = actions[i].getResult();
            if (localResult)
                return true;
        }
        return false;

    }
}
