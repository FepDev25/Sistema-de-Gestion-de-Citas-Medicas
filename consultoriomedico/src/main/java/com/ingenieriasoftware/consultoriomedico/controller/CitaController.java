package com.ingenieriasoftware.consultoriomedico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieriasoftware.consultoriomedico.dto.CitaRequestDTO;
import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.service.CitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping("/agendar")
    public ResponseEntity<Cita> agendarCita(@Valid @RequestBody CitaRequestDTO citaDTO) {
        Cita cita = citaService.crearCita(
                citaDTO.getIdMedico(),
                citaDTO.getCedulaPaciente(),
                citaDTO.getFecha(),
                citaDTO.getHora(),
                citaDTO.getDuracion()
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

    //Cancelar Cita
    @PutMapping("/cancelar/{idCita}")
    public ResponseEntity<?> cancelarCita(@PathVariable Long idCita) {
        return ResponseEntity.ok(
                citaService.cancelarCita(idCita)
        );
    }

}
