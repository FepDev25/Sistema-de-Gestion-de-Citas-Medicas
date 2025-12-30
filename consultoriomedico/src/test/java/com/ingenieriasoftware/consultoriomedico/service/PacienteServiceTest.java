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
    @Test
    void testBuscarPorCedula_Exitoso() {
        // Arrange
        when(pacienteRepository.findByCedula("1234567890")).thenReturn(Optional.of(paciente));

        // Act
        Paciente pacienteEncontrado = pacienteService.buscarPorCedula("1234567890");

        // Assert
        assertThat(pacienteEncontrado).isNotNull();
        assertThat(pacienteEncontrado.getCedula()).isEqualTo("1234567890");
        assertThat(pacienteEncontrado.getNombres()).isEqualTo("María");
        assertThat(pacienteEncontrado.getApellidos()).isEqualTo("García");
        assertThat(pacienteEncontrado.getTelefono()).isEqualTo("0999123456");
        
        verify(pacienteRepository, times(1)).findByCedula("1234567890");
    }

    @Test
    void testBuscarPorCedula_PacienteNoEncontrado() {
        // Arrange
        when(pacienteRepository.findByCedula("9999999999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> pacienteService.buscarPorCedula("9999999999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paciente no encontrado");
        
        verify(pacienteRepository, times(1)).findByCedula("9999999999");
    }

    @Test
    void testCrearPaciente_ConTodosLosCampos() {
        // Arrange
        Paciente pacienteCompleto = new Paciente();
        pacienteCompleto.setId(2L);
        pacienteCompleto.setNombres("Carlos");
        pacienteCompleto.setApellidos("Rodríguez");
        pacienteCompleto.setCedula("0987654321");
        pacienteCompleto.setTelefono("0988765432");
        pacienteCompleto.setDireccion("Calle Secundaria 456");
        pacienteCompleto.setFechaNacimiento(LocalDate.of(1985, 8, 20));
        pacienteCompleto.setUsername("carlos.rodriguez");

        when(pacienteRepository.findByCedula("0987654321")).thenReturn(Optional.empty());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteCompleto);

        // Act
        Paciente resultado = pacienteService.crearPaciente(pacienteCompleto);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getNombres()).isEqualTo("Carlos");
        assertThat(resultado.getApellidos()).isEqualTo("Rodríguez");
        assertThat(resultado.getCedula()).isEqualTo("0987654321");
        assertThat(resultado.getUsername()).isEqualTo("carlos.rodriguez");
        assertThat(resultado.getFechaNacimiento()).isEqualTo(LocalDate.of(1985, 8, 20));
        
        verify(pacienteRepository, times(1)).findByCedula("0987654321");
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }
}