package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    // listar todos los detalles de una venta
    List<DetalleVenta> findAllByVenta_Id(Long ventaId);

    // cargar un detalle con todas sus relaciones (para endpoint GET /detalles/{id})
    @Query("""
        SELECT d FROM DetalleVenta d
        JOIN FETCH d.venta v
        JOIN FETCH v.cliente
        JOIN FETCH d.producto
        WHERE d.id = :id
        """)
    Optional<DetalleVenta> findByIdWithRelations(@Param("id") Long id);
}
