package com.github.devshiro.framr.core.component.grid;

import com.github.devshiro.framr.core.repository.CordaStateRepository;
import com.github.devshiro.framr.core.repository.RepositorySupplier;
import com.vaadin.ui.Grid;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class EntityGrid<T> extends Grid<T> {

    @Getter
    private final List<T> entityList = new ArrayList<>();

    private CordaStateRepository<T, ?> repository;

    private final List<Grid.Column<T, ?>> definedColumns;

    public EntityGrid(Class<T> entityClass) {
        super(entityClass);
        repository = RepositorySupplier.getInstance().get(entityClass);
        definedColumns = getColumns();
    }
}
