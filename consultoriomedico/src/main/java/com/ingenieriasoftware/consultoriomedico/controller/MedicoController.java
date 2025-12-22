package com.ingenieriasoftware.consultoriomedico.controller;

import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.service.MedicoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    private final MedicoService medicoService;
    // Constructor
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
    
    @PostMapping
    public Medico crear(@RequestBody Medico medico) {
        return medicoService.registrarMedico(medico);
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