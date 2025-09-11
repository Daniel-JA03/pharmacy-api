package com.daniel.farmacia.application.dto.pago;

import com.daniel.farmacia.domain.entity.type.TipoEstadoPago;
import com.daniel.farmacia.domain.entity.type.TipoMetodoPago;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponseDto {
    private Long idPago;
    private Double monto;
    private TipoMetodoPago metodo;
    private TipoEstadoPago estado;
    private LocalDateTime fechaPago;
    private String transactionId;
}
