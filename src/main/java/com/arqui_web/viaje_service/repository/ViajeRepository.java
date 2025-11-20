package com.arqui_web.viaje_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arqui_web.viaje_service.dto.ViajeResponseDTO;
import com.arqui_web.viaje_service.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

//	@Query("SELECT new com.arqui_web.viaje_service.dto.ViajeResponseDTO("
//			+ ")")
//	public ViajeResponseDTO postViaje(@Param("dto") ViajeResponseDTO dto);

	@Query("""
			    SELECT new com.arqui_web.viaje_service.dto.ViajeResponseDTO(
			        v.id, v.fechaInicio, v.fechaFin, v.monto
			    )
			    FROM Viaje v
			    WHERE v.id = :id
			""")
	public ViajeResponseDTO findViajeDTOById(@Param("id") Long id);
}
