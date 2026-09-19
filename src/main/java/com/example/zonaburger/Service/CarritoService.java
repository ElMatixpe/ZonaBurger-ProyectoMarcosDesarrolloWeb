package com.example.zonaburger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.zonaburger.model.ItemCarrito;
import com.example.zonaburger.model.Producto;

@Service
public class CarritoService {
    private final List<ItemCarrito> carrito = new ArrayList<>();
    public void agregarProducto(Producto producto) {
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }
        }
        carrito.add(new ItemCarrito(producto, 1));
    }

    public List<ItemCarrito> listarCarrito() {
        return carrito;
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : carrito) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void eliminarProducto(int id) {
        carrito.removeIf(
                item -> item.getProducto().getId() == id
        );
    }

    public void aumentarCantidad(int id) {
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == id) {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }
        }
    }

    public void disminuirCantidad(int id) {
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == id) {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - 1);
                } else {
                    eliminarProducto(id);
                }
                return;
            }
        }
    }

    public void vaciarCarrito() {
        carrito.clear();
    }
}
