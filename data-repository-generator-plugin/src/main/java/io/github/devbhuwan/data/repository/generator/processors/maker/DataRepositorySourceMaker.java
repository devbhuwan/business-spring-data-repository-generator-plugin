package io.github.devbhuwan.data.repository.generator.processors.maker;

public class DataRepositorySourceMaker extends SourceMaker {

    @Override
    String postfix() {
        return "DataRepository";
    }

    @Override
    String templatePath() {
        return "templates/repository/DataRepository.java";
    }

    @Override
    String subpackagePath() {
        return "repositories";
    }

}
