package com.arqui_web.viaje_service.dto;

public class ParadaDTO {
	private Long id;
	private Double latitud;
	private Double longitud;

	public ParadaDTO() {
		super();
	}

	public ParadaDTO(Long id, Double latitud, Double longitud) {
		super();
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
}
