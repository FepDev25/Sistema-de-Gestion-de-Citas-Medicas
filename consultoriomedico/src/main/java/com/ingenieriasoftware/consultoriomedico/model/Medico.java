package com.ingenieriasoftware.consultoriomedico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

//Entidad que hereda de Usuario y comparte su tabla en BD
@Entity
//@Table(name = "medicos")
@Data
@EqualsAndHashCode(callSuper = true)// verifica también los atributos heredados de Usuario
public class Medico extends Usuario {
    
    @Column(nullable = false, length = 100)
    private String especialidad;
    //con esto evitamos que se repitan licencias en la BD
    @Column(nullable = false, unique = true, length = 50)
    private String licencia;
}