package com.ingenieriasoftware.consultoriomedico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieriasoftware.consultoriomedico.dto.ConsultaRequestDTO;
import com.ingenieriasoftware.consultoriomedico.service.ConsultaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarConsulta(@Valid @RequestBody ConsultaRequestDTO consultaDTO) {
        return ResponseEntity.ok(
                consultaService.registrarConsulta(
                        consultaDTO.getIdCita(),
                        consultaDTO.getDiagnostico(),
                        consultaDTO.getObservaciones(),
                        consultaDTO.getPrescripcion()
                )
        );
    }

    @GetMapping("/cita/{idCita}")
    public ResponseEntity<?> obtenerConsultaPorCita(@PathVariable Long idCita) {
        return ResponseEntity.ok(
                consultaService.buscarPorCita(idCita)
        );
    }
}
