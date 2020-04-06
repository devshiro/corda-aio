package com.github.devshiro.framr.app.ui;

import com.github.devshiro.framr.core.component.NodeBrowser;
import com.github.devshiro.framr.core.component.view.FramrUI;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/")
public class ApplicationUI extends FramrUI {

    @Autowired
    public ApplicationUI(FramrConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        NodeBrowser nodeBrowser = new NodeBrowser(component -> {
        }, NodeView::new);
        setContent(nodeBrowser);
        setSizeFull();
    }
}
