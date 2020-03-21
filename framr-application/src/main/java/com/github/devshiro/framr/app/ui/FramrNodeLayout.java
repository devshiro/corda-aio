package com.github.devshiro.framr.app.ui;

import com.github.devshiro.framr.core.component.view.CordaNodeLayoutBase;
import com.vaadin.flow.component.html.Label;

public class FramrNodeLayout extends CordaNodeLayoutBase {

    public FramrNodeLayout() {
        super();
        add(new Label("Hello label"));
        setSizeFull();
    }
}
