package com.daniel.farmacia.application.dto.producto;

import com.daniel.farmacia.domain.entity.type.TipoProducto;
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
    private TipoProducto tipoProducto;
    private String marca;
    private Long categoriaId;
    private String nombreCategoria;
}
