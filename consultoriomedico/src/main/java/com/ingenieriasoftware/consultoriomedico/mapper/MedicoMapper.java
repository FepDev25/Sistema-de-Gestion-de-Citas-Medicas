package com.ingenieriasoftware.consultoriomedico.mapper;

import com.ingenieriasoftware.consultoriomedico.dto.MedicoRequestDTO;
import com.ingenieriasoftware.consultoriomedico.model.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", constant = "MEDICO")
    Medico toEntity(MedicoRequestDTO dto);
}
