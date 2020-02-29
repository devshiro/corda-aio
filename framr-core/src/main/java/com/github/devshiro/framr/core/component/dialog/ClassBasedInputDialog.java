package com.github.devshiro.framr.core.component.dialog;

import com.github.devshiro.framr.core.component.layout.ClassBasedFormLayout;
import com.github.devshiro.framr.core.util.Callback;
import com.github.devshiro.framr.core.mapper.StringInputMapper;
import com.github.devshiro.framr.core.mapper.StringMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;

public class ClassBasedInputDialog<T> extends Dialog {

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

    public ClassBasedInputDialog(Class<T> inputClass, String confirmButtonText, Icon confirmButtonIcon, StringInputMapper<T> mapper, Callback<T> onClose) {
        super();
        ClassBasedFormLayout formLayout = new ClassBasedFormLayout(inputClass);
        add(formLayout);
        Button confirmButton = new Button();
        confirmButton.setText(confirmButtonText);
        if (confirmButtonIcon != null) {
            confirmButton.setIcon(confirmButtonIcon);
        }
        confirmButton.addClickListener(event -> {
            onClose.callback(formLayout.readForm(mapper));
            close();
        });
        add(confirmButton);
    }

}
