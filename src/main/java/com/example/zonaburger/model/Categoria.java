package com.example.zonaburger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    protected Categoria() {
    }

    public int getId() {
        return id == null ? 0 : id;
    }

    public String getNombre() {
        return nombre;
    }
}