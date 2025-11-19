package com.arqui_web.viaje_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.ParadaDTO;

public class ParadaClient {
	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://MICROSERVICIO-MONOPATIN/api/paradas";

	public ParadaDTO obtenerParadaById(Long id) {
		return restTemplate.getForObject(BASE_URL + "/" + id, ParadaDTO.class);
	}
}
