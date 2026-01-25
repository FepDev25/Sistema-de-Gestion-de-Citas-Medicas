package com.ingenieriasoftware.consultoriomedico.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedico_Id(Long idMedico);
    List<Cita> findByPacienteId(Long idPaciente);
    List<Cita> findByMedico_IdAndFechaAndEstadoNot(Long idMedico, LocalDate fecha, EstadoCita estado);

    @Query(value = "SELECT COUNT(*) > 0 FROM cita c WHERE c.medico_id = :idMedico AND c.fecha = :fecha AND c.estado <> 'CANCELADA' AND c.hora < :horaFin AND (c.hora + (c.duracion * interval '1 minute')) > :horaInicio", nativeQuery = true)
    boolean existsOverlap(@Param("idMedico") Long idMedico, @Param("fecha") LocalDate fecha, @Param("horaInicio") LocalTime horaInicio, @Param("horaFin") LocalTime horaFin);

}
