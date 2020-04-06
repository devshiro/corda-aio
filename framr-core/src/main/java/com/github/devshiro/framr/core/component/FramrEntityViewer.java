package com.github.devshiro.framr.core.component;

import com.github.devshiro.framr.core.classcollector.FramrClassCollector;
import com.github.devshiro.framr.core.component.grid.EntityGrid;
import com.github.devshiro.framr.core.configuration.FramrInitizr;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.datasource.HibernateProvider;
import com.vaadin.ui.TabSheet;
import org.hibernate.Session;

public class FramrEntityViewer extends TabSheet implements Closeable {

    private FramrClassCollector classCollector;

    private CordaNodeDetails nodeDetails;

    private Session hibernateSession;

    public FramrEntityViewer(CordaNodeDetails nodeDetails) {
        classCollector = FramrInitizr.getInstance().getClassCollector();
        this.nodeDetails = nodeDetails;
        generateTabs();
        setWidthFull();
    }

    private void generateTabs() {
        hibernateSession = HibernateProvider.get(nodeDetails, classCollector.getCachedEntityClasses());
        classCollector.getCachedEntityClasses().forEach(entityClass -> {
            EntityGrid entityGrid = new EntityGrid(entityClass, hibernateSession);
            addTab(entityGrid, entityClass.getSimpleName());
        });
    }

    @Override
    public void close() {
        // For cleanup
        hibernateSession.close();
    }
}
