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
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody PacienteRequestDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombres(pacienteDTO.getNombres());
        paciente.setApellidos(pacienteDTO.getApellidos());
        paciente.setUsername(pacienteDTO.getUsername());
        paciente.setPassword(pacienteDTO.getPassword());
        paciente.setCedula(pacienteDTO.getCedula());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());

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
