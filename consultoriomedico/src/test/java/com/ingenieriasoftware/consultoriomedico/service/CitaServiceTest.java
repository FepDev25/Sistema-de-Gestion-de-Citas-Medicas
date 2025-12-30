package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.CitaRepository;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private MedicoService medicoService;

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private CitaService citaService;

    private Medico medico;
    private Paciente paciente;
    private Cita cita;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // médico mock
        medico = new Medico();
        medico.setId(1L);
        medico.setNombres("Dr. Juan");
        medico.setApellidos("Pérez");
        medico.setEspecialidad("Cardiología");
        medico.setLicencia("LIC-12345");

        // paciente mock
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombres("María");
        paciente.setApellidos("García");
        paciente.setCedula("1234567890");

        // cita mock
        cita = new Cita();
        cita.setIdCita(1L);
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHora(LocalTime.of(10, 0));
        cita.setDuracion(30);
        cita.setEstado(EstadoCita.SOLICITADA);
    }

    }
