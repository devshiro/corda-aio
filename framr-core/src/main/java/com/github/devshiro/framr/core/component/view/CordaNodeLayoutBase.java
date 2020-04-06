package com.github.devshiro.framr.core.component.view;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.component.Closeable;
import com.github.devshiro.framr.core.component.menubar.NodeOpsMenuBar;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.corda.CordaConnector;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import net.corda.core.messaging.CordaRPCOps;

public abstract class CordaNodeLayoutBase extends VerticalLayout implements Closeable {

    protected CordaRPCOps rpcOps;

    protected FramrClassCollector classCollector;

    protected CordaNodeDetails nodeDetails;

    public CordaNodeLayoutBase() {
        super();
    }

    public final void init(CordaNodeDetails nodeDetails) {
        this.nodeDetails = nodeDetails;
        CordaConnector cordaConnector = new CordaConnector(nodeDetails);
        rpcOps = cordaConnector.getConnection().getProxy();
        classCollector = FramrInitizr.getInstance().getClassCollector();
        NodeOpsMenuBar menuBar = new NodeOpsMenuBar(rpcOps);
        addComponent(menuBar);
        setContent();
        setSizeFull();
    }

    protected abstract void setContent();
}
