package com.github.devshiro.framr.core.component.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Notifications {

    private static final int DEFAULT_DURATION = 5000;
    private static final Notification.Position DEFAULT_POSITION = Notification.Position.BOTTOM_START;

    public static Notification show(String text) {
        Notification notification = new Notification(text, DEFAULT_DURATION, DEFAULT_POSITION);
        notification.open();
        return notification;
    }

    public static Notification showSuccess(String text) {
        Notification notification = new Notification(text, DEFAULT_DURATION, DEFAULT_POSITION);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
        return notification;
    }

    public static Notification showError(String text) {
        Notification notification = new Notification(text, DEFAULT_DURATION, DEFAULT_POSITION);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
        return notification;
    }
}
