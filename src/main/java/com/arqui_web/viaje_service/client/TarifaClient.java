package com.arqui_web.viaje_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.CostoRequestDTO;
import com.arqui_web.viaje_service.dto.CostoResponseDTO;

@Service
public class TarifaClient {

	@Autowired
	private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8083/tarifas";

	public CostoResponseDTO calcularCosto(CostoRequestDTO request) {

		ResponseEntity<CostoResponseDTO> response = restTemplate.postForEntity(BASE_URL + "/calcular", request,
				CostoResponseDTO.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			throw new RuntimeException("Error al calcular costo: " + response.getStatusCode());
		}
	}
}
