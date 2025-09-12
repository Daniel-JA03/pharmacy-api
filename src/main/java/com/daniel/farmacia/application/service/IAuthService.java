package com.daniel.farmacia.application.service;

import com.daniel.farmacia.application.dto.login.LoginRequestDto;
import com.daniel.farmacia.application.dto.login.LoginResponseDto;
import com.daniel.farmacia.application.dto.registrar.RegistrarRequestDto;

public interface IAuthService {
    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
    String register(RegistrarRequestDto registrarRequestDto);
}
