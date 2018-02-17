package io.github.devbhuwan;

import io.github.devbhuwan.business.model.DemoDomain;
import io.github.devbhuwan.business.model.contracts.DemoRepository;
import io.github.devbhuwan.business.model.repositories.DemoDomainDataRepository;
import io.github.devbhuwan.business.service.BusinessServiceApplication;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessServiceApplication.class)
@ActiveProfiles("jpa")
@EnableJpaRepositories
public class JpaBusinessApplicationIntegrationTest  extends BusinessApplicationIntegrationTest{




}