package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class VistaAdminController {
    @GetMapping("/vista-admin")
    public String vistaAdmin() {
        return "paginas-internas/pg_vista_admin";
    }
}
