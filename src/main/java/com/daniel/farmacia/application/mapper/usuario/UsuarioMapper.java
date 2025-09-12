package com.daniel.farmacia.application.mapper.usuario;

import com.daniel.farmacia.application.dto.usuario.UsuarioRequestDto;
import com.daniel.farmacia.application.dto.usuario.UsuarioResponseDto;
import com.daniel.farmacia.domain.entity.Rol;
import com.daniel.farmacia.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioResponseDto toDto(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .telefono(usuario.getTelefono())
                .direccion(usuario.getDireccion())
                .roles(usuario.getRoles().stream()
                        .map(Rol::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    public Usuario toEntity(UsuarioRequestDto requestDto, Set<Rol> roles) {
        if (requestDto == null) return null;
        if (roles == null) throw new IllegalArgumentException("El rol no puede ser nulo");

        return Usuario.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .nombres(requestDto.getNombres())
                .apellidos(requestDto.getApellidos())
                .telefono(requestDto.getTelefono())
                .direccion(requestDto.getDireccion())
                .roles(roles)
                .build();
    }
}
