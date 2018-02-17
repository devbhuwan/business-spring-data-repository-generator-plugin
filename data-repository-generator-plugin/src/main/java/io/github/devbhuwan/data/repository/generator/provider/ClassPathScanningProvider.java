package io.github.devbhuwan.data.repository.generator.provider;


import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.github.devbhuwan.data.definition.BusinessDomain;
import io.github.devbhuwan.data.definition.CoreDomain;
import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.plugin.CommonsMojo;
import io.github.devbhuwan.data.repository.generator.plugin.GeneratorScope;
import org.apache.maven.model.Plugin;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.artifact.resolve.ArtifactResult;
import org.springframework.core.GenericTypeResolver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPathScanningProvider {

    private static final Map<String, Boolean> CACHE_FOR_ALREADY_GENERATED_CLASSES = new ConcurrentHashMap<>();
    private List<DefinitionDto> definitionDtos = new ArrayList<>();

    public ClassPathScanningProvider(String compileSourcesOutputDirectory, CommonsMojo generatorMojo, GeneratorScope generatorScope) {
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
                    artifactResults.forEach(artifactResult -> {
                        if (generatorScope.getScope().equals(artifactResult.getArtifact().getScope()))
                            runtimeDependencies.add(artifactResult.getArtifact().getFile());
                        else if (generatorScope.getScope().equals("compile") && artifactResult.getArtifact().getScope() == null)
                            runtimeDependencies.add(artifactResult.getArtifact().getFile());
                    });
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
                BusinessDomain businessDomain = aClass.getAnnotation(BusinessDomain.class);
                try {
                    if (businessDomain == null) {
                        aClass = Class.forName(aClass.getName());
                        businessDomain = aClass.getAnnotation(BusinessDomain.class);
                    }
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
                if (businessDomain != null && !CACHE_FOR_ALREADY_GENERATED_CLASSES.containsKey(aClass.getName())) {
                    DefinitionDto definitionDto = new DefinitionDto();
                    definitionDto.setClassName(aClass.getSimpleName());
                    definitionDto.setClassPackage(aClass.getPackage().getName());
                    Class<?> idClass = GenericTypeResolver.resolveTypeArgument(aClass, CoreDomain.class);
                    definitionDto.setIdClassName(idClass.getSimpleName());
                    definitionDto.setIdClassPackage(idClass.getPackage().getName());
                    definitionDtos.add(definitionDto);
                    CACHE_FOR_ALREADY_GENERATED_CLASSES.put(aClass.getName(), true);
                }
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
