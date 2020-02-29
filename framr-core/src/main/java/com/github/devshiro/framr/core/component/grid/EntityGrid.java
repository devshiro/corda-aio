package com.github.devshiro.framr.core.component.grid;

import com.vaadin.flow.component.grid.Grid;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class EntityGrid<T> extends Grid<T> {

    @Getter
    private final List<T> entityList = new ArrayList<>();

    private final List<Grid.Column<T>> definedColumns;

    public EntityGrid(Class<T> entityClass) {
        super(entityClass);
        definedColumns = getColumns();
    }
}
