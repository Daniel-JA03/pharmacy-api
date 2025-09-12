package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IVentaRepository extends JpaRepository<Venta, Long> {

    // listar las compras de un cliente
    List<Venta> findByCliente_Id(Long clienteId);

    // carga venta + cliente + detalles + productos (para mappers)
    @Query("""
        SELECT DISTINCT v FROM Venta v
        JOIN FETCH v.cliente
        LEFT JOIN FETCH v.detalles d
        LEFT JOIN FETCH d.producto
        WHERE v.id = :id
    """)
    Optional<Venta> findByIdWithRelations(@Param("id") Long id);

    // listar ventas completas (para reportes / historial)
    @Query("""
        SELECT DISTINCT v FROM Venta v
        JOIN FETCH v.cliente
        LEFT JOIN FETCH v.detalles d
        LEFT JOIN FETCH d.producto
        """)
    List<Venta> findAllWithRelations();
}
