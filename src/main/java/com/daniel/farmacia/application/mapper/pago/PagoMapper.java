package com.daniel.farmacia.application.mapper.pago;

import com.daniel.farmacia.application.dto.pago.PagoRequestDto;
import com.daniel.farmacia.application.dto.pago.PagoResponseDto;
import com.daniel.farmacia.domain.entity.Pago;
import com.daniel.farmacia.domain.entity.Venta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagoMapper {

    public PagoResponseDto toDto(Pago pago) {
        return PagoResponseDto.builder()
                .idPago(pago.getId())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .transactionId(pago.getTransactionId())
                .build();
    }

    public Pago toEntity(PagoRequestDto requestDto, Venta venta) {
        return Pago.builder()
                .monto(requestDto.getMonto())
                .metodo(requestDto.getMetodo())
                .estado(requestDto.getEstado())
                .fechaPago(LocalDateTime.now()) // la fecha y hora del servidor
                .transactionId(requestDto.getTransactionId())
                .venta(venta)
                .build();
    }
}
