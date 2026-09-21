package com.example.zonaburger.model;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private String imagen;
    private String descripcion;

    public Producto(int id, String nombre, double precio, String imagen, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
