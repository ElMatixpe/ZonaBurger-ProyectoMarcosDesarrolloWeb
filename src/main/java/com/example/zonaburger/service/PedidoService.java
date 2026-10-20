package com.example.zonaburger.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.zonaburger.model.ItemCarrito;
import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.model.Usuario;
import com.example.zonaburger.repository.PedidoRepository;

@Service
public class PedidoService {

    public static final List<String> ESTADOS = List.of("Pendiente", "En preparación", "Entregado", "Cancelado");
    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public PedidoService(PedidoRepository pedidoRepository,
            ProductoService productoService,
            UsuarioService usuarioService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    /** Del más reciente al más antiguo. */
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAllByOrderByIdDesc();
    }

    public Pedido buscarPorId(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    /**
     * Crea el pedido a partir del carrito. Los precios se toman de la base de datos*/
    @Transactional
    public Pedido crearPedido(Usuario usuarioSesion, List<ItemCarrito> itemsCarrito) {

        if (itemsCarrito == null || itemsCarrito.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        Usuario cliente = usuarioSesion.getId() == null ? null : usuarioService.buscarPorId(usuarioSesion.getId());
        if (cliente == null) {
            throw new IllegalStateException("Tu cuenta ya no existe. Inicia sesión nuevamente.");
        }

        Pedido pedido = new Pedido(cliente);

        for (ItemCarrito item : itemsCarrito) {
            Producto actual = productoService.buscarActivoPorId(item.getProducto().getId());

            if (actual == null) {
                throw new IllegalStateException("El producto \"" + item.getProducto().getNombre()
                        + "\" ya no está disponible. Quítalo del carrito para continuar.");
            }

            pedido.agregarItem(actual, item.getCantidad(), actual.getPrecio());
        }

        return pedidoRepository.save(pedido);
    }

    public double calcularVentasTotales() {
        Double total = pedidoRepository.sumarVentas();
        return total == null ? 0 : total;
    }

    @Transactional
    public boolean cambiarEstado(int id, String estado) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null || !ESTADOS.contains(estado)) {
            return false;
        }
        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
        return true;
    }
}