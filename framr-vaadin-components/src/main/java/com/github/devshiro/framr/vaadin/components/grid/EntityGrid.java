package com.github.devshiro.framr.vaadin.components.grid;

import com.vaadin.flow.component.grid.Grid;
import lombok.Getter;

import java.util.List;

public class EntityGrid<T> extends Grid<T> {

    @Getter
    private List<T> entityList;

    public EntityGrid(Class<T> entityClass, List<T> entities) {
        super(entityClass);
        this.entityList = entities;
        setItems(entities);
    }
}
