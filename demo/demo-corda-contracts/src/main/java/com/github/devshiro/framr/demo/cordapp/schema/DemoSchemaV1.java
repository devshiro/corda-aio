package com.github.devshiro.framr.demo.cordapp.schema;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;

public class DemoSchemaV1 extends MappedSchema {
    public DemoSchemaV1() {
        super(DemoSchema.class, 1, ImmutableList.of(ExampleEntity.class));
    }
}
