package com.arqui_web.viaje_service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.PausaResponseDTO;
import com.arqui_web.viaje_service.dto.PausaTotalDTO;

@Service
public class PausaClient {

	@Autowired
	private RestTemplate restTemplate;

	private static final String BASE_URL = "http://MICROSERVICIO-PAUSA/api/pausas";

	public PausaTotalDTO getMinutosPausaByViaje(Long viajeId) {
		return restTemplate.getForObject(BASE_URL + "/viaje/" + viajeId + "/total-minutos", PausaTotalDTO.class);
	}

	@SuppressWarnings("unchecked")
	public List<PausaResponseDTO> getPausasByViaje(Long viajeId) {
		return (List<PausaResponseDTO>) restTemplate.getForObject(BASE_URL + "/viaje/" + viajeId + "/pausas",
				PausaTotalDTO.class);

	}
}
