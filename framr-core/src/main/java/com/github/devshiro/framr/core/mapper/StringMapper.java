package com.github.devshiro.framr.core.mapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringMapper {

    public static Object map(Class<?> t, String string) {
        if (string == null || string.isEmpty()) {
            return null;
        } else if (Integer.class.equals(t) || Integer.TYPE.equals(t)) {
            return Integer.parseInt(string);
        } else if (String.class.equals(t)) {
            return string;
        } else if (Boolean.class.equals(t) || Boolean.TYPE.equals(t)) {
            return Boolean.parseBoolean(string);
        } else {
            throw new IllegalArgumentException("No mapper method found for " + t.getSimpleName());
        }
    }
}
