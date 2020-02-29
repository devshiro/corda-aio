package com.github.devshiro.framr.core.classcollector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FramrClassCollectorSupplier {

    private static FramrClassCollector _instance = null;

    public static void init(Object context) {
        if (_instance == null) {
            _instance = new FramrClassCollector(context);
        } else {
            log.warn("FramrClassCollector already initialized");
        }
    }

    public static FramrClassCollector getInstance() {
        if (_instance != null) {
            return _instance;
        } else {
            throw new RuntimeException("FramrClassCollector haven't been initialized.");
        }
    }
}
