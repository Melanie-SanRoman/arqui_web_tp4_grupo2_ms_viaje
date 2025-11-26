package com.arqui_web.viaje_service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class TestRestTemplateConfig {

    @Bean
    @Primary
    RestTemplate restTemplate(JwtInterceptor jwtInterceptor) {
        RestTemplate rest = new RestTemplate();
        rest.getInterceptors().add(jwtInterceptor);
        return rest;
    }
}
