package io.github.devbhuwan.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "business.data.repository")
@Validated
@Getter
@Setter
public class DataRepositoryProperties {

    private String dataProviderType;

}
