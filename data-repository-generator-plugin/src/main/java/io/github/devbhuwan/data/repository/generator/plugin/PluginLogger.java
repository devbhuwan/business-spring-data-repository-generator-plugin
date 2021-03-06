package io.github.devbhuwan.data.repository.generator.plugin;

import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWordMin;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import io.github.devbhuwan.data.repository.generator.processors.PluginGeneratorProcessor;
import io.github.devbhuwan.data.repository.generator.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PluginLogger {

    private static final Log commonsLogger = LogFactory.getLog(PluginGeneratorProcessor.class);
    private static org.apache.maven.plugin.logging.Log mavenLogger;
    private static int generated = 0;
    private static List<String> errors = new ArrayList<>();
    private static List<String> warns = new ArrayList<>();
    private static AsciiTable generatedTable;

    private PluginLogger() {
    }

    public static void configure(org.apache.maven.plugin.logging.Log log) {
        mavenLogger = log;
    }

    public static void info(String message) {
        if (mavenLogger == null) {
            commonsLogger.info(message);
            return;
        }
        mavenLogger.info(message);
    }

    public static void debug(String message) {
        if (mavenLogger == null) {
            commonsLogger.debug(message);
            return;
        }
        mavenLogger.debug(message);
    }

    public static void error(String message) {
        if (mavenLogger == null) {
            commonsLogger.error(message);
            return;
        }
        mavenLogger.error(message);
    }

    public static Integer addError(String message) {
        errors.add(message);
        return errors.size();
    }

    public static Integer addWarn(String warn) {
        warns.add(warn);
        return warns.size();
    }

    private static void printErrors() {
        printGenericTable("Errors", errors);
    }

    private static void printWarns() {
        printGenericTable("Warnings", warns);
    }

    private static void printGenericTable(String title, List<String> messages) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, title + ": " + messages.size()).getCells().get(1).getContext().setTextAlignment(TextAlignment.CENTER);
        table.addRule();

        int count = 1;
        for (String mess : messages) {
            table.addRow("#" + count, mess).getCells().get(0).getContext().setTextAlignment(TextAlignment.CENTER);
            table.addRule();
            count++;
        }

        table.getRenderer().setCWC(new CWC_LongestWordMin(new int[]{5, 101}));
        table.renderAsCollection().forEach(PluginLogger::info);
    }

    public static void addRowGeneratedTable(Object... columns) {
        if (generatedTable == null) {
            initializeTable();
        }
        generatedTable.addRule();
        AT_Row row = generatedTable.addRow(columns);

        if (columns[0] != null) {
            row.getCells().get(0).getContext().setTextAlignment(TextAlignment.CENTER);
        }


        if (columns[2] != null) {
            row.getCells().get(2).getContext().setTextAlignment(TextAlignment.CENTER);
        }
    }

    public static void printGeneratedTables(boolean debug) {
        if (debug && generatedTable == null) {
            initializeTable();
        }

        if (generatedTable != null) {
            printBanner();
            info("");
            if (generated > 0) {
                addRowGeneratedTable(null, null, generated + " files generated");
            } else {
                addRowGeneratedTable(null, null, "No files generated");
            }
            generatedTable.addRule();
            generatedTable.getRenderer().setCWC(new CWC_LongestWordMin(new int[]{20, 68, 17}));
            generatedTable.renderAsCollection().forEach(PluginLogger::info);

            info("");

            if (!warns.isEmpty()) {
                printWarns();
            }

        } else {
            info(String.format("\u001B[1m\u001B[43m  %s:\u001B[0m\u001B[43m %s files generated  \u001B[0m", Constants.PROJECT_NAME, generated));
        }

        if (!errors.isEmpty()) {
            info("");
            printErrors();
            info("");
        }
    }

    private static void initializeTable() {
        generatedTable = new AsciiTable();
        generatedTable.addRule();
        AT_Row header = generatedTable.addRow(Constants.TABLE_POSTFIX_COLUMN, Constants.TABLE_FILE_COLUMN, Constants.TABLE_RESULT_COLUMN);
        header.getCells().forEach(c -> c.getContext().setTextAlignment(TextAlignment.CENTER));
    }

    public static void plusGenerated(int plus) {
        generated += plus;
    }

    private static void printBanner() {
        Constants.generatorBanner().forEach(PluginLogger::info);
    }


}
