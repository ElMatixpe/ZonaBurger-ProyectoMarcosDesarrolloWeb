package com.example.zonaburger.api;

import java.util.List;

import com.example.zonaburger.model.DetallePedido;
import com.example.zonaburger.model.Pedido;
import com.example.zonaburger.model.Producto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/* Objetos que viajan en el JSON de la API. Se usan en lugar de las entidades
 * para no exponer datos internos (por ejemplo, la contraseña del usuario) */
public final class ApiDtos {

        private ApiDtos() {

        }

        // Login
        public record LoginRequest(
                        @NotBlank(message = "El usuario es obligatorio.") String usuario,
                        @NotBlank(message = "La contraseña es obligatoria.") String contrasena) {
        }

        public record LoginResponse(String token, String tipo, String usuario, String rol,
                        long expiraEnSegundos) {
        }

        // Productos 
        public record ProductoDto(int id, String nombre, String descripcion, double precio,
                        String imagen, String categoria) {

                public static ProductoDto de(Producto p) {
                        return new ProductoDto(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(),
                                        p.getImagen(), p.getCategoria().getNombre());
                }
        }

        // Pedidos: lo que envía el cliente 
        public record ItemRequest(
                        @NotNull(message = "El productoId es obligatorio.") Integer productoId,
                        @NotNull(message = "La cantidad es obligatoria.") @Min(value = 1, message = "La cantidad mínima es 1.") @Max(value = 100, message = "La cantidad máxima por producto es 100.") Integer cantidad) {
        }

        public record PedidoRequest(
                        @NotEmpty(message = "El pedido debe tener al menos un producto.") @Valid List<ItemRequest> items) {
        }

        // Pedidos: lo que devuelve la API 
        public record ItemDto(int productoId, String producto, int cantidad, double precioUnitario,
                        double subtotal) {

                public static ItemDto de(DetallePedido d) {
                        return new ItemDto(d.getProducto().getId(), d.getProducto().getNombre(),
                                        d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal());
                }
        }

        public record PedidoDto(int id, String cliente, String estado, String fecha, double total,
                        List<ItemDto> items) {

                public static PedidoDto de(Pedido p) {
                        return new PedidoDto(p.getId(), p.getCliente(), p.getEstado(),
                                        p.getFecha().toString(), p.getTotal(),
                                        p.getItems().stream().map(ItemDto::de).toList());
                }
        }
}
