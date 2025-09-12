package com.daniel.farmacia.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Column(name = "fecha_vencimiento")
    @Future(message = "La fecha de vencimiento debe ser posterior a hoy")
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    @NotBlank(message = "El lote es obligatorio")
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();
}
