package com.arqui_web.viaje_service.model;

import com.arqui_web.viaje_service.dto.TarifaResponseDTO;

public class Tarifa {
	private Long id;
	private TipoTarifa tipo;
	private Double monto;

	public Tarifa() {
		super();
	}

	public Tarifa(TipoTarifa tipo, Double monto) {
		super();
		this.tipo = tipo;
		this.monto = monto;
	}

	public TarifaResponseDTO toTarifaDTO() {
		return new TarifaResponseDTO(this.getId(), this.getTipo(), this.getMonto());
	}

	public TipoTarifa getTipo() {
		return tipo;
	}

	public void setTipo(TipoTarifa tipo) {
		this.tipo = tipo;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Long getId() {
		return id;
	}

}
