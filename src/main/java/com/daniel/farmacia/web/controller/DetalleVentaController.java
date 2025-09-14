package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaResponseDto;
import com.daniel.farmacia.application.service.IDetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
public class DetalleVentaController {
    private final IDetalleVentaService detalleVentaService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVentaResponseDto>> listarDetallePorVentaId(@PathVariable Long ventaId) {
        List<DetalleVentaResponseDto> detalleVentas = detalleVentaService.listarDetallePorVentaId(ventaId);
        return ResponseEntity.ok(detalleVentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaResponseDto> obtenerDetallePorId(@PathVariable Long id) {
        DetalleVentaResponseDto detalleVenta = detalleVentaService.obtenerDetallePorId(id);
        return ResponseEntity.ok(detalleVenta);
    }
}
