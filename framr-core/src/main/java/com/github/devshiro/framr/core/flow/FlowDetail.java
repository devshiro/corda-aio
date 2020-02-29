package com.github.devshiro.framr.core.flow;

import com.github.devshiro.framr.annotation.FramrStartableFlow;
import lombok.Data;

@Data
public class FlowDetail {

    private String displayName;

    private Class<?> flowInputClass;

    public static FlowDetail fromClass(Class<?> klass) {
        FramrStartableFlow flowAnnotation = klass.getAnnotation(FramrStartableFlow.class);
        if (flowAnnotation == null) {
            throw new IllegalArgumentException("Provided class does not have @FramrStartableFlow annotation");
        }
        FlowDetail flowDetail = new FlowDetail();
        flowDetail.setDisplayName(flowAnnotation.displayName());
        flowDetail.setFlowInputClass(flowAnnotation.inputClass());
        return flowDetail;
    }
}
