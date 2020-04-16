package com.github.devshiro.framr.vaadin.components.grid;

import com.vaadin.ui.Grid;
import lombok.Getter;

import java.util.List;

public class EntityGrid<T> extends Grid<T> {

    @Getter
    private List<T> entityList;

    @Getter
    private Class<T> entityClass;

    public EntityGrid(Class<T> entityClass, List<T> entities) {
        super(entityClass);
        this.entityClass = entityClass;
        this.entityList = entities;
        setItems(entities);
    }
}
