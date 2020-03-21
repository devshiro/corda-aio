package com.github.devshiro.framr.core.component.layout;

import com.github.devshiro.framr.core.component.NodeBrowser;
import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.vaadin.flow.component.applayout.AppLayout;

// Extension of the vaadin AppLayout with Framr stuff
public class FramrAppLayout extends AppLayout {

    protected final FramrClassCollector classCollector;

    public FramrAppLayout(FramrConfiguration configuration) {
        FramrInitizr.init(configuration, true);
        classCollector = FramrInitizr.getInstance().getClassCollector();
    }
}
