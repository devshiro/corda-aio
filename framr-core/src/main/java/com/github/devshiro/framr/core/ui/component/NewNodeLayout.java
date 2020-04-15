package com.github.devshiro.framr.core.ui.component;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.vaadin.components.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.vaadin.components.util.Callback;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.*;

public class NewNodeLayout extends VerticalLayout {

    private final Label header = new Label("Connect to new Corda Node");

    private final NodeManager nodeManager;
    private final Callback<NodeConnectionDetails> onCreateNewNode;

    public NewNodeLayout(NodeManager nodeManager, Callback<NodeConnectionDetails> onCreateNewNode) {
        super();
        this.nodeManager = nodeManager;
        this.onCreateNewNode = onCreateNewNode;
        addComponent(header);
        addComponent(new LoadedCordappsBar(nodeManager));
        addComponent(newNodeBtn());

    }

    private Button newNodeBtn() {
        Button button = new Button();
        button.setCaption("New Node Connection");
        button.addClickListener(event -> {
            ClassBasedInputDialog<NodeConnectionDetails> connectionDetailsDialog = new ClassBasedInputDialog<>(NodeConnectionDetails.class, onCreateNewNode);
            connectionDetailsDialog.setCaption("New node connection");
            getUI().addWindow(connectionDetailsDialog);
        });
        return button;
    }

    @Data
    public static class NodeConnectionDetails {
        private String rpcHost;
        private int rpcPort;
        private String rpcUsername;
        private String rpcPassword;
        private String dbUrl;
        private String dbUsername;
        private String dbPassword;
    }
}
