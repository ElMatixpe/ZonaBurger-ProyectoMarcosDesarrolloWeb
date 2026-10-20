package com.example.zonaburger.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.example.zonaburger.form.EditarUsuarioForm;
import com.example.zonaburger.form.ProductoForm;
import com.example.zonaburger.form.RegistroForm;
import com.example.zonaburger.util.ErroresFormulario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

        // PRODUCTOS 
        List<Producto> productos = productoService.listarProductos();

        // CLIENTES Y ADMINISTRADORES 
        List<Usuario> clientes = usuarioService.listarClientes();
        List<Usuario> administradores = usuarioService.listarAdministradores();

        // PEDIDOS 
        List<Pedido> pedidos = pedidoService.listarPedidos();
        double ventasTotales = pedidoService.calcularVentasTotales();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("clientes", clientes);
        model.addAttribute("administradores", administradores);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("ventasTotales", ventasTotales);
        return "paginas-internas/pg_vista_admin";
    }

    @PostMapping("/vista-admin/administradores/agregar")
    public String agregarAdministrador(
            @Valid RegistroForm form,
            BindingResult resultado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Solo un administrador con sesion activa puede crear otro administrador
        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (resultado.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdmin", ErroresFormulario.primerMensaje(
                    resultado, "nombreCompleto", "correo", "usuario", "contrasena"));
            return "redirect:/vista-admin#seccionAdministradores";
        }

        String nombreLimpio = form.nombreCompleto().trim();
        String correoLimpio = form.correo().trim();
        String usuarioLimpio = form.usuario().trim();

        if (usuarioService.existeUsuario(usuarioLimpio)) {
            redirectAttributes.addFlashAttribute("errorAdmin", "El usuario ya está registrado.");
            return "redirect:/vista-admin#seccionAdministradores";
        }

        if (usuarioService.existeCorreo(correoLimpio)) {
            redirectAttributes.addFlashAttribute("errorAdmin", "El correo electrónico ya está registrado.");
            return "redirect:/vista-admin#seccionAdministradores";
        }

        usuarioService.registrarAdministrador(nombreLimpio, correoLimpio, usuarioLimpio, form.contrasena());
        redirectAttributes.addFlashAttribute("mensajeAdmin",
                "Administrador \"" + usuarioLimpio + "\" agregado correctamente.");

        return "redirect:/vista-admin#seccionAdministradores";
    }

    // Utilidades
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
            @Valid ProductoForm form,
            BindingResult resultado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (resultado.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdmin", ErroresFormulario.primerMensaje(
                    resultado, "categoriaId", "nombre", "descripcion", "precio", "imagen"));
            return "redirect:/vista-admin#seccionProductos";
        }

        String nombre = form.nombre().trim();

        String error = productoService.agregarProducto(
                form.categoriaId(), nombre, form.precio().doubleValue(),
                form.imagen().trim(), form.descripcion().trim());

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorAdmin", error);
        } else {
            redirectAttributes.addFlashAttribute("mensajeAdmin",
                    "Producto \"" + nombre + "\" agregado correctamente.");
        }
        return "redirect:/vista-admin#seccionProductos";
    }

    @PostMapping("/vista-admin/productos/{id}/editar")
    public String editarProducto(
            @PathVariable int id,
            @Valid ProductoForm form,
            BindingResult resultado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        if (resultado.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdmin", ErroresFormulario.primerMensaje(
                    resultado, "categoriaId", "nombre", "descripcion", "precio", "imagen"));
            return "redirect:/vista-admin#seccionProductos";
        }

        String error = productoService.actualizarProducto(
                id, form.categoriaId(), form.nombre().trim(), form.precio().doubleValue(),
                form.imagen().trim(), form.descripcion().trim());

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorAdmin", error);
        } else {
            redirectAttributes.addFlashAttribute("mensajeAdmin", "Producto actualizado correctamente.");
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

    // PEDIDOS: SOLO CAMBIA EL ESTADO
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
                    "Estado del pedido " + String.format("PE%03d", id) + " actualizado a \"" + estado + "\".");
        } else {
            redirectAttributes.addFlashAttribute("errorAdmin", "No se pudo cambiar el estado del pedido.");
        }
        return "redirect:/vista-admin#seccionPedidos";
    }

    // CLIENTES Y ADMINISTRADORES 
    @PostMapping("/vista-admin/usuarios/{id}/editar")
    public String editarUsuario(
            @PathVariable int id,
            @Valid EditarUsuarioForm form,
            BindingResult resultado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!esAdministrador(session)) {
            return "redirect:/login";
        }

        Usuario objetivo = usuarioService.buscarPorId(id);
        String seccion = seccionDe(objetivo);

        if (resultado.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdmin", ErroresFormulario.primerMensaje(
                    resultado, "nombreCompleto", "correo", "usuario", "contrasena"));
            return "redirect:/vista-admin" + seccion;
        }

        String error = usuarioService.actualizarUsuario(
                id, form.nombreCompleto(), form.correo(), form.usuario(), form.contrasena());

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorAdmin", error);
            return "redirect:/vista-admin" + seccion;
        }

        // Si el administrador se edito a si mismo, se refresca la sesion
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

        try {
            usuarioService.eliminarUsuario(id);
        } catch (DataIntegrityViolationException e) {
            // Tiene pedidos u otros registros asociados en la base de datos
            redirectAttributes.addFlashAttribute("errorAdmin",
                    "No se puede eliminar a \"" + objetivo.getUsuario()
                            + "\" porque tiene pedidos o registros asociados.");
            return "redirect:/vista-admin" + seccion;
        }

        redirectAttributes.addFlashAttribute("mensajeAdmin",
                "Usuario \"" + objetivo.getUsuario() + "\" eliminado correctamente.");
        return "redirect:/vista-admin" + seccion;
    }
}