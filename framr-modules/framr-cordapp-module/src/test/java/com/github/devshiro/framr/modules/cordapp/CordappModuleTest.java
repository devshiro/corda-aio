package com.github.devshiro.framr.modules.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.ContractCordapp;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.common.corda.cordapp.CordappType;
import com.github.devshiro.framr.modules.common.corda.cordapp.WorkflowCordapp;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CordappModuleTest implements WithAssertions {

    private FramrCordappModule module;

    private ContractCordapp contractCordapp;
    private WorkflowCordapp workflowCordapp;

    @BeforeAll
    public void setup() {
        module = FramrCordappModule.getInstance();
        assertThatCode(() -> module.loadCordapp("demo-cordapp-workflows*.jar")).doesNotThrowAnyException();
        assertThatCode(() -> module.loadCordapp("demo-cordapp-contracts*.jar")).doesNotThrowAnyException();

        List<Cordapp> contractCordapps = module.getCordappsByType(CordappType.CONTRACT);
        assertThat(contractCordapps).hasSize(1);
        contractCordapp = (ContractCordapp) contractCordapps.get(0);
        assertThat(contractCordapp).isNotNull();

        List<Cordapp> workflowCordapps = module.getCordappsByType(CordappType.WORKFLOW);
        assertThat(workflowCordapps).hasSize(1);
        workflowCordapp = (WorkflowCordapp) workflowCordapps.get(0);
        assertThat(workflowCordapp).isNotNull();
    }

    @Test
    public void moduleSetupTest() {
        assertThat(module).isNotNull();
        assertThat(module.getTYPE()).isEqualTo(FramrCordappModule.TYPE_ID);
    }

    @Test
    public void testCordapps() {
        assertThat(module.getCordapps()).isNotEmpty().hasSize(2);
    }

    @Test
    public void testFlows() {
        List<Flow> flows = module.getFlows();
        assertThat(flows).isNotEmpty();
        assertThat(flows).allSatisfy(flow -> assertThat(flow.getCordapp()).isEqualTo(workflowCordapp));
        assertThat(flows).allSatisfy(flow -> assertThat(flow.getFlowInputClass()).isNotNull());
        assertThat(flows).allSatisfy(flow -> assertThat(flow.getDisplayName()).isNotBlank());
    }

    @Test
    public void testEntities() {
        List<Entity> entities = module.getEntities();
        assertThat(entities).isNotEmpty();
        assertThat(entities).allSatisfy(entity -> assertThat(entity.getCordapp()).isEqualTo(contractCordapp));
    }

    @Test
    public void testStates() {
        List<State> states = module.getStates();
        assertThat(states).isNotEmpty();
        assertThat(states).allSatisfy(state -> assertThat(state.getCordapp()).isEqualTo(contractCordapp));
    }

    @Test
    public void testContracts() {
        List<Contract> contracts = module.getContracts();
        assertThat(contracts).isNotEmpty();
        assertThat(contracts).allSatisfy(contract -> assertThat(contract.getCordapp()).isEqualTo(contractCordapp));
    }
}
