package com.example.zonaburger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.zonaburger.model.ItemCarrito;
import com.example.zonaburger.model.Pedido;

@Service
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    public PedidoService(ProductoService productoService) {

        List<ItemCarrito> itemsPedido1 = new ArrayList<>();
        itemsPedido1.add(new ItemCarrito(productoService.buscarPorId(4), 1));
        itemsPedido1.add(new ItemCarrito(productoService.buscarPorId(10), 1));
        pedidos.add(new Pedido(1, "Juan Pérez", "Pendiente", itemsPedido1));

        List<ItemCarrito> itemsPedido2 = new ArrayList<>();
        itemsPedido2.add(new ItemCarrito(productoService.buscarPorId(5), 1));
        itemsPedido2.add(new ItemCarrito(productoService.buscarPorId(1), 1));
        pedidos.add(new Pedido(2, "María López", "En preparación", itemsPedido2));

        List<ItemCarrito> itemsPedido3 = new ArrayList<>();
        itemsPedido3.add(new ItemCarrito(productoService.buscarPorId(6), 1));
        pedidos.add(new Pedido(3, "Carlos Díaz", "Entregado", itemsPedido3));

        List<ItemCarrito> itemsPedido4 = new ArrayList<>();
        itemsPedido4.add(new ItemCarrito(productoService.buscarPorId(9), 1));
        pedidos.add(new Pedido(4, "Ana Torres", "Cancelado", itemsPedido4));
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }

    public Pedido buscarPorId(int id) {

        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public double calcularVentasTotales() {

        double total = 0;

        for (Pedido p : pedidos) {
            total += p.getTotal();
        }

        return total;
    }
}
