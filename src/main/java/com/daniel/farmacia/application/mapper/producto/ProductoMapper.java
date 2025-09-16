package com.daniel.farmacia.application.mapper.producto;

import com.daniel.farmacia.application.dto.producto.ProductoRequestDto;
import com.daniel.farmacia.application.dto.producto.ProductoResponseDto;
import com.daniel.farmacia.domain.entity.Categoria;
import com.daniel.farmacia.domain.entity.Producto;
import com.daniel.farmacia.domain.entity.type.TipoProducto;
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
                .tipoProducto(producto.getTipoProducto())
                .marca(producto.getMarca())
                .categoriaId(producto.getCategoria().getId())
                .nombreCategoria(producto.getCategoria().getNombreCategoria())
                .build();
    }

    public Producto toEntity(ProductoRequestDto requestDto, Categoria categoria) {
        TipoProducto tipoProducto = requestDto.getTipoProducto() != null
                ? requestDto.getTipoProducto()
                : TipoProducto.MARCA;

        String marca = requestDto.getMarca();
        if (tipoProducto == TipoProducto.GENERICO && marca == null) {
            marca = "Sin marca";
        }

        return Producto.builder()
                .nombre(requestDto.getNombreProducto())
                .descripcion(requestDto.getDescripcionProducto())
                .precio(requestDto.getPrecioProducto())
                .stock(requestDto.getStock())
                .fechaVencimiento(requestDto.getFechaVencimiento())
                .lote(requestDto.getLote())
                .tipoProducto(tipoProducto)
                .marca(marca)
                .categoria(categoria)
                .build();
    }
}
