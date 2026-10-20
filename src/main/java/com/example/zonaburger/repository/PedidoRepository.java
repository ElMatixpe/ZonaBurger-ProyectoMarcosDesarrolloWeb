package com.example.zonaburger.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.zonaburger.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Trae de una vez el cliente y los productos, para no hacer una consulta por cada pedido
    @EntityGraph(attributePaths = {"usuario", "items", "items.producto"})
    List<Pedido> findAllByOrderByIdDesc();

    @Query("select coalesce(sum(d.cantidad * d.precioUnitario), 0.0) from DetallePedido d")
    Double sumarVentas();
}
