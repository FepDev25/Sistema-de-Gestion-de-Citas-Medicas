package com.ingenieriasoftware.consultoriomedico.service;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    public Cita crearCita(Long idMedico,
                          String cedulaPaciente,
                          LocalDate fecha,
                          LocalTime hora,
                          Integer duracion) {

        // 1. Obtener médico (usa lógica existente)
        Medico medico = medicoService.buscarPorId(idMedico);

        // 2. Obtener paciente (usa lógica existente)
        Paciente paciente = pacienteService.buscarPorCedula(cedulaPaciente);

        // 3. Crear la cita
        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setDuracion(duracion);
        cita.setEstado(EstadoCita.SOLICITADA);

        // 4. Guardar y retornar
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
}
