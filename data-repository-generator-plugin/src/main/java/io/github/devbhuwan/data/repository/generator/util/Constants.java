package io.github.devbhuwan.data.repository.generator.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constants {

    public static final String OVERWRITE = "overwrite";

    public static final String PROJECT_NAME = "Data Repository Generator";
    public static final String TABLE_POSTFIX_COLUMN = "Postfix";
    public static final String TABLE_FILE_COLUMN = "File Name";
    public static final String TABLE_RESULT_COLUMN = "Result";
    public static final String SOURCE_REPOSITORIES_GENERATED_DIRECTOR = "sourceRepositoriesGeneratedDirector";
    public static final String TEST_REPOSITORIES_GENERATED_DIRECTOR = "testRepositoriesGeneratedDirector";
    public static final String AUTO_COMPILE_AND_COPY_INTO_BUILD_DIRECTORY = "autoCompileAndCopyIntoBuildDirectory";
    public static final String ROOT_PACKAGES_TO_SCAN = "rootPackagesToScan";
    public static final String INCLUDE_CURRENT_PROJECT = "includeCurrentProject";
    public static final String INCLUDE_RUNTIME_CLASSPATH = "includeRuntimeClassPath";
    private static final String VERSION = "(v1.0.0)";
    private static final SimpleDateFormat BANNNER_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy HH:mm:SS");

    private Constants() {
    }

    public static String currentGeneratorSignature() {
        StringBuilder signature = new StringBuilder();
        for (String s : generatorBanner()) signature.append("\n *").append(s);
        return signature.toString();
    }

    public static List<String> generatorBanner() {
        List<String> banner = new ArrayList<>();
        banner.add("=====================================================");
        banner.add(" DATA REPOSITORY GENERATOR " + Constants.VERSION + " ");
        banner.add(" Generated At : " + BANNNER_DATE_FORMAT.format(new Date()) + " ");
        banner.add("=====================================================");
        return banner;
    }
}

