package com.ingenieriasoftware.consultoriomedico.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.PacienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Paciente crearPaciente(Paciente paciente) {
        if (pacienteRepository.findByCedula(paciente.getCedula()).isPresent()) {
            throw new ConflictException("Ya existe un paciente con esa cédula");
        }
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        return pacienteRepository.save(paciente);
    }

    public Paciente buscarPorCedula(String cedula) {
        return pacienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }
}
