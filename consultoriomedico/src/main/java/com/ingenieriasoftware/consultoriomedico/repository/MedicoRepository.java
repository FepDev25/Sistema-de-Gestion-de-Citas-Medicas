package com.ingenieriasoftware.consultoriomedico.repository;

import com.ingenieriasoftware.consultoriomedico.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // método para buscar un médico por su licencia
    Optional<Medico> findByLicencia(String licencia);
    // método para buscar médicos por especialidad
    List<Medico> findByEspecialidad(String especialidad);
}