package com.daniel.farmacia.web.controller;

import com.daniel.farmacia.application.dto.login.LoginRequestDto;
import com.daniel.farmacia.application.dto.login.LoginResponseDto;
import com.daniel.farmacia.application.dto.registrar.RegistrarRequestDto;
import com.daniel.farmacia.application.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = service.authenticate(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegistrarRequestDto requestDto) {
        service.register(requestDto);
        Map<String, String> responseDto = new HashMap<>();
        responseDto.put("message", "Usuario registrado exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
