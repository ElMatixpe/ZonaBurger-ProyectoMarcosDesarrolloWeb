package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.zonaburger.model.Producto;
import com.example.zonaburger.service.CarritoService;
import com.example.zonaburger.service.ProductoService;

@Controller
public class CarritoController {
    private final CarritoService carritoService;
    private final ProductoService productoService;

    public CarritoController(
            CarritoService carritoService,
            ProductoService productoService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
    }

    @GetMapping("/carrito")
    public String carrito(Model model) {
        model.addAttribute(
                "carrito",
                carritoService.listarCarrito());
        model.addAttribute(
                "total",
                carritoService.calcularTotal());
        return "paginas-internas/pg_carrito";
    }

    @GetMapping("/carrito/agregar/{id}")
    public String agregarProducto(@PathVariable int id) {
        Producto producto = productoService.buscarPorId(id);
        if (producto != null) {
            carritoService.agregarProducto(producto);
        }
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/aumentar/{id}")
    public String aumentarCantidad(@PathVariable int id) {
        carritoService.aumentarCantidad(id);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/disminuir/{id}")
    public String disminuirCantidad(@PathVariable int id) {
        carritoService.disminuirCantidad(id);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id) {
        carritoService.eliminarProducto(id);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/vaciar")
    public String vaciarCarrito() {
        carritoService.vaciarCarrito();
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/confirmar")
    public String confirmarPedido() {
        carritoService.vaciarCarrito();
        return "redirect:/";
    }
}
