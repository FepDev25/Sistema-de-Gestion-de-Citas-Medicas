package com.ingenieriasoftware.consultoriomedico.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieriasoftware.consultoriomedico.dto.MedicoRequestDTO;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    private final MedicoService medicoService;
    // Constructor
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
    
    @PostMapping
    public ResponseEntity<Medico> crear(@Valid @RequestBody MedicoRequestDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setNombres(medicoDTO.getNombres());
        medico.setApellidos(medicoDTO.getApellidos());
        medico.setUsername(medicoDTO.getUsername());
        medico.setPassword(medicoDTO.getPassword());
        medico.setEspecialidad(medicoDTO.getEspecialidad());
        medico.setLicencia(medicoDTO.getLicencia());

        return new ResponseEntity<>(medicoService.registrarMedico(medico), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Medico> listar() {
        return medicoService.listarMedicos();
    }
    
    @GetMapping("/{id}")
    public Medico buscar(@PathVariable Long id) {
        return medicoService.buscarPorId(id);
    }
}