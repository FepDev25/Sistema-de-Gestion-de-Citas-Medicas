package com.ingenieriasoftware.consultoriomedico.controller;

import com.ingenieriasoftware.consultoriomedico.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarConsulta(
            @RequestParam Long idCita,
            @RequestParam String diagnostico,
            @RequestParam String observaciones,
            @RequestParam String prescripcion
    ) {
        return ResponseEntity.ok(
                consultaService.registrarConsulta(
                        idCita,
                        diagnostico,
                        observaciones,
                        prescripcion
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
