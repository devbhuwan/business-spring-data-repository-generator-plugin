package io.github.devbhuwan.data.repository.generator.plugin;

import org.apache.maven.plugin.MojoExecutionException;

public class PluginMojoException extends MojoExecutionException {

    public PluginMojoException() {
        super("");
        PluginLogger.printGeneratedTables(true);
    }

    public PluginMojoException(String message) {
        super(message);
        PluginLogger.printGeneratedTables(true);
    }

    public PluginMojoException(String message, Throwable cause) {
        super(message, cause);
        PluginLogger.printGeneratedTables(true);
    }
}