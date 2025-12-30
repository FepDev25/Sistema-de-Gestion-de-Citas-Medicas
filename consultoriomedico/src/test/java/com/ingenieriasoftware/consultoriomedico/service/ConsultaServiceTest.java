package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ingenieriasoftware.consultoriomedico.model.Cita;
import com.ingenieriasoftware.consultoriomedico.model.Consulta;
import com.ingenieriasoftware.consultoriomedico.model.EstadoCita;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.CitaRepository;
import com.ingenieriasoftware.consultoriomedico.repository.ConsultaRepository;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private ConsultaService consultaService;

    private Cita cita;
    private Consulta consulta;
    private Medico medico;
    private Paciente paciente;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Configurar médico
        medico = new Medico();
        medico.setId(1L);
        medico.setNombres("Dr. Juan");
        medico.setApellidos("Pérez");
        medico.setEspecialidad("Cardiología");

        // Configurar paciente
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombres("María");
        paciente.setApellidos("García");
        paciente.setCedula("1234567890");

        // Configurar cita
        cita = new Cita();
        cita.setIdCita(1L);
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setFecha(LocalDate.now());
        cita.setHora(LocalTime.of(10, 0));
        cita.setDuracion(30);
        cita.setEstado(EstadoCita.SOLICITADA);

        // Configurar consulta
        consulta = new Consulta();
        consulta.setIdConsulta(1L);
        consulta.setCita(cita);
        consulta.setDiagnostico("Hipertensión arterial");
        consulta.setObservaciones("Paciente presenta presión alta");
        consulta.setPrescripcion("Losartán 50mg, 1 vez al día");
    }
    
    @Test
    void testRegistrarConsulta_Exitoso() {
        // Arrange
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(consultaRepository.findByCita_IdCita(1L)).thenReturn(Optional.empty());
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);

        // Act
        Consulta consultaRegistrada = consultaService.registrarConsulta(
                1L,
                "Hipertensión arterial",
                "Paciente presenta presión alta",
                "Losartán 50mg, 1 vez al día"
        );

        // Assert
        assertThat(consultaRegistrada).isNotNull();
        assertThat(consultaRegistrada.getDiagnostico()).isEqualTo("Hipertensión arterial");
        assertThat(consultaRegistrada.getObservaciones()).isEqualTo("Paciente presenta presión alta");
        assertThat(consultaRegistrada.getPrescripcion()).isEqualTo("Losartán 50mg, 1 vez al día");
        assertThat(consultaRegistrada.getCita()).isEqualTo(cita);
        
        verify(citaRepository, times(1)).findById(1L);
        verify(consultaRepository, times(1)).findByCita_IdCita(1L);
        verify(consultaRepository, times(1)).save(any(Consulta.class));
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void testRegistrarConsulta_CitaNoEncontrada() {
        // Arrange
        when(citaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> consultaService.registrarConsulta(
                1L,
                "Diagnóstico",
                "Observaciones",
                "Prescripción"
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cita no encontrada");
        
        verify(citaRepository, times(1)).findById(1L);
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testRegistrarConsulta_CitaYaAtendida() {
        // Arrange
        cita.setEstado(EstadoCita.FINALIZADA);
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        // Act & Assert
        assertThatThrownBy(() -> consultaService.registrarConsulta(
                1L,
                "Diagnóstico",
                "Observaciones",
                "Prescripción"
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("La cita ya fue atendida");
        
        verify(citaRepository, times(1)).findById(1L);
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testRegistrarConsulta_ConsultaYaExiste() {
        // Arrange
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(consultaRepository.findByCita_IdCita(1L)).thenReturn(Optional.of(consulta));

        // Act & Assert
        assertThatThrownBy(() -> consultaService.registrarConsulta(
                1L,
                "Diagnóstico",
                "Observaciones",
                "Prescripción"
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("La cita ya tiene una consulta registrada");
        
        verify(citaRepository, times(1)).findById(1L);
        verify(consultaRepository, times(1)).findByCita_IdCita(1L);
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testBuscarPorCita_Exitoso() {
        // Arrange
        when(consultaRepository.findByCita_IdCita(1L)).thenReturn(Optional.of(consulta));

        // Act
        Consulta consultaEncontrada = consultaService.buscarPorCita(1L);

        // Assert
        assertThat(consultaEncontrada).isNotNull();
        assertThat(consultaEncontrada.getIdConsulta()).isEqualTo(1L);
        assertThat(consultaEncontrada.getCita()).isEqualTo(cita);
        assertThat(consultaEncontrada.getDiagnostico()).isEqualTo("Hipertensión arterial");
        
        verify(consultaRepository, times(1)).findByCita_IdCita(1L);
    }

    @Test
    void testBuscarPorCita_ConsultaNoEncontrada() {
        // Arrange
        when(consultaRepository.findByCita_IdCita(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> consultaService.buscarPorCita(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe consulta para la cita con id: 1");
        
        verify(consultaRepository, times(1)).findByCita_IdCita(1L);
    }

    @Test
    void testRegistrarConsulta_VerificaCambioEstadoCita() {
        // Arrange
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(consultaRepository.findByCita_IdCita(1L)).thenReturn(Optional.empty());
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        
        Cita citaCapturada = new Cita();
        citaCapturada.setIdCita(1L);
        citaCapturada.setEstado(EstadoCita.FINALIZADA);
        
        when(citaRepository.save(any(Cita.class))).thenReturn(citaCapturada);

        // Act
        consultaService.registrarConsulta(1L, "Diagnóstico", "Observaciones", "Prescripción");

        // Assert
        verify(citaRepository).save(argThat(c -> 
            c.getEstado() == EstadoCita.FINALIZADA
        ));
    }
}