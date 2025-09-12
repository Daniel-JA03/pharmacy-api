package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
}
