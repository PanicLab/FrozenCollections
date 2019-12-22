package com.github.paniclab.frozen.collections;


import java.util.Collection;
import java.util.List;


public interface Frozen<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    boolean containsAll(@Immutable Collection<?> c);
    Frozen<E> addImmutable(@Immutable E e);
    Frozen<E> removeImmutable(@Immutable E e);
    Frozen<E> addAllImmutable(@Immutable Collection<? extends E> c);
    Frozen<E> removeAllImmutable(@Immutable Collection<? extends E> c);
    Frozen<E> asEmpty();
    E[] asArray();
    List<E> asList();
}
