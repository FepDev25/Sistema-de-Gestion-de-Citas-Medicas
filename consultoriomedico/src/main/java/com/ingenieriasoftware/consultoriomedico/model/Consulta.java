package com.ingenieriasoftware.consultoriomedico.model;

import jakarta.persistence.*;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;

    private String diagnostico;

    private String observaciones;

    private String prescripcion;

    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    // Getters y setters
}
