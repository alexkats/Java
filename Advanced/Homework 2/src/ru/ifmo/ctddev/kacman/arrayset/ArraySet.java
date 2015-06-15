package ru.ifmo.ctddev.kacman.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {
    private List<E> array;
    private boolean reversedOrder;
    private Comparator<? super E> comparator;
    private boolean hasDefaultComparator;

    private List<E> buildSet(List<E> array, Comparator<? super E> comparator) {
        if (array.size() == 0) {
            return new ArrayList<>();
        }

        List<E> res = new ArrayList<>();
        Collections.sort(array, comparator);
        E prev = array.get(0);
        res.add(prev);

        for (int i = 1; i < array.size(); i++) {
            if (comparator.compare(prev, array.get(i)) != 0) {
                res.add(array.get(i));
            }

            prev = array.get(i);
        }

        return res;
    }

    public ArraySet() {
        this.array = new ArrayList<>();
        this.comparator = null;
        this.reversedOrder = false;
        this.hasDefaultComparator = true;
    }

    private ArraySet(List<E> list, Comparator<? super E> comparator, boolean hasDefaultComparator, boolean reversedOrder, boolean alreadyExists) {
        if (alreadyExists) {
            this.array = list;
        } else {
            this.array = buildSet(list, comparator);
        }

        this.comparator = comparator;
        this.hasDefaultComparator = hasDefaultComparator;
        this.reversedOrder = reversedOrder;
    }

    private ArraySet(Collection<E> collection, Comparator<? super E> comparator, boolean reversedOrder, boolean alreadyExists) {
        if (alreadyExists) {
            this.array.addAll(collection);
        } else {
            this.array = buildSet(new ArrayList<>(collection), comparator);
        }

        this.comparator = comparator;
        this.hasDefaultComparator = false;
        this.reversedOrder = reversedOrder;
    }

    public ArraySet(Collection<E> collection, Comparator<? super E> comparator) {
        this(collection, comparator, false, false);
    }

    @SuppressWarnings("unchecked")
    private ArraySet(Collection<E> collection, boolean reversedOrder, boolean alreadyExists) {
        if (alreadyExists) {
            this.array.addAll(collection);
        } else {
            this.array = buildSet(new ArrayList<>(collection), (o1, o2) -> ((Comparable<E>) o1).compareTo(o2));
        }

        this.comparator = (o1, o2) -> ((Comparable<E>) o1).compareTo(o2);
        this.hasDefaultComparator = true;
        this.reversedOrder = reversedOrder;
    }

    public ArraySet(Collection<E> collection) {
        this(collection, false, false);
    }

    @SuppressWarnings("unchecked")
    public ArraySet(Object[] array) {
        this(Arrays.asList((E[]) array));
    }

    @SuppressWarnings("unchecked")
    public ArraySet(Object[] array, Comparator<? super E> comparator) {
        this(Arrays.asList((E[]) array), comparator);
    }

    @Override
    public E lower(E e) {
        int res = Collections.binarySearch(array, e, comparator);

        if (res < 0) {
            res = -res - 2;
        } else {
            res--;
        }

        if (res >= 0 && res < array.size()) {
            return array.get(res);
        } else {
            return null;
        }
    }

    @Override
    public E floor(E e) {
        int res = Collections.binarySearch(array, e, comparator);

        if (res < 0) {
            res = -res - 2;
        }

        if (res >= 0 && res < array.size()) {
            return array.get(res);
        } else {
            return null;
        }
    }

    @Override
    public E ceiling(E e) {
        int res = Collections.binarySearch(array, e, comparator);

        if (res < 0) {
            res = -res - 1;
        }

        if (res >= 0 && res < array.size()) {
            return array.get(res);
        } else {
            return null;
        }
    }

    @Override
    public E higher(E e) {
        int res = Collections.binarySearch(array, e, comparator);

        if (res < 0) {
            res = -res - 1;
        } else {
            res++;
        }

        if (res >= 0 && res < array.size()) {
            return array.get(res);
        } else {
            return null;
        }
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    private class ArraySetIterator implements Iterator<E> {
        int pointer;
        boolean reversedOrder;

        ArraySetIterator(boolean reversedOrder) {
            if (reversedOrder) {
                this.pointer = array.size() - 1;
            } else {
                this.pointer = 0;
            }
            this.reversedOrder = reversedOrder;
        }

        @Override
        public boolean hasNext() {
            return (reversedOrder && pointer > -1) || (!reversedOrder && pointer < array.size());
        }

        @Override
        public E next() {
            E res = array.get(pointer);
            if (reversedOrder) {
                pointer -= 1;
            } else {
                pointer += 1;
            }
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArraySetIterator(reversedOrder);
    }

    @Override
    public NavigableSet<E> descendingSet() {
        reversedOrder = !reversedOrder;

        if (array.size() == 0) {
            return new ArraySet<>();
        }

        if (hasDefaultComparator) {
            return new ArraySet<>(array, reversedOrder, true);
        }
        else {
            return new ArraySet<>(array, comparator, reversedOrder, true);
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new ArraySetIterator(!reversedOrder);
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (!reversedOrder) {
            return headSet(toElement, toInclusive).tailSet(fromElement, fromInclusive);
        } else {
            return tailSet(fromElement, fromInclusive).headSet(toElement, toInclusive);
        }
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        int res = Collections.binarySearch(array, toElement, comparator);

        if (res < 0) {
            res = -res - 1;

            if (!reversedOrder) {
                res--;
            }
        }

        if (res < 0 || res >= array.size()) {
            return new ArraySet<>();
        }

        boolean equals = comparator.compare(array.get(res), toElement) == 0;

        if (reversedOrder) {
            if (!inclusive && equals) {
                res++;
            }

            return new ArraySet<>(array.subList(res, array.size()), comparator, hasDefaultComparator, true, true);
        } else {
            if (!inclusive && equals) {
                res--;
            }

            return new ArraySet<>(array.subList(0, res + 1), comparator, hasDefaultComparator, false, true);
        }
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        int res = Collections.binarySearch(array, fromElement, comparator);

        if (res < 0) {
            res = -res - 1;

            if (reversedOrder) {
                res--;
            }
        }

        if (res < 0 || res >= array.size()) {
            return new ArraySet<>();
        }

        boolean equals;

        equals = comparator.compare(array.get(res), fromElement) == 0;

        if (reversedOrder) {
            if (!inclusive && equals) {
                res--;
            }

            return new ArraySet<>(array.subList(0, res + 1), comparator, hasDefaultComparator, true, true);
        } else {
            if (!inclusive && equals) {
                res++;
            }

            return new ArraySet<>(array.subList(res, array.size()), comparator, hasDefaultComparator, false, true);
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        return hasDefaultComparator ? null : comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (reversedOrder) {
            return array.get(array.size() - 1);
        } else {
            return array.get(0);
        }
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (reversedOrder) {
            return array.get(0);
        } else {
            return array.get(array.size() - 1);
        }
    }

    @Override
    public int size() {
        return array.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(array, (E) o, comparator) >= 0;
    }
}