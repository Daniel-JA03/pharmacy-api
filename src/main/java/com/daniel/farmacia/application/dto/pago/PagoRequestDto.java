package com.daniel.farmacia.application.dto.pago;

import com.daniel.farmacia.domain.entity.type.TipoEstadoPago;
import com.daniel.farmacia.domain.entity.type.TipoMetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagoRequestDto {
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false ,message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El método de pago es obligatorio")
    private TipoMetodoPago metodo;

    @NotNull(message = "El estado del pago es obligatorio")
    private TipoEstadoPago estado;
    private LocalDateTime fechaPago; // se asigna automaticamente
    private String transactionId;
}
