package io.github.devbhuwan.data.repository.generator.plugin;

import io.github.devbhuwan.data.repository.generator.provider.ClassPathScanningProvider;
import io.github.devbhuwan.data.repository.generator.support.PluginTemplateSupport;
import io.github.devbhuwan.data.repository.generator.util.Constants;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;

import java.util.Collections;

@Mojo(name = DataRepositoryGeneratorMojo.MOJO_GOAL, defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresDependencyResolution = ResolutionScope.TEST, requiresDependencyCollection = ResolutionScope.TEST)
public class DataRepositoryGeneratorMojo extends CommonsMojo {

    public static final String MOJO_GOAL = "generate-repositories";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        PluginLogger.configure(getLog());
        this.validateField(Constants.OVERWRITE);
        this.validateField(Constants.AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY);
        this.validateField(Constants.SOURCE_REPOSITORIES_GENERATED_DIRECTOR);
        this.validateField(Constants.TEST_REPOSITORIES_GENERATED_DIRECTOR);
        try {
            compileBeforeGenerateIgnoreFailOnError();

            ClassPathScanningProvider withSources = new ClassPathScanningProvider(getMavenProject().getBuild().getOutputDirectory(), this);
            ClassPathScanningProvider withTestSources = new ClassPathScanningProvider(getMavenProject().getBuild().getOutputDirectory(), this);

            PluginTemplateSupport pluginTemplateSupport = new PluginTemplateSupport();

            pluginTemplateSupport.initializeCreation(sourceRepositoriesGeneratedDirector.getAbsolutePath(), withSources.getCandidates());
            pluginTemplateSupport.initializeCreation(testRepositoriesGeneratedDirector.getAbsolutePath(), withTestSources.getCandidates());

            PluginLogger.printGeneratedTables(true);

        } catch (Exception e) {
            PluginLogger.addError(e.getMessage());
            throw new PluginMojoException(e.getMessage(), e);
        }
    }

    private void compileBeforeGenerateIgnoreFailOnError() {
        try {
            System.setProperty("maven.home", "/home/devbhuwan/.sdkman/candidates/maven/3.5.0");
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(getMavenProject().getFile());
            request.setGoals(Collections.singletonList("compile -DfailOnError=false"));
            Invoker invoker = new DefaultInvoker();
            invoker.execute(request);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
