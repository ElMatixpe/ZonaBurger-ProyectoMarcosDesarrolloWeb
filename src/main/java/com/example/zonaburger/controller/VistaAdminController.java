package com.example.zonaburger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.service.PedidoService;
import com.example.zonaburger.service.ProductoService;
import com.example.zonaburger.service.UsuarioService;
import com.example.zonaburger.util.ValidadorContrasena;

import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/vista-admin/administradores/agregar")
    public String agregarAdministrador(
            @RequestParam("nombreCompleto") String nombreCompleto,
            @RequestParam("correo") String correo,
            @RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Solo un administrador con sesión activa puede crear otro administrador
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        String nombreLimpio = nombreCompleto.trim();
        String correoLimpio = correo.trim();
        String usuarioLimpio = usuario.trim();

        if (nombreLimpio.isEmpty() || correoLimpio.isEmpty()
                || usuarioLimpio.isEmpty() || contrasena.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorAdmin", "Todos los campos son obligatorios.");
            return "redirect:/vista-admin#seccionAdministradores";
        }

        if (!ValidadorContrasena.esValida(contrasena)) {
            redirectAttributes.addFlashAttribute("errorAdmin", ValidadorContrasena.MENSAJE);
            return "redirect:/vista-admin#seccionAdministradores";
        }

        if (usuarioService.existeUsuario(usuarioLimpio)) {
            redirectAttributes.addFlashAttribute("errorAdmin", "El usuario ya está registrado.");
            return "redirect:/vista-admin#seccionAdministradores";
        }

        if (usuarioService.existeCorreo(correoLimpio)) {
            redirectAttributes.addFlashAttribute("errorAdmin", "El correo electrónico ya está registrado.");
            return "redirect:/vista-admin#seccionAdministradores";
        }

        usuarioService.registrarAdministrador(nombreLimpio, correoLimpio, usuarioLimpio, contrasena);
        redirectAttributes.addFlashAttribute("mensajeAdmin",
                "Administrador \"" + usuarioLimpio + "\" agregado correctamente.");

        return "redirect:/vista-admin#seccionAdministradores";
    }

    private boolean esAdministrador(HttpSession session) {
        Usuario activo = (Usuario) session.getAttribute("usuarioActivo");
        return activo != null && "ADMINISTRADOR".equals(activo.getRol());
    }

    private String seccionDe(Usuario u) {
        return u != null && "ADMINISTRADOR".equals(u.getRol())
                ? "#seccionAdministradores"
                : "#seccionClientes";
    }

    // PRODUCTOS
    @PostMapping("/vista-admin/productos/agregar")
    public String agregarProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("imagen") String imagen,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (nombre.isBlank() || descripcion.isBlank() || imagen.isBlank() || precio <= 0) {
            redirectAttributes.addFlashAttribute("errorAdmin",
                    "Completa todos los campos del producto y usa un precio mayor a 0.");
            return "redirect:/vista-admin#seccionProductos";
        }

        productoService.agregarProducto(nombre.trim(), precio, imagen.trim(), descripcion.trim());
        redirectAttributes.addFlashAttribute("mensajeAdmin",
                "Producto \"" + nombre.trim() + "\" agregado correctamente.");
        return "redirect:/vista-admin#seccionProductos";
    }

    @PostMapping("/vista-admin/productos/{id}/editar")
    public String editarProducto(
            @PathVariable int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam("imagen") String imagen,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (nombre.isBlank() || descripcion.isBlank() || imagen.isBlank() || precio <= 0) {
            redirectAttributes.addFlashAttribute("errorAdmin",
                    "Completa todos los campos del producto y usa un precio mayor a 0.");
            return "redirect:/vista-admin#seccionProductos";
        }

        if (productoService.actualizarProducto(id, nombre.trim(), precio, imagen.trim(), descripcion.trim())) {
            redirectAttributes.addFlashAttribute("mensajeAdmin", "Producto actualizado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorAdmin", "El producto no existe.");
        }
        return "redirect:/vista-admin#seccionProductos";
    }

    @PostMapping("/vista-admin/productos/{id}/eliminar")
    public String eliminarProducto(
            @PathVariable int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (productoService.eliminarProducto(id)) {
            redirectAttributes.addFlashAttribute("mensajeAdmin", "Producto eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorAdmin", "El producto no existe.");
        }
        return "redirect:/vista-admin#seccionProductos";
    }

    // PEDIDOS
    @PostMapping("/vista-admin/pedidos/{id}/estado")
    public String cambiarEstadoPedido(
            @PathVariable int id,
            @RequestParam("estado") String estado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (pedidoService.cambiarEstado(id, estado)) {
            redirectAttributes.addFlashAttribute("mensajeAdmin",
                    "Estado del pedido #00" + id + " actualizado a \"" + estado + "\".");
        } else {
            redirectAttributes.addFlashAttribute("errorAdmin", "No se pudo cambiar el estado del pedido.");
        }
        return "redirect:/vista-admin#seccionPedidos";
    }

    // CLIENTES Y ADMINISTRADORES
    @PostMapping("/vista-admin/usuarios/{id}/editar")
    public String editarUsuario(
            @PathVariable int id,
            @RequestParam("nombreCompleto") String nombreCompleto,
            @RequestParam("correo") String correo,
            @RequestParam("usuario") String usuario,
            @RequestParam(value = "contrasena", defaultValue = "") String contrasena,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        Usuario objetivo = usuarioService.buscarPorId(id);
        String seccion = seccionDe(objetivo);

        String error = usuarioService.actualizarUsuario(id, nombreCompleto, correo, usuario, contrasena);

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorAdmin", error);
            return "redirect:/vista-admin" + seccion;
        }

        // Si el administrador se editó a si mismo, se refresca la sesión
        Usuario activo = (Usuario) session.getAttribute("usuarioActivo");
        if (activo != null && activo.getId() != null && activo.getId() == id) {
            session.setAttribute("usuarioActivo", usuarioService.buscarPorId(id));
        }

        redirectAttributes.addFlashAttribute("mensajeAdmin", "Datos actualizados correctamente.");
        return "redirect:/vista-admin" + seccion;
    }

    @PostMapping("/vista-admin/usuarios/{id}/eliminar")
    public String eliminarUsuario(
            @PathVariable int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        Usuario objetivo = usuarioService.buscarPorId(id);
        String seccion = seccionDe(objetivo);

        if (objetivo == null) {
            redirectAttributes.addFlashAttribute("errorAdmin", "El usuario no existe.");
            return "redirect:/vista-admin" + seccion;
        }

        Usuario activo = (Usuario) session.getAttribute("usuarioActivo");
        if (activo != null && activo.getId() != null && activo.getId() == id) {
            redirectAttributes.addFlashAttribute("errorAdmin", "No puedes eliminar tu propia cuenta.");
            return "redirect:/vista-admin" + seccion;
        }

        usuarioService.eliminarUsuario(id);
        redirectAttributes.addFlashAttribute("mensajeAdmin",
                "Usuario \"" + objetivo.getUsuario() + "\" eliminado correctamente.");
        return "redirect:/vista-admin" + seccion;
    }
}