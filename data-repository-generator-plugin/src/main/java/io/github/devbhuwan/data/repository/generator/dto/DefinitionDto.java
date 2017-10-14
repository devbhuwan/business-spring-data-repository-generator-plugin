package io.github.devbhuwan.data.repository.generator.dto;

public class DefinitionDto {

    private String className;
    private String classPackage;
    private String idClassName;
    private String idClassPackage;
    private String generatorSignature;

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void validate() {

    }

    public String getIdClassName() {
        return idClassName;
    }

    public void setIdClassName(String idClassName) {
        this.idClassName = idClassName;
    }

    public String getIdClassPackage() {
        return idClassPackage;
    }

    public void setIdClassPackage(String idClassPackage) {
        this.idClassPackage = idClassPackage;
    }

    public String getGeneratorSignature() {
        return generatorSignature;
    }

    public void setGeneratorSignature(String generatorSignature) {
        this.generatorSignature = generatorSignature;
    }
}
