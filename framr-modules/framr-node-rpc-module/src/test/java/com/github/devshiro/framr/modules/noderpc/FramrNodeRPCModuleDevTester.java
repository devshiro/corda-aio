package com.github.devshiro.framr.modules.noderpc;

import com.github.devshiro.framr.demo.cordapp.flow.ExampleFlow;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.noderpc.flow.FlowInvocation;

import java.util.UUID;

// Testing CordaRPC requires a fully working Corda node.
// Before execution, start framr-nodes-dev/build/nodes/partya/corda.jar
public class FramrNodeRPCModuleDevTester {

    public static void main(String[] args) {
        FramrNodeRPCModule module = FramrNodeRPCModule.getInstance();
        UUID nodeId = module.registerNode("localhost", 10003);
        module.connect(nodeId, "user1", "test");

        Flow flow = new Flow(ExampleFlow.Initiator.class);
        ExampleFlow.InitiatorInput initiatorInput = new ExampleFlow.InitiatorInput();
        initiatorInput.setAmount(10001);
        FlowInvocation flowInvocation = new FlowInvocation(flow, initiatorInput);

        module.startFlow(nodeId, flowInvocation);
        module.deleteNode(nodeId);
    }

}
