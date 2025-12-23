package com.ingenieriasoftware.consultoriomedico.repository;

import com.ingenieriasoftware.consultoriomedico.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Optional<Consulta> findByCita_IdCita(Long idCita);
}
