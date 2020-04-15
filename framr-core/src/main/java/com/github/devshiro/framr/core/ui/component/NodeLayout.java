package com.github.devshiro.framr.core.ui.component;

import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.vaadin.components.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.vaadin.components.grid.EntityGrid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.Map;

public class NodeLayout extends VerticalLayout {

    private final MenuBar menu = new MenuBar();
    private final NodeManagerProxy proxy;

    private Map<Class<?>, EntityGrid> grids = new HashMap<>();

    public NodeLayout(NodeManagerProxy proxy) {
        this.proxy = proxy;
        setupMenu();
        addComponent(menu);
        for (Entity entity : proxy.getEntities()) {
            EntityGrid grid = new EntityGrid(entity.getKlass(), proxy.getEntities(entity.getKlass()));
            grids.put(entity.getKlass(), grid);
            addComponent(grid);
        }
    }

    private void setupMenu() {
        MenuBar.MenuItem flows = menu.addItem("Flows");
        MenuBar.MenuItem refresh = menu.addItem("Refresh");
        refresh.setCommand(selectedItem -> {
            for(Entity entity : proxy.getEntities()) {
                EntityGrid grid = grids.get(entity.getKlass());
                grid.setItems(proxy.getEntities(entity.getKlass()));
            }
        });
        for (Flow flow : proxy.getFlows()) {
            MenuBar.MenuItem flowItem = flows.addItem(flow.getDisplayName());
            flowItem.setCommand(selectedItem -> {
                ClassBasedInputDialog flowInputDialog = new ClassBasedInputDialog(flow.getFlowInputClass(), o -> {
                    proxy.startFlow(flow, o);
                });

                flowInputDialog.setCaption(flow.getDisplayName() + " Input");
                UI.getCurrent().addWindow(flowInputDialog);
            });
        }
    }

}
