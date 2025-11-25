package com.arqui_web.viaje_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.arqui_web.viaje_service.dto.PausaResponseDTO;
import com.arqui_web.viaje_service.dto.PausaTotalDTO;
import com.arqui_web.viaje_service.dto.ViajeResponseDTO;;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ViajeServiceApplicationTests {

	private final RestTemplate rest = new RestTemplate();

	private final String viajeUrl = "http://localhost:8080/viajes";
	private final String pausaUrl = "http://localhost:8081/pausas";
	private final String tarifaUrl = "http://localhost:8082/tarifas";

	private static Long viajeId;
	private static Long pausaId;

	@Test
	@Order(1)
	void iniciarViaje() {

		ResponseEntity<ViajeResponseDTO> response = rest.postForEntity(viajeUrl + "/iniciar/1", null,
				ViajeResponseDTO.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());

		viajeId = response.getBody().getId();
		assertNotNull(viajeId);

		System.out.println("Viaje creado: " + viajeId);
	}

	@Test
	@Order(2)
	void iniciarPausa() {

		ResponseEntity<PausaResponseDTO> response = rest.postForEntity(pausaUrl + "/iniciar/" + viajeId, null,
				PausaResponseDTO.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(viajeId, response.getBody().getViajeId());

		pausaId = response.getBody().getId();
		assertNotNull(pausaId);

		System.out.println("Pausa creada: " + pausaId);
	}

	@Test
	@Order(3)
	void finalizarPausa() {

		ResponseEntity<PausaResponseDTO> response = rest.exchange(pausaUrl + "/" + pausaId + "/finalizar",
				HttpMethod.PUT, null, PausaResponseDTO.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody().getFin());

		System.out.println("Pausa finalizada");
	}

	@Test
	@Order(4)
	void consultarMinutosPausa() {

		ResponseEntity<PausaTotalDTO> response = rest.getForEntity(pausaUrl + "/viaje/" + viajeId + "/total-minutos",
				PausaTotalDTO.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getMinutosTotales());

		System.out.println("Minutos pausa: " + response.getBody().getMinutosTotales());
	}

	@Test
	@Order(5)
	void calcularTarifa() {

		Map<String, Object> request = new HashMap<>();
		request.put("kilometros", 3.5);
		request.put("minutosPausa", 2);

		ResponseEntity<Map> response = rest.postForEntity(tarifaUrl + "/calcular", request, Map.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody().get("costo"));

		System.out.println("Costo calculado: " + response.getBody().get("costo"));
	}

	@Test
	@Order(6)
	void finalizarViaje() {

		ResponseEntity<ViajeResponseDTO> response = rest.exchange(viajeUrl + "/" + viajeId + "/finalizar",
				HttpMethod.PUT, null, ViajeResponseDTO.class);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody().getCosto());

		System.out.println("Viaje finalizado");
	}
}
