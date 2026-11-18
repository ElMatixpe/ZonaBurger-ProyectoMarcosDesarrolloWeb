package com.example.zonaburger.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zonaburger.model.ItemCarrito;
import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.security.UsuarioPrincipal;
import com.example.zonaburger.service.PedidoService;
import com.example.zonaburger.service.ProductoService;
import com.example.zonaburger.service.UsuarioService;

import jakarta.validation.Valid;

/** GET  /api/pedidos : un ADMINISTRADOR ve todos; un CLIENTE solo los suyos.
 * POST /api/pedidos : crea un pedido para el usuario del token */
@RestController
@RequestMapping("/api/pedidos")
public class ApiPedidoController {

    private final PedidoService pedidoService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public ApiPedidoController(PedidoService pedidoService,
            ProductoService productoService,
            UsuarioService usuarioService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<ApiDtos.PedidoDto> listar(@AuthenticationPrincipal UsuarioPrincipal principal) {

        List<Pedido> pedidos = "ADMINISTRADOR".equals(principal.getRol())
                ? pedidoService.listarPedidos()
                : pedidoService.listarPedidosDeUsuario(principal.getId());

        return pedidos.stream().map(ApiDtos.PedidoDto::de).toList();
    }

    @PostMapping
    public ResponseEntity<ApiDtos.PedidoDto> crear(
            @Valid @RequestBody ApiDtos.PedidoRequest datos,
            @AuthenticationPrincipal UsuarioPrincipal principal) {

        Usuario cliente = usuarioService.buscarPorId(principal.getId());
        if (cliente == null) {
            throw new IllegalStateException("Tu cuenta ya no existe.");
        }

        List<ItemCarrito> items = new ArrayList<>();
        for (ApiDtos.ItemRequest item : datos.items()) {
            Producto producto = productoService.buscarActivoPorId(item.productoId());
            if (producto == null) {
                throw new IllegalStateException("El producto con id " + item.productoId()
                        + " no existe o no está disponible.");
            }
            items.add(new ItemCarrito(producto, item.cantidad()));
        }

        // los precios se toman de la base de datos
        Pedido pedido = pedidoService.crearPedido(cliente, items);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiDtos.PedidoDto.de(pedido));
    }
}
