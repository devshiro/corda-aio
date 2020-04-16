package com.github.devshiro.framr.core.ui;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.core.ui.tab.FramrNodeTabbar;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MainUI extends UI {

    @Autowired
    private NodeManager nodeManager;

    @Override
    protected void init(VaadinRequest request) {
        setContent(new FramrNodeTabbar(nodeManager));
    }

}
