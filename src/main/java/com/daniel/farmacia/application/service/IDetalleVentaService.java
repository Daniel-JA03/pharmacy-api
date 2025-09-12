package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;

import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVentaResponseDto> listarDetallePorVentaId(Long ventaId);
    DetalleVentaResponseDto obtenerDetallePorId(Long id);
}
