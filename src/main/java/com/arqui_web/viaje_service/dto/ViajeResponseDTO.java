package com.arqui_web.viaje_service.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViajeResponseDTO {
	private Long id;
	private LocalDate inicio;
	private LocalDate fin;
	private MonopatinDTO monopatin;
	private Double kilometros;
	private Double costo;
	private List<PausaResponseDTO> pausas;

	public ViajeResponseDTO() {
		super();
	}

	public ViajeResponseDTO(Long id, LocalDate inicio, LocalDate fin, MonopatinDTO monopatin, Double kilometros,
			Double costo) {
		super();
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
		this.monopatin = monopatin;
		this.kilometros = kilometros;
		this.costo = costo;
		this.pausas = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	public MonopatinDTO getMonopatin() {
		return monopatin;
	}

	public void setMonopatin(MonopatinDTO monopatin) {
		this.monopatin = monopatin;
	}

	public Double getKilometros() {
		return kilometros;
	}

	public void setKilometros(Double kilometros) {
		this.kilometros = kilometros;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public List<PausaResponseDTO> getPausas() {
		return pausas;
	}

	public void setPausas(List<PausaResponseDTO> pausas) {
		this.pausas = pausas;
	}
}
