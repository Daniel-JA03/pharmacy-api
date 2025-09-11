package com.daniel.farmacia.application.dto.venta;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class VentaResponseDto {
    private Long idVenta;
    private LocalDate fecha;
    private Double total;
    private TipoEstadoVenta estado;

    private Long idCliente;
    private String username;
    private String nombres;
    private String apellidos;

    private List<DetalleVentaResponseDto> detalles;
}
