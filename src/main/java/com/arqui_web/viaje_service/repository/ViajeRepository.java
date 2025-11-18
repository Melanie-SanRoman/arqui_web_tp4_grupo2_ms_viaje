package com.arqui_web.viaje_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui_web.viaje_service.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

}
