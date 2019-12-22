package com.github.paniclab.frozen.collections;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public final class FrozenCollectionBuilder<T, E> {
    private Class<T> containerType;
    private Class<E> elementType;
    private Collection<? extends E> elements;

    FrozenCollectionBuilder() {
    }

    @NotNull
    public FrozenCollectionBuilder<T, E> withContainerType(@Nullable Class<T> containerType) {
        this.containerType = containerType;
        return this;
    }

    @NotNull
    public FrozenCollectionBuilder<T, E> withElementType(@NotNull Class<E> elementType) {
        this.elementType = elementType;
        return this;
    }

    @NotNull
    public FrozenCollectionBuilder<T, E> withElements(@NotNull Collection<? extends E> collection) {
        this.elements = collection;
        return this;
    }

    @Nullable
    Class<T> containerType() {
        return this.containerType;
    }

    @NotNull
    Class<E> elementType() {
        return this.elementType;
    }

    @NotNull
    Collection<? extends E> elements() {
        if(this.elements == null) {
            return Collections.emptyList();
        }
        return this.elements;
    }

    public FrozenCollection<E> build() {

    }
}
