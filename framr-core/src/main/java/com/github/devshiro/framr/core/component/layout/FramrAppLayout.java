package com.github.devshiro.framr.core.component.layout;

import com.github.devshiro.framr.core.component.NodeBrowser;
import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.classcollector.FramrClassCollectorSupplier;
import com.vaadin.flow.component.applayout.AppLayout;

public class FramrAppLayout extends AppLayout {

    protected final FramrClassCollector classCollector;

    public FramrAppLayout() {
        FramrClassCollectorSupplier.init(this);
        classCollector = FramrClassCollectorSupplier.getInstance();
        setupNavBar();
    }

    private void setupNavBar() {
        NodeBrowser nodeBrowser = new NodeBrowser();
        nodeBrowser.setOnTabChange(this::setContent);
        addToNavbar(nodeBrowser);
    }
}
