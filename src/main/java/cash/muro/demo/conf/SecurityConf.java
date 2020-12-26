package cash.muro.demo.conf;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import cash.muro.springsecurity.authentication.cashid.CashIdAuthenticationFilter;
import cash.muro.springsecurity.authentication.cashid.MuroAuthenticationProvider;
import cash.muro.springsecurity.authentication.cashid.conf.AuthKeysConf;
import cash.muro.springsecurity.authorization.AuthoritiesService;
import cash.muro.springsecurity.authorization.MuroDemoAuthoritiesService;

@Configuration
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/cashid/**")
			.and()
			.addFilterAfter(new CashIdAuthenticationFilter(authenticationManager(), successHandler()), AbstractPreAuthenticatedProcessingFilter.class)
			.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll()).logout()
			.logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(authenticationProvider()));
	}

	private SimpleUrlAuthenticationSuccessHandler successHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
					throws IOException, ServletException {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().append("OK");
				response.setStatus(200);
			}
		};
	}

	@Bean
	public MuroAuthenticationProvider authenticationProvider() {
		return new MuroAuthenticationProvider(authoritiesService());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "cashid.authkeys")
	public AuthKeysConf authKeysConf() {
		return new AuthKeysConf();
	}
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
	    return new SecurityEvaluationContextExtension();
	}	
	
	@Bean
	public AuthoritiesService authoritiesService() {
		  return new MuroDemoAuthoritiesService(
				  Stream.of("bitcoincash:qpscef2g644pe6d6mzxafa9j0araydt8vgw9g9uv2x", 
						  "bchtest:qqdt4y77x3khlygjg3z8u2g5kdscvuadgg20f54stw")
                  .collect(Collectors.toSet()));
	}
}
