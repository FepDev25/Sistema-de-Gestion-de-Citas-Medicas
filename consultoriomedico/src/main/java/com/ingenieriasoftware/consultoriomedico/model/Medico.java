package com.ingenieriasoftware.consultoriomedico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
//@Table(name = "medicos")
@Data
@EqualsAndHashCode(callSuper = true)
public class Medico extends Usuario {

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(nullable = false, unique = true, length = 50)
    private String licencia;
}