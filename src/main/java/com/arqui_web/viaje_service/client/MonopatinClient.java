package com.arqui_web.viaje_service.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.MonopatinDTO;

@Service
public class MonopatinClient {

	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://MICROSERVICIO-MONOPATIN/api/monopatines";

	// GET /api/monopatines/{id}
	public MonopatinDTO getMonopatinById(Long id) {
		return restTemplate.getForObject(BASE_URL + "/" + id, MonopatinDTO.class);
	}

	// PATCH /api/monopatines/{id}/localizacion
	public MonopatinDTO desplazarUbicacion(Long id, Map<String, Object> fields) {
		return restTemplate.patchForObject(BASE_URL + "/" + id + "/localizacion", fields, MonopatinDTO.class);
	}
}
