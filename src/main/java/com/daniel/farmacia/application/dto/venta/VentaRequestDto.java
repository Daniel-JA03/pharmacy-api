package com.daniel.farmacia.application.dto.venta;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaRequestDto;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class VentaRequestDto {
    private LocalDate fecha; // se asigna automaticamente

    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private Double total;

    @NotNull(message = "El estado de la venta es obligatorio")
    private TipoEstadoVenta estado;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    private List<DetalleVentaRequestDto> detalles;
}
