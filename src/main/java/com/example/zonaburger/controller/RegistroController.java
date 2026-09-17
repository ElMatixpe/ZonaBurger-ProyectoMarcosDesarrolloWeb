package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class RegistroController {
    @GetMapping("/registro")
    public String registro() {
        return "paginas-internas/pg_registro";
    }
}
