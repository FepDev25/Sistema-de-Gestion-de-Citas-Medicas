package com.ingenieriasoftware.consultoriomedico.repository;

import com.ingenieriasoftware.consultoriomedico.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByLicencia(String licencia);
    List<Medico> findByEspecialidad(String especialidad);
}