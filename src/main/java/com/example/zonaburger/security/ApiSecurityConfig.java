package com.example.zonaburger.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.zonaburger.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;

/** Cadena de seguridad exclusiva de la API REST (/api/**):
 * sin sesion, sin formulario de login y autenticada con JWT.
 * Tiene prioridad sobre la cadena web de SecurityConfig */
@Configuration
public class ApiSecurityConfig {

        /* Lo usa /api/auth/login para validar usuario y contraseña con Spring Security */
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracion)
                        throws Exception {
                return configuracion.getAuthenticationManager();
        }

        @Bean
        @Order(1)
        public SecurityFilterChain filtroApi(HttpSecurity http,
                        JwtService jwtService,
                        UsuarioRepository usuarioRepository) throws Exception {

                http
                                .securityMatcher("/api/**")

                                // El token viaja en la cabecera, por eso no hace falta CSRF
                                .csrf(csrf -> csrf.disable())

                                // Cada peticion se autentica sola con su token: no se crea sesion
                                .sessionManagement(sesion -> sesion
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(rutas -> rutas
                                                .requestMatchers("/api/auth/login").permitAll()
                                                .requestMatchers("/api/pedidos", "/api/pedidos/**")
                                                .hasAnyRole("CLIENTE", "ADMINISTRADOR")
                                                .anyRequest().authenticated())

                                .addFilterBefore(new JwtAuthenticationFilter(jwtService, usuarioRepository),
                                                UsernamePasswordAuthenticationFilter.class)

                                .exceptionHandling(errores -> errores
                                                .authenticationEntryPoint((peticion, respuesta,
                                                                excepcion) -> responderJson(respuesta, 401,
                                                                                "No autenticado: falta el token o es inválido o ya venció."))
                                                .accessDeniedHandler((peticion, respuesta, excepcion) -> responderJson(
                                                                respuesta, 403,
                                                                "No tienes permiso para acceder a este recurso.")));

                return http.build();
        }

        private static void responderJson(HttpServletResponse respuesta, int estado, String mensaje)
                        throws IOException {
                respuesta.setStatus(estado);
                respuesta.setContentType("application/json;charset=UTF-8");
                respuesta.getWriter().write("{\"error\":\"" + mensaje + "\"}");
        }
}
