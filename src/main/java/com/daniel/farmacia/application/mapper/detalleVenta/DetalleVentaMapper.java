package com.daniel.farmacia.application.mapper.detalleVenta;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaRequestDto;
import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;
import com.daniel.farmacia.domain.entity.DetalleVenta;
import com.daniel.farmacia.domain.entity.Producto;
import com.daniel.farmacia.domain.entity.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleVentaMapper {

    public DetalleVentaResponseDto toDto(DetalleVenta detalleVenta) {

        return DetalleVentaResponseDto.builder()
                .idDetalle(detalleVenta.getId())
                .cantidad(detalleVenta.getCantidad())
                .precioUnitario(detalleVenta.getPrecioUnitario())
                .subTotal(detalleVenta.getSubtotal()) // ya calculado por el metodo de la entidad
                .idVenta(detalleVenta.getVenta().getId())
                .fecha(detalleVenta.getVenta().getFecha())
                .estado(detalleVenta.getVenta().getEstado())
                .idProducto(detalleVenta.getProducto().getId())
                .nombre(detalleVenta.getProducto().getNombre())
                .precio(detalleVenta.getProducto().getPrecio())
                .numeroReceta(detalleVenta.getNumeroReceta())
                .build();
    }

    // Resquest -> No calculamos el subtotal porque lo hace @PrePersist
    public DetalleVenta toEntity(DetalleVentaRequestDto requestDto, Venta venta, Producto producto) {
        return DetalleVenta.builder()
                .cantidad(requestDto.getCantidad())
                .precioUnitario(requestDto.getPrecioUnitario())
                .venta(venta)
                .producto(producto) // se asignara despues por ID, o se carga desde BD
                .numeroReceta(requestDto.getNumeroReceta())
                .build();
    }
}
