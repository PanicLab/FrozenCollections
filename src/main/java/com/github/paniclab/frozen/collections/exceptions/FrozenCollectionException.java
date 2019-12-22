package com.github.paniclab.frozen.collections.exceptions;


public class FrozenCollectionException extends RuntimeException {

    public FrozenCollectionException() {
    }

    public FrozenCollectionException(String message) {
        super(message);
    }

    public FrozenCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrozenCollectionException(Throwable cause) {
        super(cause);
    }
}
