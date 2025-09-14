package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;
import com.daniel.farmacia.application.mapper.detalleVenta.DetalleVentaMapper;
import com.daniel.farmacia.application.service.IDetalleVentaService;
import com.daniel.farmacia.domain.entity.DetalleVenta;
import com.daniel.farmacia.domain.repository.IDetalleVentaRepository;
import com.daniel.farmacia.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio SOLO LECTURA
 * TODO: esta diseñada para reportes
 * la creación/actualización de detalles debe hacerse a través de IVentaService
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // Para evitar LazyInitException
public class DetalleVentaServiceImpl implements IDetalleVentaService {
    private final IDetalleVentaRepository detalleVentaRepository;
    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    public List<DetalleVentaResponseDto> listarDetallePorVentaId(Long ventaId) {
        return detalleVentaRepository.findAllByVenta_Id(ventaId).stream()
                .map(detalleVentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DetalleVentaResponseDto obtenerDetallePorId(Long id) {
        DetalleVenta detalle = detalleVentaRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + id));
        return detalleVentaMapper.toDto(detalle);
    }
}
