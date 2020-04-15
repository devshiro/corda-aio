package com.github.devshiro.framr.core.ui.component;

import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.modules.common.corda.component.CordappComponentBase;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.cordapp.FramrCordappModule;
import com.github.devshiro.framr.vaadin.components.panel.ListPanel;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class LoadedCordappsBar extends HorizontalLayout {

    public LoadedCordappsBar(NodeManager nodeManager) {

        List<String> cordappNames = nodeManager.getCordapps().stream().map(Cordapp::getName).collect(Collectors.toList());
        List<String> flowNames = nodeManager.getFlows().stream().map(Flow::getDisplayName).collect(Collectors.toList());
        List<String> entityNames = nodeManager.getEntities().stream().map(CordappComponentBase::getKlass).map(Class::getSimpleName).collect(Collectors.toList());
        List<String> stateNames = nodeManager.getStates().stream().map(CordappComponentBase::getKlass).map(Class::getSimpleName).collect(Collectors.toList());
        List<String> contractNames = nodeManager.getContracts().stream().map(CordappComponentBase::getKlass).map(Class::getSimpleName).collect(Collectors.toList());

        addComponentsAndExpand(
                new ListPanel("Loaded Cordapps", cordappNames),
                new ListPanel("Loaded flows", flowNames),
                new ListPanel("Loaded Entities", entityNames),
                new ListPanel("Loaded States", stateNames),
                new ListPanel("Loaded Contracts", contractNames)
        );
    }
}
