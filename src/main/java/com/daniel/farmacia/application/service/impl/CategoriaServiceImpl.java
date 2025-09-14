package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.categoria.CategoriaRequestDto;
import com.daniel.farmacia.application.dto.categoria.CategoriaResponseDto;
import com.daniel.farmacia.application.mapper.categoria.CategoriaMapper;
import com.daniel.farmacia.application.service.ICategoriaService;
import com.daniel.farmacia.domain.entity.Categoria;
import com.daniel.farmacia.domain.repository.ICategoriaRepository;
import com.daniel.farmacia.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements ICategoriaService {
    private final ICategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDto buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
    }

    @Override
    public CategoriaResponseDto crearCategoria(CategoriaRequestDto requestDto) {
        if (categoriaRepository.existsByNombreCategoriaIgnoreCase(requestDto.getNombreCategoria())) {
            throw new ResourceNotFoundException("Ya existe una categoría con el nombre: " + requestDto.getNombreCategoria());
        }

        Categoria categoria = categoriaMapper.toEntity(requestDto);
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponseDto actualizarCategoria(Long id, CategoriaRequestDto requestDto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + id));
        categoria.setNombreCategoria(requestDto.getNombreCategoria());

        Categoria actualizarCategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(actualizarCategoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
