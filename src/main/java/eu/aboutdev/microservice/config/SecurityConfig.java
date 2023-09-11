package eu.aboutdev.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeExchange().pathMatchers("/actuator/health/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer().jwt().and()
                .and().build();
    }
}
