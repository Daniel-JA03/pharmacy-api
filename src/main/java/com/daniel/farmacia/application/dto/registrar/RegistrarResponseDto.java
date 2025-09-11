package com.daniel.farmacia.application.dto.registrar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrarResponseDto {
    private Long id;
    private String username;
    private String message;
}
