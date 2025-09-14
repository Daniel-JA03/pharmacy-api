package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.pago.PagoRequestDto;
import com.daniel.farmacia.application.dto.pago.PagoResponseDto;
import com.daniel.farmacia.application.mapper.pago.PagoMapper;
import com.daniel.farmacia.application.service.IPagoService;
import com.daniel.farmacia.domain.entity.Pago;
import com.daniel.farmacia.domain.entity.Venta;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import com.daniel.farmacia.domain.repository.IPagoRepository;
import com.daniel.farmacia.domain.repository.IVentaRepository;
import com.daniel.farmacia.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiceImpl implements IPagoService {
    private final IPagoRepository pagoRepository;
    private final IVentaRepository ventaRepository;
    private final PagoMapper pagoMapper;

    @Override
    public PagoResponseDto crearPago(PagoRequestDto requestDto, Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrado con ID: " + ventaId));

        Pago pago = pagoMapper.toEntity(requestDto, venta);
        pago = pagoRepository.save(pago);

        // actualizar estado de la venta a "PAGADO"
        venta.setEstado(TipoEstadoVenta.PAGADO);
        ventaRepository.save(venta);

        return pagoMapper.toDto(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDto buscarPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        return pagoMapper.toDto(pago);
    }

    @Override
    public PagoResponseDto obtenerPagoPorVentaId(Long ventaId) {
        Pago pago = pagoRepository.findByVentaId(ventaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + ventaId));
        return pagoMapper.toDto(pago);
    }
}
