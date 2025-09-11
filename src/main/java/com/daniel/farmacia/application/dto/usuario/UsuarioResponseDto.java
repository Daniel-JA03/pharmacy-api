package com.daniel.farmacia.application.dto.usuario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private List<String> roles;
}
