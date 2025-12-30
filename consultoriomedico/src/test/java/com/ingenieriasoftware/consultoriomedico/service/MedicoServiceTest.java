package com.ingenieriasoftware.consultoriomedico.service;


import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.repository.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    private Medico medico;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        medico = new Medico();
        medico.setId(1L);
        medico.setNombres("Dr. Juan");
        medico.setApellidos("Pérez");
        medico.setEspecialidad("Cardiología");
        medico.setLicencia("LIC-12345");
        medico.setUsername("juan.perez");
    }
    @Test
    void testRegistrarMedico_Exitoso() {
        // Arrange
        when(medicoRepository.findByLicencia("LIC-12345")).thenReturn(Optional.empty());
        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);

        // Act
        Medico medicoRegistrado = medicoService.registrarMedico(medico);

        // Assert
        assertThat(medicoRegistrado).isNotNull();
        assertThat(medicoRegistrado.getNombres()).isEqualTo("Dr. Juan");
        assertThat(medicoRegistrado.getApellidos()).isEqualTo("Pérez");
        assertThat(medicoRegistrado.getRol()).isEqualTo("MEDICO");
        assertThat(medicoRegistrado.getEspecialidad()).isEqualTo("Cardiología");

        verify(medicoRepository, times(1)).findByLicencia("LIC-12345");
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    void testRegistrarMedico_LicenciaDuplicada() {
        // Arrange
        when(medicoRepository.findByLicencia("LIC-12345")).thenReturn(Optional.of(medico));

        // Act & Assert
        assertThatThrownBy(() -> medicoService.registrarMedico(medico))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("La licencia LIC-12345 ya está registrada");

        verify(medicoRepository, times(1)).findByLicencia("LIC-12345");
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testListarMedicos() {
        // Arrange
        Medico medico2 = new Medico();
        medico2.setId(2L);
        medico2.setNombres("Dra. Ana");
        medico2.setApellidos("López");
        medico2.setEspecialidad("Pediatría");

        List<Medico> medicos = Arrays.asList(medico, medico2);
        when(medicoRepository.findAll()).thenReturn(medicos);

        // Act
        List<Medico> resultado = medicoService.listarMedicos();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).contains(medico, medico2);
        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    void testListarMedicos_ListaVacia() {
        // Arrange
        when(medicoRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Medico> resultado = medicoService.listarMedicos();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();
        verify(medicoRepository, times(1)).findAll();
    }


    @Test
    void testRevisarAgenda() {
        // Arrange
        Date fecha = new Date();

        // Act
        boolean resultado = medicoService.revisarAgenda(1L, fecha);

        // Assert
        assertThat(resultado).isTrue();
    }
    @Test
    void testBuscarPorId_Exitoso() {
        // Arrange
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));

        // Act
        Medico medicoEncontrado = medicoService.buscarPorId(1L);

        // Assert
        assertThat(medicoEncontrado).isNotNull();
        assertThat(medicoEncontrado.getId()).isEqualTo(1L);
        assertThat(medicoEncontrado.getNombres()).isEqualTo("Dr. Juan");
        assertThat(medicoEncontrado.getApellidos()).isEqualTo("Pérez");
        verify(medicoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_MedicoNoEncontrado() {
        // Arrange
        when(medicoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> medicoService.buscarPorId(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Médico no encontrado");

        verify(medicoRepository, times(1)).findById(1L);
    }

}


