package com.ingenieriasoftware.consultoriomedico.repository;

import java.util.Optional;

import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCedula(String cedula);
}
