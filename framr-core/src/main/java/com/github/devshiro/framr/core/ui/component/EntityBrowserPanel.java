package com.github.devshiro.framr.core.ui.component;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.vaadin.components.grid.EntityGrid;
import com.github.devshiro.framr.vaadin.components.util.Refreshable;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

import java.util.ArrayList;
import java.util.List;

public class EntityBrowserPanel extends Panel implements Refreshable {

    private final NodeManagerProxy proxy;
    private final TabSheet content;

    private final List<EntityGrid> grids;

    public EntityBrowserPanel(NodeManagerProxy proxy) {
        super();
        this.proxy = proxy;
        grids = new ArrayList<>();
        setCaption("Entity Browser");
        content = new TabSheet();

        for (Entity entity : proxy.getEntities()) {
            EntityGrid grid = new EntityGrid(entity.getKlass(), proxy.getEntities(entity.getKlass()));
            grid.setSizeFull();
            content.addTab(grid, entity.getKlass().getSimpleName());
            grids.add(grid);
        }

        content.setSizeFull();
        setContent(content);
        setSizeFull();
    }

    @Override
    public void refresh() {
        for (EntityGrid grid : grids) {
            grid.setItems(proxy.getEntities(grid.getEntityClass()));
            grid.markAsDirty();
        }
    }
}
