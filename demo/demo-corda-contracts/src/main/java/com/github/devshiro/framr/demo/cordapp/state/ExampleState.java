package com.github.devshiro.framr.demo.cordapp.state;

import com.github.devshiro.framr.annotation.FramrState;
import com.github.devshiro.framr.demo.cordapp.contract.DemoContract;
import com.github.devshiro.framr.demo.cordapp.schema.DemoSchemaV1;
import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Query;
import java.util.List;

@Data
@AllArgsConstructor
@BelongsToContract(DemoContract.class)
@FramrState
public class ExampleState implements LinearState, QueryableState {

    private final Integer value;
    private final Party issuer;
    private final UniqueIdentifier linearId;


    @NotNull
    @Override
    public PersistentState generateMappedObject(@NotNull MappedSchema schema) {
        if (schema instanceof DemoSchemaV1) {
            return new ExampleEntity(
                    issuer.getName().toString(),
                    value,
                    linearId.getId()
            );
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new DemoSchemaV1());
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(issuer);
    }
}
