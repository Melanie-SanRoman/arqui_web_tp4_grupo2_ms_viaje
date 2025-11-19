package com.arqui_web.viaje_service.dto;

public class MonopatinDTO {
	private Long id;
	private ParadaDTO parada;

	public MonopatinDTO() {
		super();
	}

	public MonopatinDTO(Long id) {
		super();
		this.id = id;
	}

	public ParadaDTO getParada() {
		return parada;
	}

	public void setParada(ParadaDTO parada) {
		this.parada = parada;
	}

	public MonopatinDTO(Long id, ParadaDTO parada) {
		super();
		this.id = id;
		this.parada = parada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
