package com.github.devshiro.framr.core.component.menubar;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.component.dialog.ClassBasedInputDialog;
import com.github.devshiro.framr.core.component.util.Notifications;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.flow.FlowDetail;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import net.corda.core.flows.FlowLogic;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.messaging.FlowProgressHandle;

import java.util.List;

public class NodeOpsMenuBar extends MenuBar {

    private final FramrClassCollector classCollector;

    private CordaRPCOps rpcOps;
    MenuItem flowsMenu;

    public NodeOpsMenuBar(CordaRPCOps rpcOps) {
        super();
        this.rpcOps = rpcOps;
        classCollector = FramrInitizr.getInstance().getClassCollector();
        flowsMenu();
    }

    private void flowsMenu() {
        List<String> registeredFlows = rpcOps.registeredFlows();
        flowsMenu = addItem("Flows");
        registeredFlows.forEach(flow -> {
            try {
                Class flowClass = Class.forName(flow);
                FlowDetail flowDetail = FlowDetail.fromClass(flowClass);
                MenuItem flowMenuItem = flowsMenu.addItem(flowDetail.getDisplayName());
                flowMenuItem.setCommand(selectedItem -> {
                    ClassBasedInputDialog flowInputDialog = new ClassBasedInputDialog(flowDetail.getFlowInputClass(), "Start Flow", o -> {
                        FlowProgressHandle handle = rpcOps.startTrackedFlowDynamic((Class<? extends FlowLogic<?>>) flowDetail.getFlowClass(), o);
                        handle.getProgress().subscribe(System.out::println);
                        Notifications.showSuccess("Flow Executed");
                    });
                    UI.getCurrent().addWindow(flowInputDialog);
                });
            } catch (ClassNotFoundException | IllegalArgumentException e) {
            }
        });
    }
}
