package com.arqui_web.viaje_service.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.MonopatinDTO;
import com.arqui_web.viaje_service.dto.ParadaDTO;

@Service
public class MonopatinClient {

	@Autowired
	private RestTemplate restTemplate;

	// Puerto ficticio solo para que falle y se active el mock
	private final String BASE_URL = "http://localhost:8090/api/monopatines";

	// --- Estado interno para simular movimiento ---
	private Map<Long, ParadaDTO> ubicacionesMock = new HashMap<>();

	// GET /api/monopatines/{id}
	public MonopatinDTO getMonopatinById(Long id) {
		try {
			return restTemplate.getForObject(BASE_URL + "/" + id, MonopatinDTO.class);
		} catch (Exception e) {
			System.out.println("⚠ No hay MS Monopatin activo. Devolviendo mock.");

			// Creamos monopatín falso
			MonopatinDTO mock = new MonopatinDTO();
			mock.setId(id);

			// Si ya se movió, devolvemos la nueva parada
			ParadaDTO parada = ubicacionesMock.getOrDefault(id, paradaDefault());

			mock.setParada(parada);

			return mock;
		}
	}

	// PATCH /api/monopatines/{id}/localizacion
	public MonopatinDTO desplazarUbicacion(Long id, Map<String, Object> fields) {
		try {
			return restTemplate.patchForObject(BASE_URL + "/" + id + "/localizacion", fields, MonopatinDTO.class);

		} catch (Exception e) {
			// ⚠ simulamos que el monopatín se movió a una parada nueva
			ParadaDTO nueva = new ParadaDTO();
			nueva.setId(1000L);
			nueva.setLatitud(-34.61);
			nueva.setLongitud(-58.41);

			ubicacionesMock.put(id, nueva);

			MonopatinDTO mock = new MonopatinDTO();
			mock.setId(id);
			mock.setParada(nueva);
			return mock;
		}
	}

	private ParadaDTO paradaDefault() {
		ParadaDTO p = new ParadaDTO();
		p.setId(999L);
		p.setLatitud(-34.6037);
		p.setLongitud(-58.3816);
		return p;
	}
}
