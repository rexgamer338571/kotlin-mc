package dev.ng5m.util;

@FunctionalInterface
public interface Getter<T, O> {
    O get(T instance);
}
