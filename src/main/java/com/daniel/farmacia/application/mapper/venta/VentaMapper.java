package com.daniel.farmacia.application.mapper.venta;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;
import com.daniel.farmacia.application.dto.venta.VentaRequestDto;
import com.daniel.farmacia.application.dto.venta.VentaResponseDto;
import com.daniel.farmacia.application.mapper.detalleVenta.DetalleVentaMapper;
import com.daniel.farmacia.domain.entity.DetalleVenta;
import com.daniel.farmacia.domain.entity.Usuario;
import com.daniel.farmacia.domain.entity.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VentaMapper {
    private final DetalleVentaMapper detalleVentaMapper;

    public VentaResponseDto toDto(Venta venta) {

        return VentaResponseDto.builder()
                .idVenta(venta.getId())
                .fecha(venta.getFecha())
                .total(venta.getTotal())
                .estado(venta.getEstado())
                .idCliente(venta.getCliente().getId())
                .username(venta.getCliente().getUsername())
                .nombres(venta.getCliente().getNombres())
                .apellidos(venta.getCliente().getApellidos())
                .detalles(venta.getDetalles().stream()
                        .map(detalleVentaMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Venta toEntity(VentaRequestDto requestDto, Usuario usuario) {
        return Venta.builder()
                .fecha(requestDto.getFecha() != null ? requestDto.getFecha() : LocalDate.now())
                .total(requestDto.getTotal()) // TODO: recalcularlo en servicio con calcularTotal()
                .estado(requestDto.getEstado())
                .cliente(usuario)
                .build();
    }
}
