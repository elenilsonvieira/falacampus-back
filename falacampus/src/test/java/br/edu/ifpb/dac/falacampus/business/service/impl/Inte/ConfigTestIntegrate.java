package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback
@Transactional
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
@EnableJpaRepositories(basePackages = {"br.edu.ifpb.dac.falacampus.model.repository"})
@ComponentScan({"br.edu.ifpb.dac.falacampus.business.service.impl","br.edu.ifpb.dac.falacampus.presentation.control", "br.edu.ifpb.dac.falacampus.business.service","br.edu.ifpb.dac.falacampus.business.service.impl"})
public interface ConfigTestIntegrate {
  // ...
}
