package com.daniel.farmacia.application.dto.producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductoRequestDto {
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    private String descripcionProducto;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precioProducto;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Future(message = "La fecha de vencimiento debe ser posterior a hoy")
    private LocalDate fechaVencimiento;

    @NotBlank(message = "El lote es obligatorio")
    private String lote;

    private Long categoriaId;
}
