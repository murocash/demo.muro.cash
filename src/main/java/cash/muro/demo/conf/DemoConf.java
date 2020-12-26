package cash.muro.demo.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
		cash.muro.demo.model.Peak.class })
@EnableJpaRepositories(basePackageClasses = { cash.muro.demo.repos.PeakRepository.class })
public class DemoConf {

}
