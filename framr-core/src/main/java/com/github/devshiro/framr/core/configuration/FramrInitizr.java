package com.github.devshiro.framr.core.configuration;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;

import java.util.Objects;

public class FramrInitizr {

    private static FramrInitizr _instance;

    public static FramrInitizr getInstance() {
        if (_instance != null) {
            return _instance;
        } else {
            throw new IllegalStateException("Framr not initialized yet. Forgot to call FramrInitizr::init() ?");
        }
    }

    public static FramrInitizr init(FramrConfiguration configuration) {
        _instance = new FramrInitizr(configuration);
        return getInstance();
    }

    public static FramrInitizr init(FramrConfiguration configuration, boolean eagerInit) {
        _instance = new FramrInitizr(configuration, eagerInit);
        return getInstance();
    }

    private final FramrConfiguration configuration;

    private FramrClassCollector classCollector;

    private FramrInitizr(FramrConfiguration configuration) {
        this(configuration, false);
    }

    private FramrInitizr(FramrConfiguration configuration, boolean eagerInit) {
        Objects.requireNonNull(configuration, "Configuration must not be null");
        this.configuration = configuration;
        _instance = this;

        if (eagerInit) {
            classCollector = classCollectorInit(configuration.getClassCollectorConfiguration());
        }
    }

    private FramrClassCollector classCollectorInit(FramrClassCollectorConfiguration classCollectorConfiguration) {
        if (classCollectorConfiguration.isUseAnnotation()) {
            // figure this shit out later
        } else {
            // tbd
        }
        return new FramrClassCollector(classCollectorConfiguration.getCordappPackageName());
    }

    public FramrClassCollector getClassCollector() {
        if (classCollector == null) {
            classCollector = classCollectorInit(configuration.getClassCollectorConfiguration());
        }
        return classCollector;
    }
}
