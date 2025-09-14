package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombreCategoriaIgnoreCase(String nombreCategoria);
}
