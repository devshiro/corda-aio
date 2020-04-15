package com.github.devshiro.framr.vaadin.components.panel;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class ListPanel extends Panel {
    public ListPanel(String caption, List<String> items) {
        super();
        setCaption(caption);
        setSizeUndefined();
        VerticalLayout content = new VerticalLayout();
        for (String item : items) {
            Label label = new Label(item);
            content.addComponentsAndExpand(label);
        }
        content.setSizeUndefined();
        content.setMargin(true);
        setContent(content);
    }
}
