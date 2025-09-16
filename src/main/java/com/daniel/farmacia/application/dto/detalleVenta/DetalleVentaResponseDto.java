package com.daniel.farmacia.application.dto.detalleVenta;

import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DetalleVentaResponseDto {
    private Long idDetalle;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;

    private Long idVenta;
    private LocalDate fecha;
    private TipoEstadoVenta estado;

    private Long idProducto;
    private String nombre;
    private Double precio;

    private String numeroReceta;
}
