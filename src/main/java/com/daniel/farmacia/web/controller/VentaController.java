package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.venta.VentaRequestDto;
import com.daniel.farmacia.application.dto.venta.VentaResponseDto;
import com.daniel.farmacia.application.service.IVentaService;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    private final IVentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listarVentas() {
        List<VentaResponseDto> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> buscarVentaPorId(@PathVariable Long id) {
        VentaResponseDto venta = ventaService.buscarVentaPorId(id);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaResponseDto>> listarVentasPorCliente(@PathVariable Long clienteId) {
        List<VentaResponseDto> ventas = ventaService.listarVentasPorCliente(clienteId);
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    public ResponseEntity<VentaResponseDto> crearVenta(@Valid @RequestBody VentaRequestDto requestDto) {
        VentaResponseDto venta = ventaService.crearVenta(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(venta);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<VentaResponseDto> actualizarVenta(
            @PathVariable Long id,
            @Valid @RequestParam TipoEstadoVenta estado
            ) {
        VentaResponseDto venta = ventaService.actualizarEstadoVenta(id, estado);
        return ResponseEntity.ok(venta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
