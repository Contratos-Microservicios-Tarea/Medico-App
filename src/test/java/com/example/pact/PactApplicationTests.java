package com.example.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.MedicoApp.dto.LicenseDto;
import com.example.MedicoApp.service.LicensesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PactTestFor(providerName = "Licencias", port = "8081")
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(classes = com.example.MedicoApp.MedicoApp.class)
@TestPropertySource(properties = "LICENSES_URL=http://localhost:8081")
public class PactApplicationTests {

    @Autowired
    private LicensesService licensesService;

    // Pact para creación exitosa de licencia
    @Pact(consumer = "MedicoApp")
    public V4Pact createPactForLicenseCreation(PactDslWithProvider builder) {
        return builder
                .given("Se puede crear una licencia")
                .uponReceiving("Solicitud de creación de licencia")
                .path("/licenses")
                .method("POST")
                .body("{\"patientId\":\"11111111-1\",\"doctorId\":\"D-123\",\"diagnosis\":\"Diagnostico\",\"days\":5}")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body("L-1001")
                .toPact(V4Pact.class);
    }

    // Test del Pact
    @Test
    @PactTestFor(pactMethod = "createPactForLicenseCreation")
    void testCreateLicense() {
        LicenseDto request = new LicenseDto("11111111-1", "D-123", "Diagnostico",5);
        String folio = licensesService.createLicencias(request);
        assertEquals("L-1001", folio);
    }

    @Pact(consumer = "MedicoApp")
    public V4Pact createPactForZeroDaysLicense(PactDslWithProvider builder) {
        return builder
                .given("No se puede crear una licencia con 0 días")
                .uponReceiving("Solicitud de creación de licencia con 0 días")
                .path("/licenses")
                .method("POST")
                .body("{\"patientId\":\"11111111-1\",\"doctorId\":\"D-123\",\"diagnosis\":\"Diagnostico\",\"days\":0}")
                .willRespondWith()
                .status(400)
                .headers(Map.of("Content-Type", "application/json"))
                .body("{\"error\":\"INVALID_DAYS\"}")
                .toPact(V4Pact.class);
    }

    // Test del Pact con 0 días
    @Test
    @PactTestFor(pactMethod = "createPactForZeroDaysLicense")
    void testCreateLicenseWithZeroDays() {
        LicenseDto request = new LicenseDto("11111111-1", "D-123", "Diagnostico", 0);

        try {
            licensesService.createLicencias(request);
        } catch (RuntimeException ex) {
            // Aquí puedes verificar el mensaje de error si tu servicio lo lanza
            assertEquals("INVALID_DAYS", ex.getMessage());
        }
    }



}
