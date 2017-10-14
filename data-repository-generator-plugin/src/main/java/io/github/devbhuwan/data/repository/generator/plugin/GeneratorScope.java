package io.github.devbhuwan.data.repository.generator.plugin;


public enum GeneratorScope {

    RUNTIME("compile"), TEST("test");

    private final String scope;

    GeneratorScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}
