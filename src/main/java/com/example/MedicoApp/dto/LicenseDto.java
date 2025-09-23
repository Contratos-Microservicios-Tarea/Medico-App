package com.example.MedicoApp.dto;


public record LicenseDto(

        String patientId,
        String doctorId,
        String diagnosis,
        int days
)
{

}