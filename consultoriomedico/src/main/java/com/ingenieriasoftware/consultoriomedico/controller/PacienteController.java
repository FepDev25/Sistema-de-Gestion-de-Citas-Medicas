package com.ingenieriasoftware.consultoriomedico.controller;

import org.springframework.web.bind.annotation.*;

import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return pacienteService.crearPaciente(paciente);
    }

    @GetMapping("/{cedula}")
    public Paciente obtenerPaciente(@PathVariable String cedula) {
        return pacienteService.buscarPorCedula(cedula);
    }

    @GetMapping("/{cedula}/historial")
    public String obtenerHistorial(@PathVariable String cedula) {
        return "Historial médico del paciente con cédula " + cedula;
    }
}
