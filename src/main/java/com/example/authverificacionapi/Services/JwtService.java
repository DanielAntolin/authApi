package com.example.authverificacionapi.Services;

import com.example.authverificacionapi.common.dtos.TokenResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {
    TokenResponse generateToken(Long userId);
    Claims getClaims(String token);
    boolean isTokenExpired(String token);
    Integer extractUserId(String token);
}
