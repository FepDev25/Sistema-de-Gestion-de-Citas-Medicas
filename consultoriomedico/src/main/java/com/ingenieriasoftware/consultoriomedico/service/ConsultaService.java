package com.ingenieriasoftware.consultoriomedico.service;

import org.springframework.stereotype.Service;

import com.ingenieriasoftware.consultoriomedico.exception.ConflictException;
import com.ingenieriasoftware.consultoriomedico.exception.ResourceNotFoundException;
import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.Consulta;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;
import com.ingenieriasoftware.consultoriomedico.repository.CitaRepository;
import com.ingenieriasoftware.consultoriomedico.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final CitaRepository citaRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           CitaRepository citaRepository) {
        this.consultaRepository = consultaRepository;
        this.citaRepository = citaRepository;
    }

    public Consulta registrarConsulta(Long idCita,
                                      String diagnostico,
                                      String observaciones,
                                      String prescripcion) {

        //Buscar la cita
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        //Validar que no esté atendida
        if (cita.getEstado() == EstadoCita.FINALIZADA) {
            throw new ConflictException("La cita ya fue atendida");
        }

        //Validar que no exista consulta previa
        if (consultaRepository.findByCita_IdCita(idCita).isPresent()) {
            throw new ConflictException("La cita ya tiene una consulta registrada");
        }

        //Crear la consulta
        Consulta consulta = new Consulta();
        consulta.setCita(cita);
        consulta.setDiagnostico(diagnostico);
        consulta.setObservaciones(observaciones);
        consulta.setPrescripcion(prescripcion);

        //Guardar consulta
        Consulta consultaGuardada = consultaRepository.save(consulta);

        // Cambiar estado de la cita
        cita.setEstado(EstadoCita.FINALIZADA);
        citaRepository.save(cita);

        return consultaGuardada;
    }

    public Consulta buscarPorCita(Long idCita) {

    return consultaRepository.findByCita_IdCita(idCita)
            .orElseThrow(() ->
                    new ResourceNotFoundException("No existe consulta para la cita con id: " + idCita)
            );
    }

}
