package com.github.devshiro.framr.core.ui.tab;

import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.github.devshiro.framr.core.ui.component.EntityBrowserPanel;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.vaadin.components.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.vaadin.components.util.Closeable;
import com.github.devshiro.framr.vaadin.components.util.Refreshable;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class NodeLayout extends VerticalLayout implements Closeable, Refreshable {

    private final MenuBar menu = new MenuBar();
    private final NodeManagerProxy proxy;
    private final EntityBrowserPanel entityBrowserPanel;

    public NodeLayout(NodeManagerProxy proxy) {
        this.proxy = proxy;
        setupMenu();
        addComponent(menu);
        entityBrowserPanel = new EntityBrowserPanel(proxy);
        addComponent(entityBrowserPanel);
    }

    private void setupMenu() {
        MenuBar.MenuItem flows = menu.addItem("Flows");
        for (Flow flow : proxy.getFlows()) {
            MenuBar.MenuItem flowItem = flows.addItem(flow.getDisplayName());
            flowItem.setCommand(selectedItem -> {
                ClassBasedInputDialog flowInputDialog = new ClassBasedInputDialog(flow.getFlowInputClass(), o -> {
                    CompletableFuture<?> future = proxy.startFlow(flow, o);
                    future.whenComplete((o1, throwable) -> {
                        log.info("Future completed: {}", o1);
                       refresh();
                    });
                });

                flowInputDialog.setCaption(flow.getDisplayName() + " Input");
                UI.getCurrent().addWindow(flowInputDialog);
            });
        }
    }

    @Override
    public void onClose() {
        log.info("Closed");
    }

    @Override
    public void refresh() {
        entityBrowserPanel.refresh();
    }
}
