package com.arqui_web.viaje_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arqui_web.viaje_service.client.MonopatinClient;
import com.arqui_web.viaje_service.dto.MonopatinDTO;
import com.arqui_web.viaje_service.dto.ViajeResponseDTO;
import com.arqui_web.viaje_service.model.Viaje;
import com.arqui_web.viaje_service.repository.ViajeRepository;

@Service
public class ViajeService {
	@Autowired
	private ViajeRepository repository;
	@Autowired
	private MonopatinClient monopatinClient;
	private static final Logger log = LoggerFactory.getLogger(ViajeService.class);

	public ViajeResponseDTO createViaje(ViajeResponseDTO dto) {
		Viaje v = new Viaje(dto.getInicio(), dto.getFin(), dto.getMonopatin().getId(), dto.getKilometros(),
				dto.getCosto());

		Viaje guardado = repository.save(v);
		log.info("Viaje creada con ID {}", guardado.getId());

		return v.toViajeDTO();
	}

	public Optional<ViajeResponseDTO> obtenerViaje(Long id) {
		return repository.findById(id).map(v -> {

			ViajeResponseDTO dto = v.toViajeDTO();
			MonopatinDTO monopatin = monopatinClient.obtenerMonopatinById(v.getMonopatinId());
			dto.setMonopatin(monopatin);
			return dto;

		});
	}

	public Iterable<ViajeResponseDTO> obtenerViajes() {
		return repository.findAll().stream().map(v -> v.toViajeDTO()).toList();
	}

	public Optional<ViajeResponseDTO> updateViaje(ViajeResponseDTO dto, Long id) {
		return repository.findById(id).map(v -> {
			v.setInicio(dto.getInicio());
			v.setFin(dto.getFin());
			v.setMonopatinId(dto.getMonopatin().getId());
			v.setKilometros(dto.getKilometros());
			v.setCosto(dto.getCosto());

			repository.save(v);
			log.info("Viaje con ID {} actualizada correctamente", id);

			return v.toViajeDTO();
		});
	}

	public Boolean deleteViaje(Long id) {
		Optional<Viaje> viaje = repository.findById(id);

		try {
			if (viaje == null) {
				log.error("Error, no se encontro viaje con id {}", id);
				return false;
			} else {
				repository.deleteById(id);
				log.info("Viaje con ID {} eliminado correctamente", id);
				return true;
			}
		} catch (Exception e) {
			log.error("Error eliminando carrera con id {}", id);
			return false;
		}
	}
}
