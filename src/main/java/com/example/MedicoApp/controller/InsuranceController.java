package com.example.MedicoApp.controller;

import com.example.MedicoApp.service.LicensesService;
import org.springframework.web.bind.annotation.*;

import com.example.MedicoApp.dto.LicenseDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class InsuranceController {

    LicensesService insuranceService;

    @PostMapping("licenses")
    protected final String getLicences(@RequestBody LicenseDto newLicence) {
        return insuranceService.createLicencias(newLicence) ;
    }
}
