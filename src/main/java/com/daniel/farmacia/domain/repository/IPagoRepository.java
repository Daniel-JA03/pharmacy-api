package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByVentaId(Long ventaId);
}
