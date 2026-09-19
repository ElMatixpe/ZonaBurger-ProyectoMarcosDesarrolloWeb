package com.example.zonaburger.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.zonaburger.model.Producto;

@Service
public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    public ProductoService() {
        productos.add(new Producto(
                1,
                "Combo Clásico",
                19.90,
                "promo1.png",
                "Burger Clásica, papas fritas y bebida personal."
        ));

        productos.add(new Producto(
                2,
                "Combo BBQ",
                22.90,
                "promo2.png",
                "Burger BBQ, papas fritas y bebida personal."
        ));

        productos.add(new Producto(
                3,
                "Combo Doble",
                25.90,
                "promo3.png",
                "Burger Doble, papas fritas y bebida personal."
        ));

        productos.add(new Producto(
                4,
                "Burger Clásica",
                15.90,
                "clasica.png",
                "Carne, queso, lechuga, tomate y papas fritas."
        ));

        productos.add(new Producto(
                5,
                "Burger BBQ",
                18.90,
                "bbq.png",
                "Carne, queso, tocino, cebolla y deliciosa salsa BBQ."
        ));

        productos.add(new Producto(
                6,
                "Burger Doble",
                21.90,
                "doble.png",
                "Doble carne, doble queso y papas fritas."
        ));

        productos.add(new Producto(
                7,
                "Dúo Clásico",
                34.90,
                "duo1.png",
                "2 Burger Clásicas, 2 porciones de papas fritas y 2 bebidas personales."
        ));

        productos.add(new Producto(
                8,
                "Dúo BBQ",
                39.90,
                "duo2.png",
                "2 Burger BBQ, 2 porciones de papas fritas y 2 bebidas personales."
        ));

        productos.add(new Producto(
                9,
                "Dúo Mixto",
                42.90,
                "duo3.png",
                "1 Burger Clásica, 1 Burger Doble, 2 porciones de papas fritas y 2 bebidas."
        ));

        productos.add(new Producto(
                10,
                "Salchipapa Especial",
                16.90,
                "salchipapa1.png",
                "Papas fritas, salchicha, huevo frito, salsas y bebida personal."
        ));

        productos.add(new Producto(
                11,
                "Salchipapa Full",
                22.90,
                "salchipapa2.png",
                "Papas fritas, salchicha, tocino, chorizo, huevo frito, salsas y bebida personal."
        ));
    }

    public List<Producto> listarProductos() {
        return productos;
    }
    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
