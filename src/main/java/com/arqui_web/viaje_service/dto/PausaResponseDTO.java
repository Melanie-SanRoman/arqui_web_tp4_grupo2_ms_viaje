package com.arqui_web.viaje_service.dto;

import java.time.LocalDate;

public class PausaResponseDTO {
	private LocalDate inicio;
	private LocalDate fin;

	public PausaResponseDTO() {
		super();
	}

	public PausaResponseDTO(LocalDate inicio, LocalDate fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
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
}
