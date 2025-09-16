package com.daniel.farmacia.application.dto.detalleVenta;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetalleVentaRequestDto {
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    private Double precioUnitario;
    // private Double subTotal; se calcula automaticamente

    @NotNull(message = "El ID de la venta es obligatorio")
    private Long idVenta;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    private String numeroReceta; // "REC-2025-00123" o "N° 5678"
}