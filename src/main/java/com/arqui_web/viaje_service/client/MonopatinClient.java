package com.arqui_web.viaje_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.MonopatinDTO;

@Service
public class MonopatinClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://MICROSERVICIO-MONOPATIN/api/monopatines";

    public MonopatinDTO obtenerMonopatinById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, MonopatinDTO.class);
    }
}

