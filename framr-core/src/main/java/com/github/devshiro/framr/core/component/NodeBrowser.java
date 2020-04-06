package com.github.devshiro.framr.core.component;

import com.github.devshiro.framr.core.component.util.Notifications;
import com.github.devshiro.framr.core.component.view.CordaNodeLayoutBase;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.component.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.core.util.Callback;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NodeBrowser extends VerticalLayout {

    private final Map<TabSheet.Tab, Component> tabsToComponents;
    private final TabSheet nodeTabs;

    private final Callback<Component> onTabChange;

    private final Supplier<? extends CordaNodeLayoutBase> contentSupplier;

    public NodeBrowser(Callback<Component> onTabChange, Supplier<? extends CordaNodeLayoutBase> contentSupplier) {
        super();
        this.onTabChange = onTabChange;
        this.contentSupplier = contentSupplier;
        tabsToComponents = new LinkedHashMap<>();
        nodeTabs = connectionTabs();
        nodeTabs.setSizeFull();
        addComponents(newConnectionButton(), nodeTabs);
        setSpacing(false);
        setMargin(false);
        setWidthFull();
    }

    private TabSheet connectionTabs() {
        TabSheet connectionTabs = new TabSheet();
        connectionTabs.addSelectedTabChangeListener(e -> {
            if (onTabChange != null) {
                onTabChange.callback(connectionTabs.getSelectedTab());
            }
        });
        return connectionTabs;
    }

    private Button newConnectionButton() {
        Button addButton = new Button();
        VaadinIcons icon = VaadinIcons.PLUS;
        addButton.setCaption("Connect to Corda node");
        addButton.setIcon(icon);
        addButton.addClickListener(e -> {
            ClassBasedInputDialog<CordaNodeDetails> detailsForm = new ClassBasedInputDialog<>(CordaNodeDetails.class, "Connect", nodeDetails -> {
                String caption = nodeDetails.getRpcHost() + ":" + nodeDetails.getRpcPort();
                try {
                    CordaNodeLayoutBase nodeView = contentSupplier.get();
                    nodeView.init(nodeDetails);
                    TabSheet.Tab tab = nodeTabs.addTab(nodeView, caption);
                    tabsToComponents.put(tab, nodeView);
                } catch (Exception ex) {
                    Notifications.showError(ex.getMessage());
                }
            });
            UI.getCurrent().addWindow(detailsForm);
        });
        return addButton;
    }
}
