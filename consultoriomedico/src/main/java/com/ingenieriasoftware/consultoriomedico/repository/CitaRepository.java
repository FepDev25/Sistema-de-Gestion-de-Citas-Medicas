package com.ingenieriasoftware.consultoriomedico.repository;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedico_Id(Long idMedico);

    List<Cita> findByPacienteId(Long idPaciente);

}
