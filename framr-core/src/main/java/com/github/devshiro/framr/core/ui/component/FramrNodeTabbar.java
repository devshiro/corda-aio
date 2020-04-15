package com.github.devshiro.framr.core.ui.component;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;

import java.util.Optional;

public class FramrNodeTabbar extends TabSheet {

    private TabSheet.Tab newNodeTab;
    private final NodeManager nodeManager;

    public FramrNodeTabbar(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
        newNodeTab = newNodeTab(nodeManager);
        prefetch();
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
