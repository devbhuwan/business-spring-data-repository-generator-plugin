package io.github.devbhuwan.data.repository.generator.provider;


import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.plugin.CommonsMojo;
import org.apache.maven.model.Plugin;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.artifact.resolve.ArtifactResult;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class ClassPathScanningProvider {

    private List<DefinitionDto> definitionDtos = new ArrayList<>();

    public ClassPathScanningProvider(String compileSourcesOutputDirectory, CommonsMojo generatorMojo) {
        Optional.of(compileSourcesOutputDirectory).ifPresent(outputDirectory -> {
            try {
                if (generatorMojo.isIncludeCurrentProject()) {
                    collectDefinition(generatorMojo, Collections.singleton(new File(compileSourcesOutputDirectory)));
                }
                if (generatorMojo.isIncludeRuntimeClassPath()) {
                    Plugin plugin = generatorMojo.getMavenProject().getPlugin("io.github.devbhuwan:data-repository-generator-plugin");
                    Set<File> runtimeDependencies = new HashSet<>();
                    ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(generatorMojo.getMavenSession().getProjectBuildingRequest());
                    buildingRequest.setRemoteRepositories(generatorMojo.getMavenProject().getPluginArtifactRepositories());
                    buildingRequest.setProject(generatorMojo.getMavenProject());
                    buildingRequest.setLocalRepository(generatorMojo.getMavenSession().getLocalRepository());
                    Iterable<ArtifactResult> artifactResults = generatorMojo.getDependencyResolver().resolveDependencies(buildingRequest, plugin.getDependencies(), null, null);
                    artifactResults.forEach(artifactResult -> runtimeDependencies.add(artifactResult.getArtifact().getFile()));
                    collectDefinition(generatorMojo, runtimeDependencies);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        });

    }


    private void collectDefinition(CommonsMojo generatorMojo, Set<File> dependencies) throws IOException {
        for (String rootPackage : generatorMojo.uniqueRootPackages()) {
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = ClassPath.from(buildClassLoader(dependencies)).getTopLevelClassesRecursive(rootPackage);
            topLevelClasses.forEach(classInfo -> {
                Class<?> aClass = classInfo.load();

                definitionDtos.add(new DefinitionDto());
            });
        }
    }

    private ClassLoader buildClassLoader(Set<File> dependencies) {
        try {
            URL[] urls = new URL[dependencies.size()];
            int i = 0;
            for (File file : dependencies) {
                urls[i++] = file.toURI().toURL();
            }
            return new URLClassLoader(urls);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean isEnableFeature(Annotation annotation) {
        return annotation != null;
    }


    public Collection<DefinitionDto> getCandidates() {
        return definitionDtos;
    }
}
