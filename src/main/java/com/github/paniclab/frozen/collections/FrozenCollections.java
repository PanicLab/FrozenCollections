package com.github.paniclab.frozen.collections;

import com.github.paniclab.frozen.collections.exceptions.FrozenCollectionException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Immutable
public final class FrozenCollections {
    private FrozenCollections() {
        throw new UnsupportedOperationException("Cannot create instance of this class.");
    }

    @SuppressWarnings("unchecked")
    @NotNull
    static <U> U getBrandNewInstance(@NotNull Class<U> clazz) {
        Constructor<U> constructor;

        constructor = (Constructor<U>) Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getGenericParameterTypes().length == 0)
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Unable to create instance of class " + clazz.getCanonicalName() + ". This class has no " +
                        "appropriate constructor with no args."));

        U instance;
        try {
            constructor.setAccessible(true);
            instance = constructor.newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FrozenCollectionException("Unable to create instance of class " + clazz.getCanonicalName(), e);
        }

        return instance;
    }


    static <U> Constructor<U> getConstructor(@NotNull Class<U> clazz, Class<?>...paramTypes) {
        Constructor<U> constructor;

        try {
            constructor = Optional.ofNullable(clazz.getDeclaredConstructor(paramTypes))
                    .orElseThrow(() -> new UnsupportedOperationException(
                            "Unable to create instance of class " + clazz.getCanonicalName() + ". This class has no " +
                                    "appropriate constructor with args of types:" + Arrays.toString(paramTypes)));

        } catch (NoSuchMethodException e) {
            throw new FrozenCollectionException("Unable to find constructor of class " + clazz.getCanonicalName() +
                    " with parameters " + Arrays.toString(paramTypes), e);
        }

        constructor.setAccessible(true);
        return constructor;
    }

    public static boolean isImmutable(Class<?> clazz) {
        return clazz.isAnnotationPresent(Immutable.class);
    }
}
