package com.ingenieriasoftware.consultoriomedico.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedico_Id(Long idMedico);
    List<Cita> findByPacienteId(Long idPaciente);
    List<Cita> findByMedico_IdAndFechaAndEstadoNot(Long idMedico, LocalDate fecha, EstadoCita estado);

}
