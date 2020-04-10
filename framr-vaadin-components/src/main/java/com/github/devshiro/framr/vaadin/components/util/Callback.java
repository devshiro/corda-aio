package com.github.devshiro.framr.vaadin.components.util;

@FunctionalInterface
public interface Callback<T> {
    void callback(T t);
}
