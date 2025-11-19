package com.arqui_web.viaje_service.service;

import org.springframework.stereotype.Service;

import com.arqui_web.viaje_service.dto.ParadaDTO;

@Service
public class DistanciaService {

    public double calcularKilometros(ParadaDTO inicio, ParadaDTO fin) {
        double lat1 = inicio.getLatitud();
        double lon1 = inicio.getLongitud();
        double lat2 = fin.getLatitud();
        double lon2 = fin.getLongitud();

        return haversine(lat1, lon1, lat2, lon2);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}

