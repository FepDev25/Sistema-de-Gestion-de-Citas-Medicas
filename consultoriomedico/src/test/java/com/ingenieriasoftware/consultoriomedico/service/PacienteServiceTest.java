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
}