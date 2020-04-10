package com.github.devshiro.framr.core.ui;

import com.github.devshiro.framr.vaadin.components.dialog.ClassBasedInputDialog;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.Data;

@SpringUI
public class MainUI extends UI {

    @Data
    public class ExampleClass {
        private String asd1;
        private String asd2;
        private String url;
    }

    @Override
    protected void init(VaadinRequest request) {
        ClassBasedInputDialog<ExampleClass> dialog = new ClassBasedInputDialog<>(ExampleClass.class, exampleClass -> {});
        addWindow(dialog);
    }
}
