package com.ingenieriasoftware.consultoriomedico.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public Paciente crearPaciente(Paciente paciente) {
        if (pacienteRepository.findByCedula(paciente.getCedula()).isPresent()) {
            throw new ConflictException("Ya existe un paciente con esa cédula");
        }
        return pacienteRepository.save(paciente);
    }

    public Paciente buscarPorCedula(String cedula) {
        return pacienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }
}
