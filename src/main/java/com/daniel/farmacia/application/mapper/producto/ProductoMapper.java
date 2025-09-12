package com.daniel.farmacia.application.mapper.producto;

import com.daniel.farmacia.application.dto.producto.ProductoRequestDto;
import com.daniel.farmacia.application.dto.producto.ProductoResponseDto;
import com.daniel.farmacia.domain.entity.Categoria;
import com.daniel.farmacia.domain.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponseDto toDto(Producto producto) {
        return ProductoResponseDto.builder()
                .idProducto(producto.getId())
                .nombreProducto(producto.getNombre())
                .descripcionProducto(producto.getDescripcion())
                .precioProducto(producto.getPrecio())
                .stock(producto.getStock())
                .fechaVencimiento(producto.getFechaVencimiento())
                .lote(producto.getLote())
                .categoriaId(producto.getCategoria().getId())
                .nombreCategoria(producto.getCategoria().getNombreCategoria())
                .build();
    }

    public Producto toEntity(ProductoRequestDto requestDto, Categoria categoria) {
        return Producto.builder()
                .nombre(requestDto.getNombreProducto())
                .descripcion(requestDto.getDescripcionProducto())
                .precio(requestDto.getPrecioProducto())
                .stock(requestDto.getStock())
                .fechaVencimiento(requestDto.getFechaVencimiento())
                .lote(requestDto.getLote())
                .categoria(categoria)
                .build();
    }
}
