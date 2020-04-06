package com.github.devshiro.framr.core.component.grid;

import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.datasource.HibernateProvider;
import com.github.devshiro.framr.core.repository.CordaStateRepository;
import com.github.devshiro.framr.core.repository.RepositorySupplier;
import com.vaadin.ui.Grid;
import lombok.Getter;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntityGrid<T> extends Grid<T> {

    @Getter
    private List<T> entityList;

    private Session session;

    private final List<Grid.Column<T, ?>> definedColumns;

    Class<T> entityClass;

    public EntityGrid(Class<T> entityClass, Session hibernateSession) {
        super(entityClass);
        this.entityClass = entityClass;
        definedColumns = getColumns();
        entityList = fetchItems();
        setItems(entityList);
    }

    private List<T> fetchItems() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root root = cq.from(entityClass);
        cq.select(root);
        return session.createQuery(cq).getResultList();
    }
}
