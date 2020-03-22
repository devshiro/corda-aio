package com.github.devshiro.framr.core.component;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.component.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.core.component.util.Notifications;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.flow.FlowDetail;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import net.corda.core.flows.FlowLogic;
import net.corda.core.messaging.CordaRPCOps;

import java.util.List;

public class FramrFlowSelector extends VerticalLayout {

    private final FramrClassCollector classCollector;

    private final CordaRPCOps rpcOps;

    public FramrFlowSelector(CordaRPCOps rpcOps) {
        super();
        this.classCollector = FramrInitizr.getInstance().getClassCollector();
        this.rpcOps = rpcOps;
        init();
    }

    private void init() {
        List<Class<?>> cachedFlowClasses = classCollector.getCachedFlowClasses();
        if (cachedFlowClasses.isEmpty()) {
            Notifications.showWarning("No flow classes found. Maybe you've missing the workflow cordapp(s)?");
        }
        classCollector.getCachedFlowClasses().forEach(klass -> {
            FlowDetail flowDetail = FlowDetail.fromClass(klass);
            Button flowButton = new Button();
            flowButton.setCaption(flowDetail.getDisplayName());
            flowButton.addClickListener(e -> {
                ClassBasedInputDialog flowInputDialog = new ClassBasedInputDialog(flowDetail.getFlowInputClass(), "Start Flow", o -> {
                    rpcOps.startFlowDynamic((Class<? extends FlowLogic<?>>) flowDetail.getFlowClass(), o);
                    Notifications.showSuccess("Flow Executed");
                });
                UI.getCurrent().addWindow(flowInputDialog);
            });
            addComponent(flowButton);
        });
    }

}
