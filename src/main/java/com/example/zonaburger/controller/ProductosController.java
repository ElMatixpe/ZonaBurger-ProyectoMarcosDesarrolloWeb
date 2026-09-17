package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller 
public class ProductosController {
    @GetMapping("/productos")
    public String productos() {
        return "paginas-internas/pg_productos";
    }
}
