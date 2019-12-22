package com.github.paniclab.frozen.collections;


import com.github.paniclab.frozen.collections.exceptions.FrozenCollectionException;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;


public final class FrozenCollection<E> implements Frozen<E>, Collection<E> {
    private static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE =
            "This operation is not supported for this class.";
    private static final Class<? extends Collection> DEFAULT_CONSTANT_CONTAINER_TYPE = HashSet.class;

    private final int SIZE;
    private final Object ITERATIVE_CONTAINER;
    private final Class<?> ITERATIVE_CONTAINER_TYPE;
    private final Collection<E> CONSTANT_TIME_CONTAINER;
    private final Class<? extends Collection> CONSTANT_TIME_CONTAINER_TYPE;
    private final Class<E> ELEMENT_TYPE;


    private FrozenCollection() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    FrozenCollection(
            Collection<E> elements,
            Class<E> elementType,
            Collection<E> container) {
        this.ELEMENT_TYPE = elementType;
        this.SIZE = elements.size();

        this.ITERATIVE_CONTAINER_TYPE = (container == null ? Array.class : container.getClass());
        this.ITERATIVE_CONTAINER = (container == null ? initializeArrayContainer(elements) : container);

        this.CONSTANT_TIME_CONTAINER_TYPE = (container == null ? DEFAULT_CONSTANT_CONTAINER_TYPE : container.getClass());
        this.CONSTANT_TIME_CONTAINER = (container == null ? createDefaultConstantTimeContainer(elements) : container);
    }

    private <U extends Collection> Object initializeArrayContainer(U elements) {
        return Array.newInstance(elements.getClass(), elements.size());
    }

    private Collection<E> createDefaultConstantTimeContainer(Collection<E> elements) {
        return createEmptyCollectionContainer(DEFAULT_CONSTANT_CONTAINER_TYPE, elements);
    }


    private <U extends Collection> Collection<E> createEmptyCollectionContainer(
            Class<U> containerType,
            Collection<E> elements) {

        Collection<E> container;
        container = tryOptimizedHashTableConstructor(containerType, elements);
        if(container == null) {
            container = tryInitialCapacityBased(containerType, elements);
        }
        if(container == null) {
            container = tryArrayBased(containerType, elements);
        }
        if(container == null) {
            container = Optional.ofNullable(tryMandatoryNoArgConstructor(containerType))
                                .orElseThrow(() -> new FrozenCollectionException("Cannot create instance."));
        }

        return container;
    }

    @Nullable
    private <U extends Collection> Collection<E> tryOptimizedHashTableConstructor(Class<U> collectionType, Collection<E> elements) {
        Class<?>[] optimizedParams = {int.class, float.class};
        U instance = null;

        try {
            Constructor<U> containerConstructor = collectionType.getDeclaredConstructor(optimizedParams);
            instance = containerConstructor.newInstance(elements.size() + 1, 1f);
        } catch (ReflectiveOperationException e) {
            //throw new FrozenCollectionException("Unable to create instance of inner hash table based collection container.", e);
            //TODO log it
        }
        return instance;
    }

    @Nullable
    private <U extends Collection> Collection<E> tryInitialCapacityBased(Class<U> collectionType, Collection<E> elements) {
        Class<?>[] optimizedParams = {int.class};
        U instance = null;

        try {
            Constructor<U> containerConstructor = collectionType.getDeclaredConstructor(optimizedParams);
            instance = containerConstructor.newInstance(elements.size());
        } catch (ReflectiveOperationException e) {
            //throw new FrozenCollectionException("Unable to create instance of inner hash table based collection container.", e);
            //TODO log it
        }
        return instance;
    }

    @Nullable
    private <U extends Collection> Collection<E> tryArrayBased(Class<U> collectionType, Collection<E> elements) {
        Class<?>[] optimizedParams = {Object.class};
        U instance = null;

        try {
            Constructor<U> containerConstructor = collectionType.getDeclaredConstructor(optimizedParams);
            Object array = Array.newInstance(elements.getClass(), elements.size());
            instance = containerConstructor.newInstance(array);
        } catch (ReflectiveOperationException e) {
            //throw new FrozenCollectionException("Unable to create instance of inner hash table based collection container.", e);
            //TODO log it
        }
        return instance;
    }

    @Nullable
    private <U extends Collection> Collection<E> tryMandatoryNoArgConstructor(Class<U> collectionType) {
        U instance = null;
        try {
            instance = FrozenCollections.getBrandNewInstance(collectionType);
        } catch (Exception e) {
            //TODO log it
        }
        return instance;
    }




    @SuppressWarnings("unchecked")
    protected <U> U iterativeContainer() {
        return (U)this.ITERATIVE_CONTAINER;
    }

    protected Collection<E> constantTimeContainer() {
        return CONSTANT_TIME_CONTAINER;
    }

    public int size() {
        return SIZE;
    }

    public boolean isEmpty() {
        return SIZE == 0;
    }

    public boolean contains(Object o) {
        return constantTimeContainer().contains(o);
    }

    public Iterator<E> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        return null;
    }

    public boolean add(E t) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    public void clear() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public Frozen<E> addImmutable(E e) {
        return null;
    }

    @Override
    public Frozen<E> removeImmutable(E e) {
        return null;
    }

    @Override
    public Frozen<E> addAllImmutable(Collection<? extends E> c) {
        return null;
    }

    @Override
    public Frozen<E> removeAllImmutable(Collection<? extends E> c) {
        return null;
    }

    @Override
    public Frozen<E> asEmpty() {
        return null;
    }

    @Override
    public E[] asArray() {
        return null;
    }

    @Override
    public List<E> asList() {
        return null;
    }
}
