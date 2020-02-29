package com.github.devshiro.framr.core.component;

import com.github.devshiro.framr.core.component.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.core.component.util.Notifications;
import com.github.devshiro.framr.core.flow.FlowDetail;
import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.classcollector.FramrClassCollectorSupplier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import net.corda.core.flows.FlowLogic;
import net.corda.core.messaging.CordaRPCOps;

public class FramrFlowSelector extends VerticalLayout {

    private final FramrClassCollector classCollector;

    private final CordaRPCOps rpcOps;

    public FramrFlowSelector(CordaRPCOps rpcOps) {
        super();
        this.classCollector = FramrClassCollectorSupplier.getInstance();
        this.rpcOps = rpcOps;
        init();
    }

    private void init() {
        classCollector.getCachedFlowClasses().forEach(klass -> {
            FlowDetail flowDetail = FlowDetail.fromClass(klass);
            Button flowButton = new Button();
            flowButton.setText(flowDetail.getDisplayName());
            flowButton.addClickListener(e -> {
                ClassBasedInputDialog flowInputDialog = new ClassBasedInputDialog(flowDetail.getFlowInputClass(), "Start Flow", o -> {
                    rpcOps.startFlowDynamic((Class<? extends FlowLogic<?>>) flowDetail.getFlowClass(), o);
                    Notifications.showSuccess("Flow Executed");
                });
                flowInputDialog.open();
            });
            add(flowButton);
        });
    }

}
