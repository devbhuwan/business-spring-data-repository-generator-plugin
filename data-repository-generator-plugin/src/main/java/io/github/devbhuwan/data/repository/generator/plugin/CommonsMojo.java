package io.github.devbhuwan.data.repository.generator.plugin;

import io.github.devbhuwan.data.repository.generator.util.Constants;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependencies.resolve.DependencyResolver;
import org.eclipse.aether.impl.ArtifactResolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class CommonsMojo extends AbstractMojo {

    @Parameter(name = Constants.INCLUDE_CURRENT_PROJECT, defaultValue = "true")
    protected Boolean includeCurrentProject;
    @Parameter(name = Constants.INCLUDE_RUNTIME_CLASSPATH, defaultValue = "true")
    protected Boolean includeRuntimeClassPath;
    @Parameter(name = Constants.ROOT_PACKAGES_TO_SCAN)
    protected String[] rootPackagesToScan = new String[]{"io.github.devbhuwan"};
    @Parameter(name = Constants.SOURCE_REPOSITORIES_GENERATED_DIRECTOR, defaultValue = "target/generated-sources/annotations")
    protected File sourceRepositoriesGeneratedDirector;
    @Parameter(name = Constants.TEST_REPOSITORIES_GENERATED_DIRECTOR, defaultValue = "target/generated-test-sources/test-annotations")
    protected File testRepositoriesGeneratedDirector;
    @Parameter(name = Constants.OVERWRITE, defaultValue = "false")
    private Boolean overwrite;
    @Parameter(name = Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY, defaultValue = "true")
    private Boolean autoCompileAndCopyIntoBuildDirectory;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    @Component
    private BuildPluginManager pluginManager;

    /**
     *
     */
    @Component
    private ArtifactFactory artifactFactory;

    /**
     *
     */
    @Component
    private ArtifactResolver artifactResolver;
    @Component
    private DependencyResolver dependencyResolver;


    public ArtifactResolver getArtifactResolver() {
        return artifactResolver;
    }

    public DependencyResolver getDependencyResolver() {
        return dependencyResolver;
    }

    public void validateField(String parameter) throws PluginMojoException {
        boolean errorFound = Boolean.FALSE;
        switch (parameter) {
            case Constants.OVERWRITE:
                if (overwrite == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.SOURCE_REPOSITORIES_GENERATED_DIRECTOR:
                if (sourceRepositoriesGeneratedDirector == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.TEST_REPOSITORIES_GENERATED_DIRECTOR:
                if (testRepositoriesGeneratedDirector == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY:
                if (autoCompileAndCopyIntoBuildDirectory == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            default:
                PluginLogger.addError(String.format("%s configuration parameter not found!", parameter));
                throw new PluginMojoException();
        }

        if (errorFound) {
            PluginLogger.addError(String.format("%s configuration not found!", parameter));
            throw new PluginMojoException();
        }
    }

    public boolean isIncludeCurrentProject() {
        return includeCurrentProject;
    }

    public Set<String> uniqueRootPackages() {
        Set<String> uniqueRootPackage = new HashSet<>();
        uniqueRootPackage.add(rootPackagesToScan[0]);
        return uniqueRootPackage;
    }

    public Boolean isIncludeRuntimeClassPath() {
        return includeRuntimeClassPath;
    }

    public MavenProject getMavenProject() {
        return mavenProject;
    }

    public MavenSession getMavenSession() {
        return mavenSession;
    }

    public BuildPluginManager getPluginManager() {
        return pluginManager;
    }

}
