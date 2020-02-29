package com.github.devshiro.framr.core.component;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class FramrIcon extends Icon {

    public FramrIcon(VaadinIcon icon, String color) {
        super(icon);
        setColor(color);
    }
}
