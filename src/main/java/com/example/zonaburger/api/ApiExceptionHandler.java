package com.example.zonaburger.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* Convierte los errores de la API en respuestas JSON claras */
@RestControllerAdvice(basePackages = "com.example.zonaburger.api")
public class ApiExceptionHandler {

    /* Fallo alguna validacion del cuerpo (@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> datosInvalidos(MethodArgumentNotValidException e) {
        List<String> detalles = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", "Datos inválidos.");
        cuerpo.put("detalles", detalles);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    /* El cuerpo no es un JSON valido o falta. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> jsonInvalido(HttpMessageNotReadableException e) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", "El cuerpo de la petición no es un JSON válido.");
        return ResponseEntity.badRequest().body(cuerpo);
    }

    /* Reglas del negocio (producto no disponible, cuenta inexistente, etc.) */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> reglaDeNegocio(IllegalStateException e) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(cuerpo);
    }
}
