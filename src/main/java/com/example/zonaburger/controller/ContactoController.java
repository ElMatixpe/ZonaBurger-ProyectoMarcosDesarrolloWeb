package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class ContactoController {
    @GetMapping("/contacto")
    public String contacto() {
        return "paginas-internas/pg_contacto";
    }
}
