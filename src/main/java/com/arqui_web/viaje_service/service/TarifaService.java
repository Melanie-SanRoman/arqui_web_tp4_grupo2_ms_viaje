package com.arqui_web.viaje_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arqui_web.viaje_service.dto.TarifaResponseDTO;
import com.arqui_web.viaje_service.model.TipoTarifa;
import com.arqui_web.viaje_service.repository.TarifaRepository;

@Service
public class TarifaService {
	@Autowired
	private TarifaRepository repository;
	private static final Logger log = LoggerFactory.getLogger(TarifaService.class);

	public Optional<TarifaResponseDTO> obtenerTarifa(String tarifa) {
		TipoTarifa tipoTarifa = null;
		if (tarifa != null) {
			tipoTarifa = TipoTarifa.valueOf(tarifa.toUpperCase());
		}
		return repository.findByTipo(tipoTarifa).map(t -> {

			TarifaResponseDTO dto = t.toTarifaDTO();
			return dto;
		});
	}

	public Iterable<TarifaResponseDTO> obtenerTarifas() {
		return repository.findAll().stream().map(t -> t.toTarifaDTO()).toList();
	}

	public Optional<TarifaResponseDTO> updateTarifa(TarifaResponseDTO dto, String tarifa) {
		TipoTarifa tipoTarifa = null;
		if (tarifa != null) {
			tipoTarifa = TipoTarifa.valueOf(tarifa.toUpperCase());
		}
		return repository.findByTipo(tipoTarifa).map(t -> {
			t.setMonto(dto.getMonto());

			repository.save(t);
			log.info("Tarifa de tipo {} actualizada correctamente", tarifa);
			return t.toTarifaDTO();
		});
	}
}
