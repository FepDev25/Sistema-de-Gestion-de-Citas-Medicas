package com.ingenieriasoftware.consultoriomedico.service;

import org.springframework.stereotype.Service;

import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente crearPaciente(Paciente paciente) {
        if (pacienteRepository.findByCedula(paciente.getCedula()).isPresent()) {
            throw new RuntimeException("Ya existe un paciente con esa cédula");
        }
        return pacienteRepository.save(paciente);
    }

    public Paciente buscarPorCedula(String cedula) {
        return pacienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }
}
