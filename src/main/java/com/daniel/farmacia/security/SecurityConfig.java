package com.daniel.farmacia.security;

import com.daniel.farmacia.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService userDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                        // ADMINISTRADOR
                        // Categorías
                        .requestMatchers(HttpMethod.POST, "/api/categorias").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasAuthority("ROLE_ADMIN")
                        // Productos
                        .requestMatchers(HttpMethod.POST, "/api/productos").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority("ROLE_ADMIN")
                        // Ventas (ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/ventas/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/ventas/**").hasAuthority("ROLE_ADMIN")
                        // Pagos (ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/pagos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pagos/**").hasAuthority("ROLE_ADMIN")

                        // FARMACEUTICO
                        // Categorías
                        .requestMatchers(HttpMethod.POST, "/api/categorias").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        // Productos
                        .requestMatchers(HttpMethod.POST, "/api/productos").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        // Ventas
                        .requestMatchers(HttpMethod.GET, "/api/ventas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO", "ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/ventas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO")
                        // Detalles
                        .requestMatchers(HttpMethod.GET, "/api/detalles/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO", "ROLE_CLIENTE")
                        // Pagos
                        .requestMatchers(HttpMethod.GET, "/api/pagos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_FARMACEUTICO", "ROLE_CLIENTE")

                        // CLIENTE
                        //Ventas
                        .requestMatchers(HttpMethod.POST, "/api/ventas").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/ventas/cliente/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                        // Pagos
                        .requestMatchers(HttpMethod.POST, "/api/pagos/venta/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/pagos/venta/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLIENTE")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(provider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // Configurar el codificador de contraseña
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticacion con detalles de usuario y codificacion de constraseñas
    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);  // Usa el servicio personalizado para cargar usuarios
        provider.setPasswordEncoder(passwordEncoder());  // Usa el codificador BCrypt
        return provider;
    }

    // Configura el AuthenticationManager, necesario para la autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Configuración de CORS para permitir solicitudes desde http://localhost:5173
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
