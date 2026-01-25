package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.CitaRepository;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public CitaService(CitaRepository citaRepository,
                       MedicoService medicoService,
                       PacienteService pacienteService) {
        this.citaRepository = citaRepository;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    /**
     * Crear una nueva cita médica
     */
    @Transactional
    public Cita crearCita(Long idMedico, String cedulaPaciente, LocalDate fecha, LocalTime horaInicio, Integer duracion) {

        Medico medico = medicoService.buscarPorId(idMedico);
        Paciente paciente = pacienteService.buscarPorCedula(cedulaPaciente);

        // verificar solapamiento de horarios
        LocalTime horaFin = horaInicio.plusMinutes(duracion);

        if (citaRepository.existsOverlap(idMedico, fecha, horaInicio, horaFin)) {
            throw new ConflictException("El médico ya tiene una cita agendada en ese horario");
        }

        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setFecha(fecha);
        cita.setHora(horaInicio);
        cita.setDuracion(duracion);
        cita.setEstado(EstadoCita.SOLICITADA);

        return citaRepository.save(cita);
    }

    /**
     * Listar todas las citas
     */
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    /**
     * Listar citas por médico
     */
    public List<Cita> listarCitasPorMedico(Long idMedico) {
        return citaRepository.findByMedico_Id(idMedico);
    }

    /**
     * Listar citas por paciente
     */
    public List<Cita> listarCitasPorPaciente(String cedulaPaciente) {
        Paciente paciente = pacienteService.buscarPorCedula(cedulaPaciente);
        return citaRepository.findByPacienteId(paciente.getId());
    }

    @Transactional
    public Cita cancelarCita(Long idCita) {

        // Buscar la cita
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        // Validar que no esté finalizada
        if (cita.getEstado() == EstadoCita.FINALIZADA) {
            throw new ConflictException("No se puede cancelar una cita ya atendida");
        }

        // Cambiar estado a CANCELADA
        cita.setEstado(EstadoCita.CANCELADA);

        // Guardar cambios
        return citaRepository.save(cita);
    }

}
