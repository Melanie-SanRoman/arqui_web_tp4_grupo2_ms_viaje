package com.arqui_web.viaje_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class TestRestTemplateConfig {
    
    @Bean
    @Primary
    RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        return rest;
    }
}
