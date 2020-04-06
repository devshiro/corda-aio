package com.github.devshiro.framr.app.ui;

import com.github.devshiro.framr.core.component.FramrEntityViewer;
import com.github.devshiro.framr.core.component.view.CordaNodeLayoutBase;
import com.github.devshiro.framr.core.datasource.HibernateProvider;
import org.hibernate.Session;

public class NodeView extends CordaNodeLayoutBase {

    private FramrEntityViewer framrEntityViewer;

    @Override
    protected void setContent() {
        framrEntityViewer = new FramrEntityViewer(nodeDetails);
        addComponent(framrEntityViewer);
    }

    @Override
    public void close() {
        // Free up resources
        framrEntityViewer.close();
        framrEntityViewer = null;
    }
}
