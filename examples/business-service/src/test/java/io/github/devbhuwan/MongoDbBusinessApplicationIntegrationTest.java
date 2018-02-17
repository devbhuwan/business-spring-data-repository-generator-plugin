package io.github.devbhuwan;

import io.github.devbhuwan.business.service.BusinessServiceApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessServiceApplication.class)
@ActiveProfiles("mongo")
@EnableMongoRepositories
public class MongoDbBusinessApplicationIntegrationTest extends BusinessApplicationIntegrationTest {
//
//    @ClassRule
//    public static OutputCapture outputCapture = new OutputCapture();
}