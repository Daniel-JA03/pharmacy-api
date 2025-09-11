package com.daniel.farmacia.application.dto.login;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private String username;
    private List<String> roles;
    private Long usuarioId;
    private Long expirateAt;
}
