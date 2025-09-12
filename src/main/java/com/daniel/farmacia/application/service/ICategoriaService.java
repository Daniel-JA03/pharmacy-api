package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.categoria.CategoriaRequestDto;
import com.daniel.farmacia.application.dto.categoria.CategoriaResponseDto;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaResponseDto> listarCategorias();
    CategoriaResponseDto buscarCategoriaPorId(Long id);
    CategoriaResponseDto crearCategoria(CategoriaRequestDto requestDto);
    CategoriaResponseDto actualizarCategoria(Long  id, CategoriaRequestDto requestDto);
    void eliminarCategoria(Long id);
}
