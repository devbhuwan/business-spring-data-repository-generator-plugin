package io.github.devbhuwan.data;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataRepositoryProperties.class)
public class DataRepositoryAutoConfiguration {

}
