package com.daniel.farmacia.domain.entity;

import com.daniel.farmacia.domain.entity.type.TipoEstadoPago;
import com.daniel.farmacia.domain.entity.type.TipoMetodoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long id;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false ,message = "El monto debe ser mayor a 0")
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private TipoMetodoPago metodo; // PAYPAL, TARJETA, YAPE, PLIN, EFECTIVO

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private TipoEstadoPago estado; // PENDIENTE, COMPLETADO, FALLIDO

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    private String transactionId; // ID de la pasarela de pago

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", unique = true, nullable = false)
    private Venta venta;

    @PrePersist
    public void asignarFechaPago() {
        if (this.fechaPago == null) {
            this.fechaPago = LocalDateTime.now();
        }
    }
}
