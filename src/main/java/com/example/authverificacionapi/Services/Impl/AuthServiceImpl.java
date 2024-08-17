package com.example.authverificacionapi.Services.Impl;

import com.example.authverificacionapi.Services.AuthService;
import com.example.authverificacionapi.Services.JwtService;
import com.example.authverificacionapi.common.dtos.TokenResponse;
import com.example.authverificacionapi.common.dtos.UserRequest;
import com.example.authverificacionapi.common.entities.UserModel;
import com.example.authverificacionapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated -> jwtService.generateToken(userCreated.getId()))
                .orElseThrow(() -> new RuntimeException(" Error No se puede crear el usuario"));
    }

    @Override
    public UserModel getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario not found"));
    }

    @Override
    public List<UserModel> getAllUsers(String token) {
        Long idToken = Long.parseLong(token);
        System.out.println("el id recibido del token es : "+ idToken);
        if (token == null) {
            throw new RuntimeException("Token inválido");
        }


        Optional<UserModel> userOptional = userRepository.findById(idToken);
        if (userOptional.isPresent()) {
            try {
                 UserModel user = userOptional.get();
                 if(user.getRole().equals("ADMIN")){
                     return userRepository.findAll();
                 }
                 else{
                     throw new RuntimeException("El usuario no tiene role ADMIN");
                 }

            } catch (Exception e) {
                throw new RuntimeException("Error al Listar todos los usuarios");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }

    }

    @Override
    public void deleteUser(String token) {

        Long idToken = Long.parseLong(token);
         System.out.println("el id recibido del token es : "+ idToken);

        if (token == null) {
            throw new RuntimeException("Token inválido");
        }


        Optional<UserModel> userOptional = userRepository.findById(idToken);
        if (userOptional.isPresent()) {
            try {

                userRepository.delete(userOptional.get());
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar el usuario");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public void deleteUserAdmin(String token , Long idElim) {
        Long idToken = Long.parseLong(token);
        System.out.println("el id recibido del token es : "+ idToken);

        if (token == null) {
            throw new RuntimeException("Token inválido");
        }


        Optional<UserModel> userOptional = userRepository.findById(idToken);
        if (userOptional.isPresent()) {

                UserModel user = userOptional.get();
                if(user.getRole().equals("ADMIN")){
                    userRepository.deleteById(idElim);
                }
                else{
                    throw new RuntimeException("Usuario no es admin");
                }


        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public void updateUser(String token, UserRequest userRequest) {
        Long idToken = Long.parseLong(token);
        System.out.println("el id recibido del token es : "+ idToken);

        if (token == null) {
            throw new RuntimeException("Token inválido");
        }
        try {
            Optional<UserModel> userOptional = userRepository.findById(idToken);
            if (userOptional.isPresent()) {
                UserModel user = userOptional.get();
                user.setName(userRequest.getName());
                user.setEmail(userRequest.getEmail());
                user.setPassword(userRequest.getPassword());
                user.setRole(userRequest.getRole());
                userRepository.save(user);
            }
            else{
                throw new RuntimeException("Usuario no encontrado");
            }


        }catch (Exception e) {
            throw new RuntimeException("Error al modificar el usuario");
        }
    }

    @Override
    public void updateUserAdmin(String token,Long idUser, UserRequest userRequest) {
        if (token == null) {
            throw new RuntimeException("Token inválido");
        }

        Long idToken = Long.parseLong(token);
        System.out.println("El ID recibido del token es: " + idToken);

        UserModel user = userRepository.findById(idToken)
                .orElseThrow(() -> new RuntimeException("Usuario del token no encontrado"));

        if (!"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Usuario no es admin");
        }

        UserModel userUpdate = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario a actualizar no encontrado"));

        userUpdate.setName(userRequest.getName());
        userUpdate.setEmail(userRequest.getEmail());
        userUpdate.setPassword(userRequest.getPassword());
        userUpdate.setRole(userRequest.getRole());

        userRepository.save(userUpdate);
    }

    private UserModel mapToEntity(UserRequest userRequest) {
        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .role(userRequest.getRole())
                .build();
    }
}
