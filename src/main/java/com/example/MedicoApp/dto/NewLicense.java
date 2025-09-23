package com.example.MedicoApp.dto;

import java.time.OffsetDateTime;

public record NewLicense(
        String Folio,
        String PatientID,
        String DoctorID,
        String Diagnosis,
        OffsetDateTime   StartDate,
        Integer Days,
        String Status
) {}
