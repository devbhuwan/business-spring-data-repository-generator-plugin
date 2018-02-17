package io.github.devbhuwan.data.repository.generator.processors;

import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.processors.maker.DataRepositorySourceMaker;

/**
 * @author Bhuwan Prasad Upadhyay
 */
public class PluginGeneratorProcessor {

    private final DataRepositorySourceMaker dataRepositorySourceMaker = new DataRepositorySourceMaker();

    public void generate(String generatedOutputDirectory, DefinitionDto definitionDto) {
        dataRepositorySourceMaker.generate(generatedOutputDirectory, definitionDto);
    }


}