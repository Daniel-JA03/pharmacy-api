package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.producto.ProductoRequestDto;
import com.daniel.farmacia.application.dto.producto.ProductoResponseDto;

import java.util.List;

public interface IProductoService {
    List<ProductoResponseDto> listarProductos();
    ProductoResponseDto buscarProductoPorId(Long id);
    List<ProductoResponseDto> listarProductoPorNombreCategoria(String nombreCategoria);
    ProductoResponseDto crearProducto(ProductoRequestDto requestDto);
    ProductoResponseDto actualizarProducto(Long id, ProductoRequestDto requestDto);
    void eliminarProducto(Long id);
}
