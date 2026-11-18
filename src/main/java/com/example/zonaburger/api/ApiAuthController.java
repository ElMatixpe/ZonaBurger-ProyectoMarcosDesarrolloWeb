package com.example.zonaburger.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zonaburger.security.JwtService;
import com.example.zonaburger.security.UsuarioPrincipal;

import jakarta.validation.Valid;

/** POST /api/auth/login
 * Usuario y contraseña = Spring Security los valida = se genera el JWT */
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ApiAuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ApiDtos.LoginRequest datos) {
        try {
            // Spring Security compara la contraseña con el hash BCrypt guardado
            Authentication autenticacion = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contrasena()));

            UsuarioPrincipal usuario = (UsuarioPrincipal) autenticacion.getPrincipal();
            String token = jwtService.generarToken(usuario);

            return ResponseEntity.ok(new ApiDtos.LoginResponse(token, "Bearer",
                    usuario.getUsername(), usuario.getRol(), jwtService.getExpiracionSegundos()));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario o contraseña incorrectos."));
        }
    }
}
