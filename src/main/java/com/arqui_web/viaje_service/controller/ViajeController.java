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

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/viajes")
public class ViajeController {
	@Autowired
	private ViajeService service;

	@PostMapping
	public ResponseEntity<ViajeResponseDTO> postViaje(@RequestBody ViajeResponseDTO dto) {
		ViajeResponseDTO creado = service.postViaje(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> getViajeById(@PathVariable Long id) {
		Optional<ViajeResponseDTO> encontrado = service.getViajeById(id);
		return encontrado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<Iterable<ViajeResponseDTO>> getViajes() {
		Iterable<ViajeResponseDTO> it = service.getViajes();
		return ResponseEntity.ok(it);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ViajeResponseDTO> updateViaje(@RequestBody ViajeResponseDTO dto, @PathVariable Long id) {
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

	@GetMapping("/viajes/{id}/finalizar")
	public ResponseEntity<ViajeResponseDTO> finalizarViaje(@PathVariable Long id) {
	    try {
	        ViajeResponseDTO dto = service.finalizarViaje(id);
	        return ResponseEntity.ok(dto);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build();
	    }
	}

}
