package cash.muro.demo.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cash.muro.aop.MuroAspect;
import cash.muro.entities.MuroSettings;

@Configuration
@EntityScan(basePackageClasses = {
		cash.muro.entities.MuroSettings.class,
		cash.muro.aop.MuroAspect.class })
@ComponentScan(basePackageClasses = {
		com.emiperez.springframework.jsutils.Ajaxl10nController.class,
		cash.muro.springsecurity.authentication.cashid.CashIdCredentials.class,
		cash.muro.springsecurity.authorization.controllers.ErrorController.class,
		cash.muro.controllers.MuroProductController.class,
		cash.muro.services.AccessedResourceServiceImpl.class})
@EnableJpaRepositories(basePackageClasses = { cash.muro.repos.AccessedResourceRepository.class })
public class MuroSpringConf {
	
	@Bean
    public MuroAspect aspects() {
        return new MuroAspect();
    }
	
	@Bean
	@ConfigurationProperties(prefix = "muro")
	public MuroSettings muroSettings() {
		return new MuroSettings();
	}
}
