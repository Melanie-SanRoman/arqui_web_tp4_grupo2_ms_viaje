package com.arqui_web.viaje_service.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.arqui_web.viaje_service.dto.MonopatinDTO;
import com.arqui_web.viaje_service.dto.ViajeResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Viaje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDate inicio;
	@Column
	private LocalDate fin;
	@Column
	private Long monopatinId;
	@Column
	private Double kilometros;
	@Column
	private Double costo;
	private List<Long> pausasId;

	public Viaje() {
		super();
	}

	public Viaje(LocalDate inicio, LocalDate fin, Long monopatinId) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.monopatinId = monopatinId;
		this.pausasId = new ArrayList<>();
	}

	public Viaje(LocalDate inicio, LocalDate fin, Long monopatinId, Double kilometros, Double costo,
			List<Long> pausasId) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.monopatinId = monopatinId;
		this.kilometros = kilometros;
		this.costo = costo;
		this.pausasId = pausasId;
	}

	public ViajeResponseDTO toViajeDTO() {
		return new ViajeResponseDTO(this.getId(), this.getInicio(), this.getFin(), new MonopatinDTO(this.monopatinId),
				this.getKilometros(), this.getCosto());
	}

	public List<Long> getPausasId() {
		return pausasId;
	}

	public void setPausasId(List<Long> pausasId) {
		this.pausasId = pausasId;
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

	public Long getMonopatinId() {
		return monopatinId;
	}

	public void setMonopatinId(Long monopatinId) {
		this.monopatinId = monopatinId;
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

	public List<Long> getPausas() {
		return pausasId;
	}

	public void setPausas(List<Long> pausas) {
		this.pausasId = pausas;
	}

	public Long getId() {
		return id;
	}
}
