package com.github.devshiro.framr.core.component.view;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.vaadin.ui.UI;

public abstract class FramrUI extends UI {

    protected final FramrClassCollector classCollector;

    public FramrUI(FramrConfiguration configuration) {
        FramrInitizr.init(configuration, true);
        classCollector = FramrInitizr.getInstance().getClassCollector();
    }
}