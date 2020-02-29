package com.github.devshiro.framr.core.classcollector;

import com.github.devshiro.framr.annotation.FramrApplication;
import com.github.devshiro.framr.annotation.FramrEntity;
import com.github.devshiro.framr.annotation.FramrStartableFlow;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class FramrClassCollector {

    private final ClassGraph classGraph;

    private final String searchPackageName;

    @Getter
    private final List<Class<?>> cachedEntityClasses;

    @Getter
    private final List<Class<?>> cachedFlowClasses;

    public FramrClassCollector(Object context) {
        searchPackageName = findSearchPackageNameFromAnnotation(context.getClass());
        classGraph = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo();
        cachedEntityClasses = getEntityClasses();
        cachedFlowClasses = getFlowClasses();
    }

    public ScanResult scan(String whitelistPkg) {
        return classGraph.whitelistPackages(whitelistPkg).scan();
    }

    public ScanResult scan() {
        return classGraph.whitelistPackages(searchPackageName).scan();
    }

    public List<Class<?>> getEntityClasses() {
        return scan()
                .getClassesWithAnnotation(FramrEntity.class.getName())
                .loadClasses();
    }

    public List<Class<?>> getFlowClasses() {
        return scan()
                .getClassesWithAnnotation(FramrStartableFlow.class.getName())
                .loadClasses();
    }

    private String findSearchPackageNameFromAnnotation(@NonNull Class contextClass) {
        FramrApplication framrApplicationAnnotation = (FramrApplication) contextClass.getDeclaredAnnotation(FramrApplication.class);
        if (framrApplicationAnnotation == null) {
            throw new IllegalArgumentException("Missing @FramrApplication annotation from parent class");
        }
        return framrApplicationAnnotation.packageName();
    }
}
