package com.daniel.farmacia.application.mapper.rol;

import com.daniel.farmacia.application.dto.rol.RolRequestDto;
import com.daniel.farmacia.application.dto.rol.RolResponseDto;
import com.daniel.farmacia.domain.entity.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public RolResponseDto toDto(Rol rol) {
        if (rol == null) return null;
        return RolResponseDto.builder()
                .name(rol.getName())
                .build();
    }

    public Rol toEntity(RolRequestDto rolRequestDto) {
        if (rolRequestDto == null) return null;
        return Rol.builder()
                .name(rolRequestDto.getName())
                .build();
    }
}
