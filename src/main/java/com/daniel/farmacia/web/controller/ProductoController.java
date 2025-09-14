package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.producto.ProductoRequestDto;
import com.daniel.farmacia.application.dto.producto.ProductoResponseDto;
import com.daniel.farmacia.application.service.IProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> listarProductos() {
        List<ProductoResponseDto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> buscarProductoPorId(@PathVariable Long id) {
        ProductoResponseDto producto = productoService.buscarProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<ProductoResponseDto>> listarProductoPorNombreCategoria(@PathVariable String nombreCategoria) {
        List<ProductoResponseDto> productos = productoService.listarProductoPorNombreCategoria(nombreCategoria);
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(@Valid @RequestBody ProductoRequestDto requestDto) {
        ProductoResponseDto producto = productoService.crearProducto(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDto requestDto) {
        ProductoResponseDto producto = productoService.actualizarProducto(id, requestDto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
