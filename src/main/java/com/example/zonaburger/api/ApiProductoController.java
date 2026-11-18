package com.example.zonaburger.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zonaburger.service.ProductoService;

/* GET /api/productos: productos activos. Requiere un token valido */
@RestController
@RequestMapping("/api/productos")
public class ApiProductoController {

    private final ProductoService productoService;

    public ApiProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ApiDtos.ProductoDto> listar() {
        return productoService.listarProductos().stream()
                .map(ApiDtos.ProductoDto::de)
                .toList();
    }
}
