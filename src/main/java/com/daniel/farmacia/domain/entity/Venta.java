package com.daniel.farmacia.domain.entity;

import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long id;

    private LocalDate fecha;

    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_venta", nullable = false)
    private TipoEstadoVenta estado; // PENDIENTE, PAGADO, CANCELADO, ENTREGADO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY ,orphanRemoval = true)
    private Pago pago;

    // Metodo para calcular total basado en los detalles
    public void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum(); // Si está vacía, sum() devuelve 0.0 automáticamente
    }

    @PrePersist
    public void asignarFecha() {
        if (this.fecha == null) {
            this.fecha = LocalDate.now();
        }
    }
}
