package com.github.devshiro.framr.app.ui;

import com.github.devshiro.framr.core.component.FramrFlowSelector;
import com.github.devshiro.framr.core.component.view.CordaNodeLayoutBase;

public class NodeView extends CordaNodeLayoutBase {

    @Override
    protected void setContent() {
        FramrFlowSelector flowSelector = new FramrFlowSelector(rpcOps);
        addComponent(flowSelector);
    }
}
