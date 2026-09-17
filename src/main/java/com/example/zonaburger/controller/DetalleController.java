package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller 
public class DetalleController {
    @GetMapping("/detalle")
    public String detalle() {
        return "paginas-internas/pg_detalle";
    }
}
