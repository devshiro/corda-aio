package com.github.devshiro.framr.core;

import com.github.devshiro.framr.annotation.FramrApplication;
import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.google.common.reflect.ClassPath;
import io.github.classgraph.ScanResult;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@FramrApplication(packageName = "com.github.devshiro.framr.demo")
public class FramrClassCollectorTest implements WithAssertions {

    @Test
    public void testEntityClassIsOnClasspath() throws IOException {
        Optional<ClassPath.ClassInfo> exampleEntityInfo = ClassPath.from(this.getClass().getClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getName().equals(ExampleEntity.class.getName()))
                .findAny();

        assertThat(exampleEntityInfo).isPresent();
    }

    @Test
    public void testClassCollectorFindsClassesInPkg() {
        FramrClassCollector classCollector = new FramrClassCollector(this);
        ScanResult result = classCollector.scan("com.github.devshiro.framr.demo");
        assertThat(result.getAllClasses().toArray()).isNotEmpty();
    }

    @Test
    public void testClassCollectorFindsEntityClassesByAnnotation() {
        FramrClassCollector classCollector = new FramrClassCollector(this);
        List<Class<?>> entityClasses = classCollector.getEntityClasses();
        assertThat(entityClasses).containsExactly(ExampleEntity.class);
    }
}
