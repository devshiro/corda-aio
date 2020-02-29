package com.github.devshiro.framr.core.mapper;

@FunctionalInterface
public interface StringInputMapper<T> {
     T map(Class<T> inputClass, String string);
}
