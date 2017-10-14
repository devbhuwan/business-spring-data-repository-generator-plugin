package io.github.devbhuwan.data.repository.generator.plugin;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static io.takari.maven.testing.TestResources.assertFilesPresent;

public class DataRepositoryGeneratorMojoTest {

    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();
    @Rule
    public final TestResources resources = new TestResources();

    @Test
    public void givenBpmnFilesInSourceAndTestResourceDirectory_whenGenerateBpmnConstantMojo_thenGenerateConstantFiles() throws Exception {
        File basedir = resources.getBasedir("basic");
        maven.executeMojo(basedir, DataRepositoryGeneratorMojo.MOJO_GOAL);
        assertFilesPresent(basedir, "target/generated-sources/annotations/bpmn/metadata/BpmnMetadataSrcConstants.java");
        assertFilesPresent(basedir, "target/generated-test-sources/test-annotations/bpmn/metadata/BpmnMetadataTestConstants.java");
    }

}