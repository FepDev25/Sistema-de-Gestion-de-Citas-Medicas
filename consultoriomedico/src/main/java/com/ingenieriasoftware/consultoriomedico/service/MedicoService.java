package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.repository.MedicoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public MedicoService(MedicoRepository medicoRepository, PasswordEncoder passwordEncoder) {
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Método para registrar un nuevo médico
    @Transactional
    public Medico registrarMedico(Medico medico) {
        if (medicoRepository.findByLicencia(medico.getLicencia()).isPresent()) {
            throw new ConflictException("La licencia " + medico.getLicencia() + " ya está registrada.");
        }
        medico.setRol("MEDICO");
        medico.setPassword(passwordEncoder.encode(medico.getPassword()));
        return medicoRepository.save(medico);
    }
    // Método para listar todos los médicos
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }
    // Método para buscar un médico por ID
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));
    }

    // Método requerido para integración con Citas
    public boolean revisarAgenda(Long idMedico, LocalDate fecha) {
        // Por ahora retornamos true hasta que se integre el módulo de Citas
        return true; 
    }
}