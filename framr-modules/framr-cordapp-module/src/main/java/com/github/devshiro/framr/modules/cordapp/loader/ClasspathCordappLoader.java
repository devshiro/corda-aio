package com.github.devshiro.framr.modules.cordapp.loader;

import com.github.devshiro.framr.annotation.FramrContract;
import com.github.devshiro.framr.annotation.FramrEntity;
import com.github.devshiro.framr.annotation.FramrStartableFlow;
import com.github.devshiro.framr.annotation.FramrState;
import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.*;
import com.github.devshiro.framr.modules.common.corda.exception.ModuleRuntimeException;
import com.google.common.collect.ImmutableList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.*;

public class ClasspathCordappLoader extends LoaderBase {

    private ClasspathCordappLoader() {

    }

    private static volatile ClasspathCordappLoader _instance;

    public static synchronized ClasspathCordappLoader getInstance() {
        if (_instance == null) {
            _instance = new ClasspathCordappLoader();
        }
        return _instance;
    }

    @Override
    public Cordapp loadFromClasspath(String cordappName) {
        return load(ClassLoader.getSystemClassLoader(), cordappName);
    }

    private Cordapp load(ClassLoader cl, String jarName) {
        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .addClassLoader(cl)
                .whitelistJars(jarName);
        ScanResult cordappScan = classGraph.scan();
        ClassInfoList flowClassInfos = cordappScan.getClassesWithAnnotation(FramrStartableFlow.class.getName());
        ClassInfoList entityClassInfos = cordappScan.getClassesWithAnnotation(FramrEntity.class.getName());
        ClassInfoList stateClassInfos = cordappScan.getClassesWithAnnotation(FramrState.class.getName());
        ClassInfoList contractClassInfos = cordappScan.getClassesWithAnnotation(FramrContract.class.getName());

        boolean hasFlows = flowClassInfos.size() > 0;
        boolean hasContracts = entityClassInfos.size() > 0 || stateClassInfos.size() > 0 || contractClassInfos.size() > 0;

        Set<File> cordappJarsFromClassInfos = new HashSet<>();
        collectJarFilesFromClassInfo(cordappJarsFromClassInfos, flowClassInfos);
        collectJarFilesFromClassInfo(cordappJarsFromClassInfos, entityClassInfos);
        collectJarFilesFromClassInfo(cordappJarsFromClassInfos, stateClassInfos);
        collectJarFilesFromClassInfo(cordappJarsFromClassInfos, contractClassInfos);


        if (cordappJarsFromClassInfos.isEmpty()) {
            throw new ModuleRuntimeException("No JAR found with name " + jarName);
        }

        //There should be only one file present because of Jar whitelisting
        if (cordappJarsFromClassInfos.size() > 1) {
            throw new ModuleRuntimeException("Multiple jars found with name " + jarName + ". Please be more specific");
        }

        File cordappJar = cordappJarsFromClassInfos.iterator().next();

        try(JarInputStream cordappJarInput = new JarInputStream(new FileInputStream(cordappJar))) {
            Manifest mf = cordappJarInput.getManifest();

            if (hasFlows && hasContracts) {
                // This is a mixed cordapp which is baaad.
                String vendor = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Vendor"));
                String version = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Version"));
                String name = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Name"));
                return MixedCordapp.builder()
                        .vendor(vendor)
                        .version(version)
                        .name(name)
                        .jarName(cordappJar.getName())
                        .entities(getEntitiesFromClassInfo(entityClassInfos))
                        .flows(getFlowsFromClassInfo(flowClassInfos))
                        .states(getStatesFromClassInfo(stateClassInfos))
                        .contracts(getContractsFromClassInfo(contractClassInfos))
                        .build()
                        .setReference();
            } else if (hasFlows) {
                String vendor = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Workflow-Vendor"));
                String version = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Workflow-Version"));
                String name = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Workflow-Name"));
                return WorkflowCordapp.builder()
                        .vendor(vendor)
                        .version(version)
                        .name(name)
                        .jarName(cordappJar.getName())
                        .flows(getFlowsFromClassInfo(flowClassInfos))
                        .build()
                        .setReference();
            } else if (hasContracts) {
                String vendor = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Vendor"));
                String version = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Version"));
                String name = (String) mf.getMainAttributes().get(new Attributes.Name("Cordapp-Contract-Name"));
                return ContractCordapp.builder()
                        .vendor(vendor)
                        .version(version)
                        .name(name)
                        .jarName(cordappJar.getName())
                        .entities(getEntitiesFromClassInfo(entityClassInfos))
                        .states(getStatesFromClassInfo(stateClassInfos))
                        .contracts(getContractsFromClassInfo(contractClassInfos))
                        .build()
                        .setReference();
            } else {
                throw new ModuleRuntimeException("Cordapp doesn't have flows or contracts");
            }

        } catch (IOException e) {
            throw new ModuleRuntimeException(e.getMessage());
        }
    }

    private void collectJarFilesFromClassInfo(Set<File> files, ClassInfoList classInfos) {
        for (ClassInfo classInfo : classInfos) {
            files.add(classInfo.getClasspathElementFile());
        }
    }

    private List<Entity> getEntitiesFromClassInfo(ClassInfoList entityClassInfos) {
        return entityClassInfos.stream()
                .map(classInfo -> {
                    Class<?> entityClass = classInfo.loadClass();
                    return Entity.builder()
                            .klass(entityClass)
                            .build();
                })
                .collect(ImmutableList.toImmutableList());
    }

    private List<State> getStatesFromClassInfo(ClassInfoList entityClassInfos) {
        return entityClassInfos.stream()
                .map(classInfo -> {
                    Class<?> entityClass = classInfo.loadClass();
                    return State.builder()
                            .klass(entityClass)
                            .build();
                })
                .collect(ImmutableList.toImmutableList());
    }

    private List<Flow> getFlowsFromClassInfo(ClassInfoList entityClassInfos) {
        return entityClassInfos.stream()
                .map(classInfo -> {
                    Class<?> entityClass = classInfo.loadClass();
                    return Flow.builder()
                            .klass(entityClass)
                            .build();
                })
                .collect(ImmutableList.toImmutableList());
    }

    private List<Contract> getContractsFromClassInfo(ClassInfoList entityClassInfos) {
        return entityClassInfos.stream()
                .map(classInfo -> {
                    Class<?> entityClass = classInfo.loadClass();
                    return Contract.builder()
                            .klass(entityClass)
                            .build();
                })
                .collect(ImmutableList.toImmutableList());
    }
}
