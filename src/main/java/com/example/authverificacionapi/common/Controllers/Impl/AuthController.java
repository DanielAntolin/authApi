package com.example.authverificacionapi.common.Controllers.Impl;

import com.example.authverificacionapi.Services.AuthService;
import com.example.authverificacionapi.Services.JwtService;
import com.example.authverificacionapi.common.Controllers.AuthApi;
import com.example.authverificacionapi.common.dtos.TokenResponse;
import com.example.authverificacionapi.common.dtos.UserRequest;
import com.example.authverificacionapi.common.entities.UserModel;
import com.example.authverificacionapi.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;
    private UserRepository userRepository;
    private JwtService jwtService;
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<TokenResponse> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @Override
    public ResponseEntity<String> getUserId(String userId) {
        return ResponseEntity.ok(userId);
    }

    @Override
    public ResponseEntity<UserModel> getUser(String userId, Long id) {
        return ResponseEntity.ok(authService.getUser(id));
    }

    @Override
    public ResponseEntity<List<UserModel>> getUsers(String userId) {
        return ResponseEntity.ok(authService.getAllUsers(userId));
    }

    @Override
    public ResponseEntity<UserModel> deleteUser(String userId) {

            authService.deleteUser(userId);
            return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<UserModel> deleteUserAdmin(String userId, Long id) {
        authService.deleteUserAdmin(userId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserModel> updateUser(String userId, UserRequest userRequest) {
        authService.updateUser(userId, userRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserModel> updateUser(String userId, Long id, UserRequest userRequest) {
        authService.updateUserAdmin(userId, id, userRequest);
        return ResponseEntity.ok().build();
    }
/*
    @Override
    public ResponseEntity<UserModel> getUser(String userId) {
        return ResponseEntity.ok(authService.getUser(Long.parseLong(userId)));
    }
*/



}
