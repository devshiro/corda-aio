package com.github.devshiro.framr.vaadin.components.layout;

import com.github.devshiro.framr.vaadin.components.mapper.StringInputMapper;
import com.github.devshiro.framr.vaadin.components.mapper.StringMapper;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassBasedFormLayout extends FormLayout {

    @Data
    public class ClassField {
        private final String fieldName;
        private final Method setter;
        private TextField valueTextField;
    }

    @Getter
    private final Class<?> pojoClass;

    @Getter
    private final List<ClassField> fields;

    public ClassBasedFormLayout(Class<?> pojo) {
        super();
        fields = new LinkedList<>();
        pojoClass = pojo;
        extractFields();
        setupForm();
    }

    public <T> T readForm(StringInputMapper<T> inputMapper) {
        try {
            Constructor constructor = pojoClass.getConstructor();
            Object createdPojo = constructor.newInstance();


            for (ClassField field : fields) {
                String fieldValue = field.getValueTextField().getValue() == null ? new String("") : field.getValueTextField().getValue();
                Method setterMethod = field.getSetter();
                Class<?> typeClass = setterMethod.getParameterTypes()[0];
                T mappedValue = inputMapper.map((Class<T>) typeClass, fieldValue);
                setterMethod.invoke(createdPojo, mappedValue);
            }
            return (T) createdPojo;
        }
        catch (InstantiationException ie) {
            if(ie.getCause().getClass().equals(NoSuchMethodException.class)) {
                throw new RuntimeException("Missing method " + ie.getCause().getMessage());
            } else {
                throw new RuntimeException(ie);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object readForm() {
        try {
            Object createdPojo = pojoClass.newInstance();
            for (ClassField field : fields) {
                String fieldValue = field.getValueTextField().getValue() == null ? "" : field.getValueTextField().getValue();
                Method setterMethod = field.getSetter();
                Class<?> typeClass = setterMethod.getParameterTypes()[0];
                setterMethod.invoke(createdPojo, StringMapper.map(typeClass, fieldValue));
            }
            return createdPojo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupForm() {
        fields.stream()
                .forEachOrdered(field -> {
                    TextField textField = new TextField();
                    textField.setCaption(field.getFieldName());
                    field.setValueTextField(textField);
                    addComponent(textField);
                });
    }

    private void extractFields() {
        List<Field> declaredFields = Arrays.asList(pojoClass.getDeclaredFields());
        List<ClassField> classFields = Arrays.stream(pojoClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("set"))
                .map(method -> {
                    String fieldName = method.getName().substring(3);
                    return new ClassField(fieldName, method);
                }).collect(Collectors.toList());
        for (Field declaredField : declaredFields) {
            classFields.stream().filter(cf -> cf.getFieldName().equalsIgnoreCase(declaredField.getName())).findFirst().ifPresent(fields::add);
        }
    }
}