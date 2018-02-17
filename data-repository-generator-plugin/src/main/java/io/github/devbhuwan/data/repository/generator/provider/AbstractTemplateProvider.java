package io.github.devbhuwan.data.repository.generator.provider;

import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.plugin.PluginLogger;
import io.github.devbhuwan.data.repository.generator.processors.PluginGeneratorProcessor;
import io.github.devbhuwan.data.repository.generator.util.GeneratorUtils;

import java.util.Collection;

public abstract class AbstractTemplateProvider {

    private PluginGeneratorProcessor pluginGeneratorProcessor = new PluginGeneratorProcessor();

    public void initializeCreation(String path, Collection<DefinitionDto> candidates) {
        int generatedCount = 0;

        if (!GeneratorUtils.verifyPackage(path)) {
            return;
        }

        for (DefinitionDto definitionDto : candidates) {
            PluginLogger.info("Generating Constant From Bpmn File: " + definitionDto);
            if (createHelper(path, definitionDto)) {
                generatedCount++;
            }
        }

        PluginLogger.plusGenerated(generatedCount);
    }

    private boolean createHelper(String path, DefinitionDto definitionDto) {
        pluginGeneratorProcessor.generate(path, definitionDto);
        return true;
    }


}
