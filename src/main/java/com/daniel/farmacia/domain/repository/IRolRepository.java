package com.daniel.farmacia.domain.repository;

import com.daniel.farmacia.domain.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, String> {
}
