package com.ingenieriasoftware.consultoriomedico.service;


import com.ingenieriasoftware.consultoriomedico.model.Medico;
import com.ingenieriasoftware.consultoriomedico.repository.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}

