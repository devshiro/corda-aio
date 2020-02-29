package com.github.devshiro.framr.core.util;

@FunctionalInterface
public interface Callback<T> {
    void callback(T t);
}
