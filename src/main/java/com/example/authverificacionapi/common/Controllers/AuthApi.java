package com.example.authverificacionapi.common.Controllers;

import com.example.authverificacionapi.common.constans.ApiPathConstants;
import com.example.authverificacionapi.common.dtos.TokenResponse;
import com.example.authverificacionapi.common.dtos.UserRequest;
import com.example.authverificacionapi.common.entities.UserModel;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiPathConstants.V1_ROOT_PATH + ApiPathConstants.AUTH_ROOT_PATH)

public interface AuthApi {
    @PostMapping(value = "/register")
    ResponseEntity<TokenResponse>createUser(@RequestBody @Valid UserRequest userRequest);

    @GetMapping
    ResponseEntity<String> getUserId(@RequestAttribute(name = "X-User-Id")String userId);

    @GetMapping("/busqueda"+ "/{id}")
    ResponseEntity<UserModel> getUser(@RequestAttribute(name = "X-User-Id")String userId ,@PathVariable Long id );

    @GetMapping("busquedaAll")
    ResponseEntity<List<UserModel>> getUsers(@RequestAttribute(name = "X-User-Id")String userId );

    @DeleteMapping("eliminar")
    ResponseEntity<UserModel> deleteUser(@RequestAttribute(name = "X-User-Id")String userId );

    @DeleteMapping("eliminarAdmin" + "/{id}")
    ResponseEntity<UserModel> deleteUserAdmin(@RequestAttribute(name = "X-User-Id")String userId , @PathVariable Long id );
    @PutMapping("/editUsuario")
    ResponseEntity<UserModel> updateUser(@RequestAttribute(name = "X-User-Id")String userId, @RequestBody @Valid UserRequest userRequest );
    @PutMapping("/editUsuarioAdmin"+ "/{id}")
    ResponseEntity<UserModel> updateUser(@RequestAttribute(name = "X-User-Id")String userId, @PathVariable Long id, @RequestBody @Valid UserRequest userRequest );


}
