package com.github.devshiro.framr.demo.frontend.view;

import com.github.devshiro.framr.annotation.FramrApplication;
import com.github.devshiro.framr.core.component.layout.FramrAppLayout;
import com.github.devshiro.framr.core.configuration.FramrClassCollectorConfiguration;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "Demo Dashboard", shortName = "DemoApp")
@FramrApplication(packageName = "com.github.devshiro.framr.demo")
public class FramrView extends FramrAppLayout {

    @Autowired
    public FramrView(FramrConfiguration configuration) {
        super(configuration);
//        EntityGrid grid = new EntityGrid(ExampleEntity.class);
//        setContent(grid);
    }
}
