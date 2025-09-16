package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.categoria.CategoriaResponseDto;
import com.daniel.farmacia.application.dto.producto.ProductoRequestDto;
import com.daniel.farmacia.application.dto.producto.ProductoResponseDto;
import com.daniel.farmacia.application.mapper.categoria.CategoriaMapper;
import com.daniel.farmacia.application.mapper.producto.ProductoMapper;
import com.daniel.farmacia.application.service.ICategoriaService;
import com.daniel.farmacia.application.service.IProductoService;
import com.daniel.farmacia.domain.entity.Categoria;
import com.daniel.farmacia.domain.entity.Producto;
import com.daniel.farmacia.domain.repository.IProductoRepository;
import com.daniel.farmacia.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // para evitar LazyInitException en mappers
public class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ICategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDto> listarProductos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDto buscarProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDto> listarProductoPorNombreCategoria(String nombreCategoria) {
        return productoRepository.findByCategoria_NombreCategoria(nombreCategoria).stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoResponseDto crearProducto(ProductoRequestDto requestDto) {
        CategoriaResponseDto categoriaResponseDto = categoriaService.buscarCategoriaPorId(requestDto.getCategoriaId());
        Categoria categoria = categoriaMapper.toEntityCategoria(categoriaResponseDto);

        Producto producto = productoMapper.toEntity(requestDto, categoria);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    @Override
    public ProductoResponseDto actualizarProducto(Long id, ProductoRequestDto requestDto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        CategoriaResponseDto categoriaResponseDto = categoriaService.buscarCategoriaPorId(requestDto.getCategoriaId());
        Categoria categoria = categoriaMapper.toEntityCategoria(categoriaResponseDto);

        // Reutilizar el mapper para la actualizacion
        Producto productoActualizado = productoMapper.toEntity(requestDto, categoria);
        // mantener el ID existente
        productoActualizado.setId(producto.getId());
        productoRepository.save(productoActualizado);
        return productoMapper.toDto(productoActualizado);

    }

    @Override
    public void eliminarProducto(Long id) {
        // verificar si el producto existe
        if(!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado" + id);
        }

        // eliminar el producto
        productoRepository.deleteById(id);
    }
}
