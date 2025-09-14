package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.pago.PagoRequestDto;
import com.daniel.farmacia.application.dto.pago.PagoResponseDto;
import com.daniel.farmacia.application.service.IPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final IPagoService pagoService;

    @PostMapping("/venta/{ventaId}")
    public ResponseEntity<PagoResponseDto> crearPago(
            @PathVariable Long ventaId,
            @Valid @RequestBody PagoRequestDto requestDto) {
        PagoResponseDto responseDto = pagoService.crearPago(requestDto, ventaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> buscarPagoPorId(@PathVariable Long id) {
        PagoResponseDto pago = pagoService.buscarPagoPorId(id);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/venta/{ventaId}/pago")
    public ResponseEntity<PagoResponseDto> obtenerPagoPorVentaId(@PathVariable Long ventaId) {
        PagoResponseDto pago = pagoService.obtenerPagoPorVentaId(ventaId);
        return ResponseEntity.ok(pago);
    }
}
