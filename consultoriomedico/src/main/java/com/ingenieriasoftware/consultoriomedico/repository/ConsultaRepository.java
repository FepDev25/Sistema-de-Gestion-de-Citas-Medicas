package com.ingenieriasoftware.consultoriomedico.repository;

import com.ingenieriasoftware.consultoriomedico.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
