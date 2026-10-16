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

    public Pedido crearPedido(String cliente, List<ItemCarrito> itemsCarrito) {

        // Se copian los items: el carrito se vacía después y no debe afectar al pedido
        List<ItemCarrito> itemsPedido = new ArrayList<>();

        for (ItemCarrito item : itemsCarrito) {
            itemsPedido.add(new ItemCarrito(item.getProducto(), item.getCantidad()));
        }

        int nuevoId = 1;

        for (Pedido p : pedidos) {
            if (p.getId() >= nuevoId) {
                nuevoId = p.getId() + 1;
            }
        }

        Pedido pedido = new Pedido(nuevoId, cliente, "Pendiente", itemsPedido);
        pedidos.add(pedido);

        return pedido;
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

    public static final List<String> ESTADOS = List.of("Pendiente", "En preparación", "Entregado", "Cancelado");

    public boolean cambiarEstado(int id, String estado) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null || !ESTADOS.contains(estado)) {
            return false;
        }
        pedido.setEstado(estado);
        return true;
    }
}