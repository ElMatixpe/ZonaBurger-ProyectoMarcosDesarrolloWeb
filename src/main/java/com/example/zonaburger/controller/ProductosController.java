package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.zonaburger.service.ProductoService;

@Controller
public class ProductosController {

    private final ProductoService productoService;

    public ProductosController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public String productos(Model model) {

        model.addAttribute(
                "productos",
                productoService.listarProductos()
        );

        return "paginas-internas/pg_productos";
    }
}