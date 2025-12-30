package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    // Flujo AAA: Arrange (o sea preparar), Act (ejecutar), Assert (verificar)

    @Test
    void testCrearCita_Exitoso() {
        // Arrange
        when(medicoService.buscarPorId(1L)).thenReturn(medico);
        when(pacienteService.buscarPorCedula("1234567890")).thenReturn(paciente);
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        // Act
        Cita citaCreada = citaService.crearCita(
                1L,
                "1234567890",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                30
        );

        // Assert
        assertThat(citaCreada).isNotNull();
        assertThat(citaCreada.getMedico()).isEqualTo(medico);
        assertThat(citaCreada.getPaciente()).isEqualTo(paciente);
        assertThat(citaCreada.getEstado()).isEqualTo(EstadoCita.SOLICITADA);
        
        verify(medicoService, times(1)).buscarPorId(1L);
        verify(pacienteService, times(1)).buscarPorCedula("1234567890");
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void testListarCitas() {
        // Arrange
        List<Cita> citas = Arrays.asList(cita, new Cita());
        when(citaRepository.findAll()).thenReturn(citas);

        // Act
        List<Cita> resultado = citaService.listarCitas();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        verify(citaRepository, times(1)).findAll();
    }

    @Test
    void testListarCitasPorMedico() {
        // Arrange
        List<Cita> citas = Arrays.asList(cita);
        when(citaRepository.findByMedico_Id(1L)).thenReturn(citas);

        // Act
        List<Cita> resultado = citaService.listarCitasPorMedico(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMedico()).isEqualTo(medico);
        verify(citaRepository, times(1)).findByMedico_Id(1L);
    }

    @Test
    void testListarCitasPorPaciente() {
        // Arrange
        List<Cita> citas = Arrays.asList(cita);
        when(pacienteService.buscarPorCedula("1234567890")).thenReturn(paciente);
        when(citaRepository.findByPacienteId(1L)).thenReturn(citas);

        // Act
        List<Cita> resultado = citaService.listarCitasPorPaciente("1234567890");

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPaciente()).isEqualTo(paciente);
        verify(pacienteService, times(1)).buscarPorCedula("1234567890");
        verify(citaRepository, times(1)).findByPacienteId(1L);
    }

    
}
