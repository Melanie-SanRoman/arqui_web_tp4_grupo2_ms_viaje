package com.arqui_web.viaje_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;;

@SpringBootTest
class ViajeServiceApplicationTests {

	private final RestTemplate rest = new RestTemplate();

    private final String viajeUrl = "http://localhost:8081/viajes";
    private final String pausaUrl = "http://localhost:8082/pausas";
    private final String tarifaUrl = "http://localhost:8083/tarifas";

    private static Long viajeIdGenerado;

    @Test
    @Order(1)
    void testCrearViaje() {
        Map<String, Object> request = new HashMap<>();
        request.put("monopatinId", 123L);
        request.put("usuarioId", 50L);
        request.put("origen", "Av. Siempre Viva 123");

        ResponseEntity<Map> response = rest.postForEntity(
                viajeUrl + "/crear",
                request,
                Map.class
        );

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get("viajeId"));

        viajeIdGenerado = Long.valueOf(response.getBody().get("viajeId").toString());
    }

    @Test
    @Order(2)
    void testRegistrarPausa() {
        Map<String, Object> request = new HashMap<>();
        request.put("viajeId", viajeIdGenerado);
        request.put("motivo", "Semáforo");
        request.put("duracion", 45);

        ResponseEntity<Map> response = rest.postForEntity(
                pausaUrl + "/registrar",
                request,
                Map.class
        );

        assertEquals(201, response.getStatusCode().value());
        assertTrue((Boolean) response.getBody().get("registrada"));
    }

    @Test
    @Order(3)
    void testConsultarTarifa() {
        ResponseEntity<Map> response = rest.getForEntity(
                tarifaUrl + "/actual?monopatinId=" + viajeIdGenerado,
                Map.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().get("precio"));
    }

    @Test
    @Order(4)
    void testCerrarViaje() {
        Map<String, Object> request = new HashMap<>();
        request.put("viajeId", viajeIdGenerado);

        ResponseEntity<Map> response = rest.postForEntity(
                viajeUrl + "/cerrar",
                request,
                Map.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().get("costoTotal"));
    }

}
