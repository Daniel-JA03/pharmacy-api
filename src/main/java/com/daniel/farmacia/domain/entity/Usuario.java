package com.daniel.farmacia.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "El username es obligatorio")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Column(name = "nombre_usuario", nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @Column(name = "apellido_usuario", nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @Column(nullable = false)
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "nombre_rol")
    )
    private Set<Rol> roles = new HashSet<>();


    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Venta> pedidos = new ArrayList<>();
}
