package com.github.devshiro.framr.vaadin.components.mapper;

@FunctionalInterface
public interface StringInputMapper<T> {
     T map(Class<T> inputClass, String string);
}
