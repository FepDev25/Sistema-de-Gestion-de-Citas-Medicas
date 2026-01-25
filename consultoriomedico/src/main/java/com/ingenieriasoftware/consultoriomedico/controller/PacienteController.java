package com.ingenieriasoftware.consultoriomedico.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieriasoftware.consultoriomedico.dto.PacienteRequestDTO;
import com.ingenieriasoftware.consultoriomedico.mapper.PacienteMapper;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;

    public PacienteController(PacienteService pacienteService, PacienteMapper pacienteMapper) {
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
    }

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody PacienteRequestDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        return new ResponseEntity<>(pacienteService.crearPaciente(paciente), HttpStatus.CREATED);
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
