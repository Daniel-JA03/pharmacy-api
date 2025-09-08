package com.daniel.farmacia.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Long id;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad = 1; // Inicializado

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    @Column(name = "precio_unitario")
    private Double precioUnitario = 0.0; // Inicializado

    private Double subtotal = 0.0; // Inicializado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Calcular subtotal automáticamente antes de guardaro actualizar la entidad
    @PrePersist // calcula el subtotal cuando se crea un nuevo detalle
    @PreUpdate // recalcula el subtotal si se modifica cantidad o precio
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }
}
