package com.github.devshiro.framr.core.ui.tab;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.github.devshiro.framr.vaadin.components.util.Closeable;
import com.github.devshiro.framr.vaadin.components.util.Refreshable;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.Optional;

public class FramrNodeTabbar extends TabSheet {

    private TabSheet.Tab newNodeTab;
    private final NodeManager nodeManager;

    public FramrNodeTabbar(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
        newNodeTab = newNodeTab(nodeManager);
        prefetch();
        setCloseHandler(new CloseHandler() {
            @Override
            public void onTabClose(TabSheet tabsheet, Component tabContent) {
                Tab tab = tabsheet.getTab(tabContent);
                if (Closeable.class.isAssignableFrom(tab.getClass())) {
                    Closeable closeableTab = (Closeable) tab;
                    closeableTab.onClose();
                }
                tabsheet.removeTab(tab);
            }
        });

        addSelectedTabChangeListener(new SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                TabSheet tabSheet = event.getTabSheet();
                Component tab = tabSheet.getSelectedTab();
                if (Refreshable.class.isAssignableFrom(tab.getClass())) {
                    Refreshable refreshableTab = (Refreshable) tab;
                    refreshableTab.refresh();
                }
            }
        });
    }

    private void prefetch() {
        for (NodeManagerProxy nodeManagerProxy : nodeManager.getNodeManagerProxies()) {
            createNodeTab(nodeManagerProxy);
        }
    }

    TabSheet.Tab newNodeTab(NodeManager nodeManager) {
        NewNodeLayout newNodeLayout = new NewNodeLayout(nodeManager, nodeConnectionDetails -> {
            Optional<NodeManagerProxy> nodeManagerProxy = nodeManager.register(nodeConnectionDetails);
            // Create new page
            nodeManagerProxy.ifPresent(this::createNodeTab);
        });
        return addTab(newNodeLayout, null, VaadinIcons.PLUS);
    }

    private Tab createNodeTab(NodeManagerProxy proxy) {
        TabSheet.Tab newTab = addTab(new NodeLayout(proxy));
        newTab.setClosable(true);
        newTab.setCaption(proxy.getNodeName());
        newTab.setIcon(VaadinIcons.EXCHANGE);
        return newTab;
    }
}
