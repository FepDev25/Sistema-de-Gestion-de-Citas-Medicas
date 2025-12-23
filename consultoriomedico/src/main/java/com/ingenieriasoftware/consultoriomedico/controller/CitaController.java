package com.ingenieriasoftware.consultoriomedico.controller;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<Cita> agendarCita(
            @RequestParam Long idMedico,
            @RequestParam String cedulaPaciente,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime hora,
            @RequestParam Integer duracion
    ) {
        Cita cita = citaService.crearCita(
                idMedico,
                cedulaPaciente,
                fecha,
                hora,
                duracion
        );

        return ResponseEntity.ok(cita);
    }

    //LISTAR TODAS LAS CITAS
    @GetMapping
    public ResponseEntity<?> listarTodasLasCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    //LISTAR CITAS POR MÉDICO
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> listarCitasPorMedico(@PathVariable Long idMedico) {
        return ResponseEntity.ok(citaService.listarCitasPorMedico(idMedico));
    }

    //LISTAR CITAS POR PACIENTE
    @GetMapping("/paciente/{cedula}")
    public ResponseEntity<?> listarCitasPorPaciente(@PathVariable String cedula) {
        return ResponseEntity.ok(citaService.listarCitasPorPaciente(cedula));
    }

}
