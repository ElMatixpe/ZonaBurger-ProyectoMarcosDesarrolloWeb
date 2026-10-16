package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.service.CarritoService;
import com.example.zonaburger.service.PedidoService;
import com.example.zonaburger.service.ProductoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarritoController {
    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final PedidoService pedidoService;

    public CarritoController(
            CarritoService carritoService,
            ProductoService productoService,
            PedidoService pedidoService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.pedidoService = pedidoService;
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

    @PostMapping("/carrito/confirmar")
    public String confirmarPedido(HttpSession session, RedirectAttributes redirectAttributes) {

        // Para hacer un pedido hay que haber iniciado sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioActivo");
        if (usuario == null) {
            return "redirect:/login";
        }

        // Si el carrito está vacío no se crea ningún pedido
        if (carritoService.listarCarrito().isEmpty()) {
            return "redirect:/carrito";
        }

        String cliente = usuario.getNombreCompleto();
        if (cliente == null || cliente.isBlank()) {
            cliente = usuario.getUsuario();
        }

        Pedido pedido = pedidoService.crearPedido(cliente, carritoService.listarCarrito());
        carritoService.vaciarCarrito();

        redirectAttributes.addFlashAttribute("pedidoConfirmado", pedido.getId());
        return "redirect:/carrito";
    }
}