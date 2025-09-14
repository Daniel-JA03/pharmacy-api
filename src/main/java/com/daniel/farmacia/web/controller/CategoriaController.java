package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.categoria.CategoriaRequestDto;
import com.daniel.farmacia.application.dto.categoria.CategoriaResponseDto;
import com.daniel.farmacia.application.service.ICategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final ICategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarCategorias() {
        List<CategoriaResponseDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarCategoriaPorId(@PathVariable Long id) {
        CategoriaResponseDto categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> crearCategoria(@Valid @RequestBody CategoriaRequestDto requestDto) {
        CategoriaResponseDto categoria = categoriaService.crearCategoria(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDto requestDto
    ) {
        CategoriaResponseDto categoria = categoriaService.actualizarCategoria(id, requestDto);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
