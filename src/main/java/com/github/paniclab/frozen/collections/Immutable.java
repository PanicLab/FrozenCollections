package com.github.paniclab.frozen.collections;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface Immutable {
}
