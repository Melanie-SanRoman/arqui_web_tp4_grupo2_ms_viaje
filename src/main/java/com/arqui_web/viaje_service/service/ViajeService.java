package com.arqui_web.viaje_service.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arqui_web.viaje_service.ViajeServiceApplication;
import com.arqui_web.viaje_service.client.MonopatinClient;
import com.arqui_web.viaje_service.client.PausaClient;
import com.arqui_web.viaje_service.client.TarifaClient;
import com.arqui_web.viaje_service.dto.CostoRequestDTO;
import com.arqui_web.viaje_service.dto.CostoResponseDTO;
import com.arqui_web.viaje_service.dto.MonopatinDTO;
import com.arqui_web.viaje_service.dto.ParadaDTO;
import com.arqui_web.viaje_service.dto.PausaResponseDTO;
import com.arqui_web.viaje_service.dto.PausaTotalDTO;
import com.arqui_web.viaje_service.dto.ViajeResponseDTO;
import com.arqui_web.viaje_service.model.Viaje;
import com.arqui_web.viaje_service.repository.ViajeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ViajeService {

	@Autowired
	private ViajeRepository repository;
	@Autowired
	private MonopatinClient monopatinClient;
	@Autowired
	private TarifaClient tarifaClient;
	@Autowired
	private PausaClient pausaClient;
	@Autowired
	private DistanciaService distanciaService;

	private static final Logger log = LoggerFactory.getLogger(ViajeService.class);

	ViajeService(ViajeServiceApplication viajeServiceApplication) {
	}

	public ViajeResponseDTO postViaje(ViajeResponseDTO dto) {
		Viaje v = new Viaje(dto.getInicio(), dto.getFin(), dto.getMonopatin().getId());

		Viaje guardado = repository.save(v);
		log.info("Viaje creada con ID {}", guardado.getId());

		return v.toViajeDTO();
	}

	public Optional<ViajeResponseDTO> getViajeById(Long id) {
		return repository.findById(id).map(v -> {

			ViajeResponseDTO dto = v.toViajeDTO();
			MonopatinDTO monopatin = monopatinClient.getMonopatinById(v.getMonopatinId());
			dto.setMonopatin(monopatin);
			return dto;

		});
	}

	public Iterable<ViajeResponseDTO> getViajes() {
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
			log.error("Error eliminando viaje con id {}", id);
			return false;
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ViajeResponseDTO finalizarViaje(Long viajeId) {
		// Obtener el viaje a finalizar
		Viaje viaje = repository.findById(viajeId).orElseThrow(() -> new EntityNotFoundException("Viaje no existe"));

		// Obtener punto de inicio
		MonopatinDTO monopatin = monopatinClient.getMonopatinById(viaje.getMonopatinId());
		ParadaDTO paradaInicio = monopatin.getParada();

		// Cambiar ubicacion del monopatin
		// Este MS no tiene manera de saber las coordenadas de la parada donde el
		// monopatin finalizo el viaje
		monopatinClient.desplazarUbicacion(monopatin.getId(), new HashMap<>());

		// Obtener punto final
		MonopatinDTO monopatinActual = monopatinClient.getMonopatinById(viaje.getMonopatinId());
		ParadaDTO paradaFin = monopatinActual.getParada();

		// Obtener pausas
		List<Long> pausas = pausaClient.getPausasByViaje(viaje.getId()).stream().map(PausaResponseDTO::getId)
				.collect(Collectors.toList());

		// Calcular distancia
		Double km = distanciaService.calcularKilometros(paradaInicio, paradaFin);

		// Calcular minutos
		PausaTotalDTO pausaDTO = pausaClient.getMinutosPausaByViaje(viaje.getId());
		Double minutosPausa = pausaDTO.getMinutosTotales();

		// Calcular costo final
		CostoResponseDTO costoDTO = tarifaClient.calcularCosto(new CostoRequestDTO(km, minutosPausa));
		Double costoFinal = costoDTO.getCosto();

		// Actualizar viaje
		viaje.setFin(LocalDate.now());
		viaje.setMonopatinId(monopatinActual.getId());
		viaje.setKilometros(km);
		viaje.setCosto(costoFinal);
		viaje.setPausas(pausas);

		return viaje.toViajeDTO();
	}
}
