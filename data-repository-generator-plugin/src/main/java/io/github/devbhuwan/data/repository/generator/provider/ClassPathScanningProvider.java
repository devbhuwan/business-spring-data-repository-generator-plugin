package io.github.devbhuwan.data.repository.generator.provider;


import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.plugin.CommonsMojo;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ClassPathScanningProvider {

    private final PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<DefinitionDto> definitionDtos = new ArrayList<>();

    public ClassPathScanningProvider(String compileSourcesOutputDirectory, CommonsMojo generatorMojo) {
        Optional.of(compileSourcesOutputDirectory).ifPresent(outputDirectory -> {
            try {
                if (generatorMojo.isIncludeCurrentProject()) {
                    ClassLoader urlClassLoader = new URLClassLoader(new URL[]{new File(compileSourcesOutputDirectory).toURL()});
                    collectDefinition(generatorMojo, urlClassLoader);
                }
                if (generatorMojo.isIncludeRuntimeClassPath()) {
                    collectDefinition(generatorMojo, this.getClass().getClassLoader());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        });

    }


    private void collectDefinition(CommonsMojo generatorMojo, ClassLoader urlClassLoader) throws IOException {
        for (String rootPackage : generatorMojo.uniqueRootPackages()) {
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = ClassPath.from(urlClassLoader).getTopLevelClassesRecursive(rootPackage);
            topLevelClasses.forEach(classInfo -> {
                Class<?> aClass = classInfo.load();

                definitionDtos.add(new DefinitionDto());
            });
        }
    }

    private boolean isEnableFeature(Annotation annotation) {
        return annotation != null;
    }


    public Collection<DefinitionDto> getCandidates() {
        return definitionDtos;
    }
}
