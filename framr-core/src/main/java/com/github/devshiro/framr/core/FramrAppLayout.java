package com.github.devshiro.framr.core;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class FramrAppLayout extends AppLayout {

    protected final FramrClassCollector classCollector;

    public FramrAppLayout() {
        classCollector = new FramrClassCollector(this);
        setupNavBar();
        setupDrawer();
    }

    private void setupDrawer() {
        FlowSelector flowSelector = new FlowSelector(classCollector);
        addToDrawer(flowSelector);
    }

    private void setupNavBar() {
        Tabs tabs = new Tabs();
        classCollector.getCachedEntityClasses().forEach(klass -> {
            Tab tab = new Tab();
            tab.setLabel(klass.getSimpleName());
            tabs.add(tab);
        });
        addToNavbar(tabs);
    }
}
