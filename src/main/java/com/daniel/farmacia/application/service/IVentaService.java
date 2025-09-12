package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.venta.VentaRequestDto;
import com.daniel.farmacia.application.dto.venta.VentaResponseDto;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;

import java.util.List;

public interface IVentaService {
    List<VentaResponseDto> listarVentas();
    VentaResponseDto buscarVentaPorId(Long id);
    List<VentaResponseDto> listarVentasPorCliente(Long clienteId);
    VentaResponseDto crearVenta(VentaRequestDto ventaRequestDto);
    VentaResponseDto actualizarEstadoVenta(Long id, TipoEstadoVenta estado);
    void eliminarVenta(Long id); 
}
