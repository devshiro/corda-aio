package com.github.devshiro.framr.modules.common.corda.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Flow;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkflowCordapp extends CordappBase {

    private final List<Flow> flows;

    @Builder
    public WorkflowCordapp(String vendor, String name, String jarName, String version, List<Flow> flows) {
        super(vendor, name, jarName, version, CordappType.WORKFLOW);
        this.flows = flows;
    }

    @Override
    public Cordapp setReference() {
        flows.forEach(flow -> flow.setCordapp(this));
        return this;
    }
}
