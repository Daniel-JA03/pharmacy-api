package com.daniel.farmacia.application.mapper.pago;

import com.daniel.farmacia.application.dto.pago.PagoRequestDto;
import com.daniel.farmacia.application.dto.pago.PagoResponseDto;
import com.daniel.farmacia.domain.entity.Pago;
import com.daniel.farmacia.domain.entity.Venta;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoResponseDto toDto(Pago pago) {
        return PagoResponseDto.builder()
                .idPago(pago.getId())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .fechaPago(pago.getFechaPago())
                .transactionId(pago.getTransactionId())
                .build();
    }

    public Pago toEntity(PagoRequestDto requestDto, Venta venta) {
        return Pago.builder()
                .monto(requestDto.getMonto())
                .metodo(requestDto.getMetodo())
                .estado(requestDto.getEstado())
                .fechaPago(requestDto.getFechaPago())
                .transactionId(requestDto.getTransactionId())
                .venta(venta)
                .build();
    }
}
