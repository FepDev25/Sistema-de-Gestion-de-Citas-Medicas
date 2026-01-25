package com.ingenieriasoftware.consultoriomedico.mapper;

import com.ingenieriasoftware.consultoriomedico.dto.PacienteRequestDTO;
import com.ingenieriasoftware.consultoriomedico.model.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", constant = "PACIENTE")
    Paciente toEntity(PacienteRequestDTO dto);
}
