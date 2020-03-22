package com.github.devshiro.framr.core.component.view;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.corda.CordaConnector;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.vaadin.ui.HorizontalLayout;
import net.corda.core.messaging.CordaRPCOps;

public abstract class CordaNodeLayoutBase extends HorizontalLayout {

    protected CordaRPCOps rpcOps;

    protected FramrClassCollector classCollector;

    public CordaNodeLayoutBase() {
        super();
    }

    public final void init(CordaNodeDetails nodeDetails) {
        CordaConnector cordaConnector = new CordaConnector(nodeDetails);
        rpcOps = cordaConnector.getConnection().getProxy();
        classCollector = FramrInitizr.getInstance().getClassCollector();

        setContent();
    }

    protected abstract void setContent();
}
