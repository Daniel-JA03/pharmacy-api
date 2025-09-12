package com.daniel.farmacia.application.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDto {
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombreCategoria;
}
