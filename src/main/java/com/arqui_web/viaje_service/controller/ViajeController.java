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

@RestController
@RequestMapping("/viajes")
public class ViajeController {
	@Autowired
	private ViajeService service;

	@PostMapping
	public ResponseEntity<ViajeResponseDTO> createCarrera(@RequestBody ViajeResponseDTO dto) {
		ViajeResponseDTO creado = service.createViaje(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> obtenerViajeById(@PathVariable Long id) {
		Optional<ViajeResponseDTO> encontrado = service.obtenerViaje(id);
		return encontrado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<Iterable<ViajeResponseDTO>> obtenerViajes() {
		Iterable<ViajeResponseDTO> it = service.obtenerViajes();
		return ResponseEntity.ok(it);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> updateCarrera(@RequestBody ViajeResponseDTO dto, @PathVariable Long id) {
		return service.updateViaje(dto, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
		boolean eliminado = service.deleteViaje(id);
		if (eliminado) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
