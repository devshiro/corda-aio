package com.github.devshiro.framr.modules.noderpc.flow;

import com.github.devshiro.framr.modules.common.corda.component.Flow;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
public class FlowInvocation {
    private final Flow flow;
    private final Object arg; // .getClass() should be equal to flow.getInputClass()

    public FlowInvocation(@NonNull Flow flow, Object arg) {
        if (flow.getFlowInputClass() != null) {
            Objects.requireNonNull(arg, "Provided flow argument must not be null when Flow::getInputClass() not null");
        }
        if (!arg.getClass().equals(flow.getFlowInputClass())) {
            throw new IllegalArgumentException("Flow argument should be same as flow input class: " + arg.getClass().getName() + " != " + flow.getFlowInputClass().getName());
        }
        this.flow = flow;
        this.arg = arg;
    }
}
