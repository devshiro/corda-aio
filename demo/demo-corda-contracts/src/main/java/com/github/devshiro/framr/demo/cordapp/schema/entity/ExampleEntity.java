package com.github.devshiro.framr.demo.cordapp.schema.entity;

import com.github.devshiro.framr.annotation.FramrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "openfleet_example_entity")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@FramrEntity
public class ExampleEntity extends PersistentState {
    @Column(name = "issuer")
    private String issuer;

    @Column(name = "value")
    private int value;

    @Column(name = "linear_id")
    private UUID linearId;
}
