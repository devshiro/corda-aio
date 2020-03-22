package com.github.devshiro.framr.core.component.dialog;

import com.github.devshiro.framr.core.component.layout.ClassBasedFormLayout;
import com.github.devshiro.framr.core.mapper.StringInputMapper;
import com.github.devshiro.framr.core.mapper.StringMapper;
import com.github.devshiro.framr.core.util.Callback;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ClassBasedInputDialog<T> extends Window {

    private static final String DEFAULT_BTN_TEXT = "Confirm";

    public ClassBasedInputDialog(Class<T> inputClass, Callback<T> onClose) {
        this(inputClass, DEFAULT_BTN_TEXT, onClose);
    }

    public ClassBasedInputDialog(Class<T> inputClass, String confirmButtonText, Callback<T> onClose) {
        this(inputClass, confirmButtonText, null, (klass, string) -> {
            Object mapped = StringMapper.map(klass, string);
            return (T) mapped;
        }, onClose);
    }

    public ClassBasedInputDialog(Class<T> inputClass, String confirmButtonText, VaadinIcons confirmButtonIcon, StringInputMapper<T> mapper, Callback<T> onClose) {
        super();
        VerticalLayout windowLayout = new VerticalLayout();
        ClassBasedFormLayout formLayout = new ClassBasedFormLayout(inputClass);
        windowLayout.addComponent(formLayout);
        Button confirmButton = new Button();
        confirmButton.setCaption(confirmButtonText);
        if (confirmButtonIcon != null) {
            confirmButton.setIcon(confirmButtonIcon);
        }
        confirmButton.addClickListener(event -> {
            onClose.callback(formLayout.readForm(mapper));
            close();
        });
        windowLayout.addComponent(confirmButton);
        setContent(windowLayout);
        center();
    }

}
