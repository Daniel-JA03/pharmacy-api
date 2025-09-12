package com.daniel.farmacia.application.mapper.categoria;

import com.daniel.farmacia.application.dto.categoria.CategoriaRequestDto;
import com.daniel.farmacia.application.dto.categoria.CategoriaResponseDto;
import com.daniel.farmacia.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponseDto toDto(Categoria categoria) {
        return CategoriaResponseDto.builder()
                .categoriaId(categoria.getId())
                .nombreCategoria(categoria.getNombreCategoria())
                .build();
    }

    public Categoria toEntity(CategoriaRequestDto requestDto) {
        return Categoria.builder()
                .nombreCategoria(requestDto.getNombreCategoria())
                .build();
    }

    public Categoria toEntityCategoria(CategoriaResponseDto responseDto) {
        return Categoria.builder()
                .id(responseDto.getCategoriaId())
                .nombreCategoria(responseDto.getNombreCategoria())
                .build();
    }
}
