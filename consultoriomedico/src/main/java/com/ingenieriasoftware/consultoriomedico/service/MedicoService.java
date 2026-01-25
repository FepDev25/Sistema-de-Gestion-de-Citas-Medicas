package com.ingenieriasoftware.consultoriomedico.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.repository.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }
    // Método para registrar un nuevo médico
    public Medico registrarMedico(Medico medico) {
        if (medicoRepository.findByLicencia(medico.getLicencia()).isPresent()) {
            throw new ConflictException("La licencia " + medico.getLicencia() + " ya está registrada.");
        }
        medico.setRol("MEDICO");
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
    public boolean revisarAgenda(Long idMedico, Date fecha) {
        // Por ahora retornamos true hasta que se integre el módulo de Citas
        return true; 
    }
}