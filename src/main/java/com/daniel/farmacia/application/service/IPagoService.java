package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.pago.PagoRequestDto;
import com.daniel.farmacia.application.dto.pago.PagoResponseDto;

/**
 * Servicio SOLO LECTURA
 * TODO: esta diseñada para reportes
 * la creación/actualización de detalles debe hacerse a través de IVentaService
 */
public interface IPagoService {
    PagoResponseDto crearPago(PagoRequestDto pago, Long ventaId);
    PagoResponseDto buscarPagoPorId(Long id);
    PagoResponseDto obtenerPagoPorVentaId(Long ventaId);
}
