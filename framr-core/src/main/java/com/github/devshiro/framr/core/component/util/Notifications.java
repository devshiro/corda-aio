package com.github.devshiro.framr.core.component.util;

import com.vaadin.ui.Notification;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Notifications {

    private static final int DEFAULT_DURATION = 5000;

    public static void showSuccess(String text) {
        Notification.show(text, Notification.Type.HUMANIZED_MESSAGE);
    }

    public static void showWarning(String text) {
        Notification.show(text, Notification.Type.WARNING_MESSAGE);
    }

    public static void showError(String text) {
        Notification.show(text, Notification.Type.ERROR_MESSAGE);
    }
}
