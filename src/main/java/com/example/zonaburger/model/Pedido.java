package com.example.zonaburger.model;

import java.util.List;

public class Pedido {

    private int id;
    private String cliente;
    private String estado;
    private List<ItemCarrito> items;

    public Pedido(int id, String cliente, String estado, List<ItemCarrito> items) {
        this.id = id;
        this.cliente = cliente;
        this.estado = estado;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public double getTotal() {

        double total = 0;

        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }

        return total;
    }
}
