import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.plugins.JavaPlugin

class JavaModuleBasePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // Set up plugins
        // Add the java plugin
        project.getPluginManager().apply(JavaPlugin.class);
        project.setProperty("sourceCompatibility", 1.8);
        project.setProperty("targetCompatibility", 1.8);

        // Set up dependencies
        DependencySet implementation = project.getConfigurations().getByName("implementation").getDependencies()
        DependencySet testImplementation = project.getConfigurations().getByName("testImplementation").getDependencies()
        DependencySet annotationProcessor = project.getConfigurations().getByName("annotationProcessor").getDependencies()
        DependencySet testAnnotationProcessor = project.getConfigurations().getByName("testAnnotationProcessor").getDependencies()

        Dependency lombok = project.getDependencies().create("org.projectlombok:lombok:1.18.10")

        implementation.add(lombok);
        annotationProcessor.add(lombok);
        testImplementation.add(project.getDependencies().create("org.junit.jupiter:junit-jupiter:5.6.0"))
        testImplementation.add(project.getDependencies().create("org.assertj:assertj-core:3.11.1"))
        testImplementation.add(lombok)
        testAnnotationProcessor.add(lombok)
    }
}