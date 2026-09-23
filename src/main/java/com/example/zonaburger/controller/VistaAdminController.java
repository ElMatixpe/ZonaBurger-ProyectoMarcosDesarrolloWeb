package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.service.PedidoService;
import com.example.zonaburger.service.ProductoService;
import com.example.zonaburger.service.UsuarioService;

import java.util.List;

@Controller
public class VistaAdminController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    public VistaAdminController(ProductoService productoService,
                                 UsuarioService usuarioService,
                                 PedidoService pedidoService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/vista-admin")
    public String vistaAdmin(Model model) {

        // PRODUCTOS (desde ProductoService)
        List<Producto> productos = productoService.listarProductos();

        // CLIENTES Y ADMINISTRADORES (desde UsuarioService)
        List<Usuario> clientes = usuarioService.listarClientes();
        List<Usuario> administradores = usuarioService.listarAdministradores();

        // PEDIDOS (desde PedidoService)
        List<Pedido> pedidos = pedidoService.listarPedidos();
        double ventasTotales = pedidoService.calcularVentasTotales();

        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);
        model.addAttribute("administradores", administradores);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("ventasTotales", ventasTotales);

        return "paginas-internas/pg_vista_admin";
    }
}
