package com.ingenieriasoftware.consultoriomedico.service;

import java.time.LocalDate;
import java.util.Optional;

// importaciones estáticas para leer mejor los tests
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import com.ingenieriasoftware.consultoriomedico.repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock // Simulamos base de datos
    private PacienteRepository pacienteRepository;

    @InjectMocks // Probamos servicio real
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // datos de prueba que se reinician antes de cada test
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombres("María");
        paciente.setApellidos("García");
        paciente.setCedula("1234567890");
        paciente.setTelefono("0999123456");
        paciente.setDireccion("Av. Principal 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        paciente.setUsername("maria.garcia");
    }
    @Test
    void testCrearPaciente_Exitoso() {
        // Arrange
        when(pacienteRepository.findByCedula("1234567890")).thenReturn(Optional.empty());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        // Act
        Paciente pacienteCreado = pacienteService.crearPaciente(paciente);

        // Assert
        assertThat(pacienteCreado).isNotNull();
        assertThat(pacienteCreado.getNombres()).isEqualTo("María");
        assertThat(pacienteCreado.getApellidos()).isEqualTo("García");
        assertThat(pacienteCreado.getCedula()).isEqualTo("1234567890");
        assertThat(pacienteCreado.getTelefono()).isEqualTo("0999123456");
        assertThat(pacienteCreado.getDireccion()).isEqualTo("Av. Principal 123");
        
        verify(pacienteRepository, times(1)).findByCedula("1234567890");
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void testCrearPaciente_CedulaDuplicada() {
        // Arrange
        when(pacienteRepository.findByCedula("1234567890")).thenReturn(Optional.of(paciente));

        // Act & Assert
        assertThatThrownBy(() -> pacienteService.crearPaciente(paciente))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe un paciente con esa cédula");
        
        verify(pacienteRepository, times(1)).findByCedula("1234567890");
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }
}