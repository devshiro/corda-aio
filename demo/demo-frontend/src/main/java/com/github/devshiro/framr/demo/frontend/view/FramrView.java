package com.github.devshiro.framr.demo.frontend.view;

import com.github.devshiro.framr.annotation.FramrApplication;
import com.github.devshiro.framr.core.EntityGrid;
import com.github.devshiro.framr.core.FramrAppLayout;
import com.github.devshiro.framr.core.component.FramrIcon;
import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Route("")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "Demo Dashboard", shortName = "DemoApp")
@FramrApplication(packageName = "com.github.devshiro.framr.demo")
public class FramrView extends FramrAppLayout {
    public FramrView() {
        super();
//        EntityGrid grid = new EntityGrid(ExampleEntity.class);
//        setContent(grid);
        Icon icon = new FramrIcon(VaadinIcon.CALENDAR_ENVELOPE, "blue");
        setContent(icon);
    }
}
