package com.github.devshiro.framr.modules.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.common.corda.cordapp.CordappType;
import com.github.devshiro.framr.modules.common.corda.cordapp.WorkflowCordapp;
import com.github.devshiro.framr.modules.cordapp.loader.ClasspathCordappLoader;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CordappLoaderTest implements WithAssertions {

    @Test
    public void checkCordappLoaderContracts() {
        ClasspathCordappLoader loader = ClasspathCordappLoader.getInstance();
        Cordapp cordapp = loader.load("*cordapp-contracts*.jar");
        assertThat(cordapp.getType()).isEqualTo(CordappType.CONTRACT);
    }

    @Test
    public void checkCordappLoaderWorkflows() {
        ClasspathCordappLoader loader = ClasspathCordappLoader.getInstance();
        Cordapp cordapp = loader.load("demo-cordapp-workflows-*.jar");
        assertThat(cordapp).isNotNull();
        assertThat(cordapp.getType()).isEqualTo(CordappType.WORKFLOW);
        WorkflowCordapp workflowCordapp = (WorkflowCordapp) cordapp;
        assertThat(workflowCordapp.getFlows()).isNotEmpty();
        assertThat(workflowCordapp.getName()).isNotBlank();
        assertThat(workflowCordapp.getVendor()).isNotBlank();
        assertThat(workflowCordapp.getVersion()).isNotBlank();

        for (Flow flow : workflowCordapp.getFlows()) {
            assertThat(flow.getDisplayName()).isNotBlank();
            assertThat(flow.getFlowInputClass()).isNotNull();
        }
    }
}
