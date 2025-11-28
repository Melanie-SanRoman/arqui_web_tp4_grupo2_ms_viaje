package com.arqui_web.viaje_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("!test")
public class RestTemplateConfig {

	@Autowired
    private JwtInterceptor jwtInterceptor;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.getInterceptors().add(jwtInterceptor);
        return rt;
    }

}
