package com.arqui_web.viaje_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arqui_web.viaje_service.dto.ViajeResponseDTO;
import com.arqui_web.viaje_service.service.ViajeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/viajes")
@Tag(name = "Viajes", description = "Endpoints de gestion de viajes")
public class ViajeController {
	@Autowired
	private ViajeService service;

	@Operation(summary = "Crear un viaje", description = "Registra un nuevo viaje en el sistema")
	@ApiResponse(responseCode = "201", description = "Viaje creado correctamente")
	@PostMapping
	public ResponseEntity<ViajeResponseDTO> postViaje(
			@Parameter(description = "DTO que contiene los datos del viaje a crear") @RequestBody ViajeResponseDTO dto) {
		ViajeResponseDTO creado = service.postViaje(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@Operation(summary = "Obtener un viaje por ID", description = "Devuelve la informacion de un viaje en especifico")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Viaje encontrado"),
			@ApiResponse(responseCode = "404", description = "Viaje no encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> getViajeById(
			@Parameter(description = "ID del viaje solicitado") @PathVariable Long id) {
		Optional<ViajeResponseDTO> encontrado = service.getViajeById(id);
		return encontrado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Lista todos los viajes", description = "Devuelve todos los viajes registrados")
	@ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
	@GetMapping
	public ResponseEntity<Iterable<ViajeResponseDTO>> getViajes() {
		Iterable<ViajeResponseDTO> it = service.getViajes();
		return ResponseEntity.ok(it);
	}

	@Operation(summary = "Actualiza un viaje", description = "Modifica los datos de un viaje existente")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Viaje actualizado"),
			@ApiResponse(responseCode = "404", description = "Viaje no encontrado") })
	@PutMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> updateViaje(
			@Parameter(description = "DTO que contiene los nuevos datos del viaje") @RequestBody ViajeResponseDTO dto,
			@Parameter(description = "ID del viaje a actualizar") @PathVariable Long id) {
		return service.updateViaje(dto, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Elimina un viaje", description = "Elimina un viaje por su ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Viaje eliminado"),
			@ApiResponse(responseCode = "404", description = "Viaje no encontrado") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteViaje(@Parameter(description = "ID del viaje a eliminar") @PathVariable Long id) {
		boolean eliminado = service.deleteViaje(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Inicia un viaje", description = "Crea un viaje asociado a un monopatin")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Viaje iniciado"),
			@ApiResponse(responseCode = "404", description = "Monopatin no encontrado") })
	@PostMapping("/iniciar/{monopatinId}")
	public ResponseEntity<ViajeResponseDTO> iniciarViaje(
			@Parameter(description = "ID del Monopatin a asociar") @PathVariable Long monopatinId) {
		try {
			ViajeResponseDTO dto = service.iniciarViaje(monopatinId);
			return ResponseEntity.ok(dto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Finaliza un viaje", description = "Marca un viaje como finalizado")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Viaje finalizado"),
			@ApiResponse(responseCode = "404", description = "Viaje no encontrado") })
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<ViajeResponseDTO> finalizarViaje(
			@Parameter(description = "ID del viaje a finalizar") @PathVariable Long id) {
		try {
			ViajeResponseDTO dto = service.finalizarViaje(id);
			return ResponseEntity.ok(dto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
