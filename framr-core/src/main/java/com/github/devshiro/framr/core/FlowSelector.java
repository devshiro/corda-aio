package com.github.devshiro.framr.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.LinkedList;
import java.util.List;

public class FlowSelector extends Accordion {

    private final FramrClassCollector classCollector;

    private final List<Component> elements = new LinkedList<>();

    public FlowSelector(FramrClassCollector classCollector) {
        super();
        this.classCollector = classCollector;
        init();
    }

    public FlowSelector() {
        super();
        this.classCollector = new FramrClassCollector(this);
        init();
    }

    private void init() {
        classCollector.getCachedFlowClasses().forEach(klass -> {
            Button flowButton = new Button();
            flowButton.setText(klass.getSimpleName());
            elements.add(flowButton);
        });

        VerticalLayout flowBtns = new VerticalLayout();
        elements.forEach(flowBtns::add);
        AccordionPanel panel = new AccordionPanel("Startable Flows", flowBtns);
        add(panel);
    }


}
