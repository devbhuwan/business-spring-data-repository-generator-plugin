package io.github.devbhuwan;

import io.github.devbhuwan.business.model.DemoDomain;
import io.github.devbhuwan.business.model.contracts.DemoRepository;
import io.github.devbhuwan.business.model.repositories.DemoDomainDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.atomic.AtomicLong;

public class BusinessApplicationIntegrationTest {

    private AtomicLong atomicLong = new AtomicLong(1000);

    @Autowired
    @Lazy
    private DemoRepository demoRepository;
    @Autowired
    @Lazy
    private DemoDomainDataRepository demoDomainDataRepository;

    @Test
    public void testName() {
        DemoDomain demoDomain = new DemoDomain();
        demoDomain.setId(atomicLong.incrementAndGet());
        demoDomain.setName("Hello");
        demoRepository.save(demoDomain);
        Assertions.assertThat(demoRepository.findById(demoDomain.getId()))
                .hasFieldOrPropertyWithValue("name", "Hello")
                .hasFieldOrPropertyWithValue("id", demoDomain.getId());
    }
}
