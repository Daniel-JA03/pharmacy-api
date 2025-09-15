package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.login.LoginRequestDto;
import com.daniel.farmacia.application.dto.login.LoginResponseDto;
import com.daniel.farmacia.application.dto.registrar.RegistrarRequestDto;
import com.daniel.farmacia.application.service.IAuthService;
import com.daniel.farmacia.domain.entity.Rol;
import com.daniel.farmacia.domain.entity.Usuario;
import com.daniel.farmacia.domain.repository.IRolRepository;
import com.daniel.farmacia.domain.repository.IUsuarioRepository;
import com.daniel.farmacia.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final IRolRepository rolRepository;
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String token = jwtUtil.generateToken(user);
        long expiration = jwtUtil.extractExpiration(token).getTime();

        // Obtener el usuario completo para obtener el ID
        Usuario usuario = usuarioRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return LoginResponseDto.builder()
                .token(token)
                .username(usuario.getUsername())
                .roles(user.getAuthorities().stream()
                        .map(r -> r.getAuthority())
                        .toList())
                .usuarioId(usuario.getId())
                .expirateAt(expiration)
                .build();
    }

    @Override
    public String register(RegistrarRequestDto registrarRequestDto) {
        // Validamos que el username no exista
        if (usuarioRepository.existsByUsername(registrarRequestDto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Validamos que el telefono no exista
        if(usuarioRepository.existsByTelefono(registrarRequestDto.getTelefono())) {
            throw new RuntimeException("El telefono ya está registrado");
        }

        // Obtener rol por defecto (CLIENTE)
        Rol rolUser = rolRepository.findById("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado en la base de datos"));

        // Construir el usuario
        Usuario usuario = Usuario.builder()
                .username(registrarRequestDto.getUsername())
                .password(passwordEncoder.encode(registrarRequestDto.getPassword()))
                .nombres(registrarRequestDto.getNombres())
                .apellidos(registrarRequestDto.getApellidos())
                .telefono(registrarRequestDto.getTelefono())
                .direccion(registrarRequestDto.getDireccion())
                .roles(Set.of(rolUser))
                .build();
        usuarioRepository.save(usuario);
        return "Usuario Registrado exitosamente";
    }
}
