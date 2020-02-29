package com.github.devshiro.framr.core.component;

import com.github.devshiro.framr.core.component.util.Notifications;
import com.github.devshiro.framr.core.component.view.FramrCordaNodeView;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.component.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.core.util.Callback;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public class NodeBrowser extends HorizontalLayout {

    private final Map<Tab, Component> tabsToComponents;
    private final Tabs nodeTabs;

    @Setter
    private Callback<Component> onTabChange;

    public NodeBrowser() {
        super();
        tabsToComponents = new LinkedHashMap<>();
        nodeTabs = connectionTabs();
        add(nodeTabs, newConnectionButton());
    }

    private Tabs connectionTabs() {
        Tabs connectionTabs = new Tabs();
        connectionTabs.addSelectedChangeListener(e -> {
            Tab selected = connectionTabs.getSelectedTab();
            if (onTabChange != null) {
                Component component = tabsToComponents.get(selected);
                onTabChange.callback(component);
            }
        });
        return connectionTabs;
    }

    private Button newConnectionButton() {
        Button addButton = new Button();
        Icon icon = VaadinIcon.PLUS.create();
        icon.setColor("#1C2E45");
        addButton.setIcon(icon);
        addButton.addClickListener(e -> {
            ClassBasedInputDialog<CordaNodeDetails> detailsForm = new ClassBasedInputDialog<>(CordaNodeDetails.class, "Connect", nodeDetails -> {
                Tab nodeTab = new Tab(nodeDetails.getHost() + ":" + nodeDetails.getPort());
                try {
                    FramrCordaNodeView nodeView = new FramrCordaNodeView(nodeDetails);
                    tabsToComponents.put(nodeTab, nodeView);
                    nodeTabs.add(nodeTab);
                } catch (Exception ex) {
                    Notifications.showError(ex.getMessage());
                }
            });
            detailsForm.open();
        });
        return addButton;
    }
}
