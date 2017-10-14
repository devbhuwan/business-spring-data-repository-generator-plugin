package io.github.devbhuwan.data.repository.generator.processors.maker;

import freemarker.template.*;
import io.github.devbhuwan.data.repository.generator.dto.DefinitionDto;
import io.github.devbhuwan.data.repository.generator.plugin.PluginLogger;
import io.github.devbhuwan.data.repository.generator.processors.PluginGeneratorProcessor;
import io.github.devbhuwan.data.repository.generator.util.Constants;
import io.github.devbhuwan.data.repository.generator.util.GeneratorUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

public abstract class SourceMaker {
    private final Configuration templateCfg = new Configuration(new Version("2.3.26"));

    SourceMaker() {
        templateCfg.setDefaultEncoding("UTF-8");
        templateCfg.setIncompatibleImprovements(new Version(2, 3, 20));
        templateCfg.setClassForTemplateLoading(PluginGeneratorProcessor.class, "/");
        templateCfg.setLocale(Locale.US);
        templateCfg.setObjectWrapper(new DefaultObjectWrapperBuilder(templateCfg.getIncompatibleImprovements()).build());
        templateCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }


    private String buildGeneratedSourcePath(String pathWithPackage, String fileName) {
        return pathWithPackage + File.separator + fileName;
    }

    private String buildJavaFileName(String className, String postfix) {
        return className + postfix + ".java";
    }

    private String buildPathWithPackage(String generatedOutputDirectory, String classPackage) {
        String subpackagePath = StringUtils.isNoneBlank(subpackagePath()) ? File.separator + subpackagePath() : "";
        return (generatedOutputDirectory + File.separator + classPackage).replace(".", File.separator) + subpackagePath;
    }

    private void make(String generatedOutputDirectory, DefinitionDto definitionDto) {
        try {
            String pathWithPackage = buildPathWithPackage(generatedOutputDirectory, definitionDto.getClassPackage());
            if (!GeneratorUtils.verifyPackage(pathWithPackage))
                return;
            Template template = templateCfg.getTemplate(templatePath());
            String fileName = buildJavaFileName(definitionDto.getClassName(), postfix());
            template.process(definitionDto, new FileWriter(buildGeneratedSourcePath(pathWithPackage, fileName)));
            PluginLogger.addRowGeneratedTable(postfix(), fileName, "Ok");
            //PluginLogger.plusGenerated(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void generate(String generatedOutputDirectory, DefinitionDto definitionDto) {
        definitionDto.validate();
        definitionDto.setGeneratorSignature(Constants.currentGeneratorSignature());
        make(generatedOutputDirectory, definitionDto);
    }

    abstract String postfix();

    abstract String templatePath();

    abstract String subpackagePath();
}
