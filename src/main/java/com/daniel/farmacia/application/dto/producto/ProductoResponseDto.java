package com.daniel.farmacia.application.dto.producto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProductoResponseDto {
    private Long idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioProducto;
    private Integer stock;
    private LocalDate fechaVencimiento;
    private String lote;
    private String categoria;
}
