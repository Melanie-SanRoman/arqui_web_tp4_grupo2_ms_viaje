package com.arqui_web.viaje_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Pausa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDate inicio;
	@Column
	private LocalDate fin;
	@ManyToOne
	private Viaje viaje;

	public Pausa() {
		super();
	}

	public Pausa(LocalDate inicio, LocalDate fin, Viaje viaje) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.viaje = viaje;
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

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

	public Long getId() {
		return id;
	}
}
