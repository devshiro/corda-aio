package com.github.devshiro.framr.vaadin.components.dialog;

import com.github.devshiro.framr.vaadin.components.layout.ClassBasedFormLayout;
import com.github.devshiro.framr.vaadin.components.mapper.StringInputMapper;
import com.github.devshiro.framr.vaadin.components.util.Callback;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ClassBasedInputDialog<T> extends Dialog {

    private static final String DEFAULT_BTN_TEXT = "Confirm";

    public ClassBasedInputDialog(Class<T> inputClass, String confirmButtonText, Callback<T> onClose) {

    }

    public ClassBasedInputDialog(Class<T> inputClass, String confirmButtonText, VaadinIcon confirmButtonIcon, StringInputMapper<T> mapper, Callback<T> onClose) {
        VerticalLayout dialogLayout = new VerticalLayout();
        ClassBasedFormLayout formLayout = new ClassBasedFormLayout(inputClass);
        formLayout.setSizeUndefined();
        dialogLayout.add(formLayout);
        Button confirmButton = new Button();
        confirmButton.setText(confirmButtonText);
        if (confirmButtonIcon != null) {
            confirmButton.setIcon(new Icon(confirmButtonIcon));
        }
        confirmButton.addClickListener(event -> {
            onClose.callback(formLayout.readForm(mapper));
            close();
        });
        dialogLayout.add(confirmButton);
        dialogLayout.setSizeUndefined();
        add(dialogLayout);
    }
}
