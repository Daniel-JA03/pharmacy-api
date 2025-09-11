package com.daniel.farmacia.application.dto.rol;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolRequestDto {
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String name;
}
