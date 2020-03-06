package com.github.devshiro.framr.core.component.view;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.component.FramrFlowSelector;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.corda.CordaConnector;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import net.corda.core.messaging.CordaRPCOps;

public class FramrCordaNodeView extends HorizontalLayout {

    private CordaRPCOps rpcOps;
    private final FramrClassCollector classCollector;

    public FramrCordaNodeView(CordaNodeDetails nodeDetails) {
        super();
        CordaConnector cordaConnector = new CordaConnector(nodeDetails);
        rpcOps = cordaConnector.getConnection().getProxy();
        classCollector = FramrInitizr.getInstance().getClassCollector();
        FramrFlowSelector framrFlowSelector = new FramrFlowSelector(rpcOps);
        Label label = new Label("Hello from " + rpcOps.nodeInfo().toString());
        add(framrFlowSelector, label);
        setSizeFull();
    }
}
