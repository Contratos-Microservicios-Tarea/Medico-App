package com.example.MedicoApp.service;

import org.springframework.stereotype.Service;

import com.example.MedicoApp.dto.LicenseDto;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LicensesService {

    private final WebClient webClient;

    public LicensesService(WebClient licenciasWebClient) {
        this.webClient = licenciasWebClient;
    }


    // Crea una licencia segun cantidad de dias
    public String createLicencias(LicenseDto request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/licenses")
                        .build()
                )
                .bodyValue(request) // 👈 aquí se envía el JSON al body
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }



}
