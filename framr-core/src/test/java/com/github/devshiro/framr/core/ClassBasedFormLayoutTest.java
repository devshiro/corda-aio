package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.component.layout.ClassBasedFormLayout;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class ClassBasedFormLayoutTest implements WithAssertions {

    @Data
    @AllArgsConstructor
    private class ExamplePojo {
        private String field1;
    }

    @Test
    public void cbflHasFieldsForClass() {
        ClassBasedFormLayout layout = new ClassBasedFormLayout(ExamplePojo.class);
        assertThat(layout.getPojoClass()).isEqualTo(ExamplePojo.class);
        assertThat(layout.getFields()).hasSize(1);
        layout.getFields().forEach(field -> assertThat(field).hasNoNullFieldsOrProperties());
    }

    @Test
    public void testCBFL() {
        ClassBasedFormLayout layout = new ClassBasedFormLayout(CordaNodeDetails.class);
        assertThat(layout.getFields()).hasSize(4);
    }

}
