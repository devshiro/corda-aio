package com.github.devshiro.framr.core.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@PWA(name = "Framr Application", shortName = "FramR")
@Route("")
public class MainUI extends Div {
    public MainUI() {
        setText("Hello PWA Vaadin 15 (again)");
    }
}
