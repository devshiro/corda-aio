package com.github.devshiro.framr.core.ui.menu;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.MenuBar;

public class MainMenu extends MenuBar {

    public MainMenu() {
        super();
        MenuItem cordaNodes = addItem("Nodes", VaadinIcons.CLUSTER, null);
        MenuItem connect = cordaNodes.addItem("Connect...", null, null);
    }
}
