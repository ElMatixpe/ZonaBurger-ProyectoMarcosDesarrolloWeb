package com.example.zonaburger.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.zonaburger.model.Categoria;
import com.example.zonaburger.model.Producto;
import com.example.zonaburger.repository.CategoriaRepository;
import com.example.zonaburger.repository.ProductoRepository;

@Service
public class ProductoService {

        private final ProductoRepository productoRepository;
        private final CategoriaRepository categoriaRepository;

        public ProductoService(ProductoRepository productoRepository,
                        CategoriaRepository categoriaRepository) {
                this.productoRepository = productoRepository;
                this.categoriaRepository = categoriaRepository;
        }

        /*Solo los productos activos */
        public List<Producto> listarProductos() {
                return productoRepository.findByActivoTrueOrderByIdAsc();
        }

        /* Solo los productos eliminados */
        public List<Producto> listarProductosInactivos() {
                return productoRepository.findByActivoFalseOrderByIdAsc();
        }

        public List<Categoria> listarCategorias() {
                return categoriaRepository.findAllByOrderByIdAsc();
        }

        public Producto buscarPorId(int id) {
                return productoRepository.findById(id).orElse(null);
        }

        /* Busca solo productos activos */
        public Producto buscarActivoPorId(int id) {
                return productoRepository.findByIdAndActivoTrue(id).orElse(null);
        }

        public String agregarProducto(int categoriaId, String nombre, double precio,
                        String imagen, String descripcion) {

                Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
                if (categoria == null) {
                        return "La categoría seleccionada no existe.";
                }

                Optional<Producto> existente = productoRepository.findByNombreIgnoreCase(nombre);

                if (existente.isPresent()) {
                        Producto p = existente.get();

                        if (p.isActivo()) {
                                return "Ya existe un producto con ese nombre.";
                        }

                        p.setCategoria(categoria);
                        p.setNombre(nombre);
                        p.setPrecio(precio);
                        p.setImagen(imagen);
                        p.setDescripcion(descripcion);
                        p.setActivo(true);
                        productoRepository.save(p);
                        return null;
                }

                productoRepository.save(new Producto(categoria, nombre, precio, imagen, descripcion));
                return null;
        }

        public String actualizarProducto(int id, int categoriaId, String nombre, double precio,
                        String imagen, String descripcion) {

                Producto producto = productoRepository.findByIdAndActivoTrue(id).orElse(null);
                if (producto == null) {
                        return "El producto no existe.";
                }

                Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
                if (categoria == null) {
                        return "La categoría seleccionada no existe.";
                }

                Optional<Producto> conMismoNombre = productoRepository.findByNombreIgnoreCase(nombre);
                if (conMismoNombre.isPresent() && conMismoNombre.get().getId() != id) {
                        return "Ya existe un producto con ese nombre.";
                }

                producto.setCategoria(categoria);
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setImagen(imagen);
                producto.setDescripcion(descripcion);
                productoRepository.save(producto);
                return null;
        }

        public boolean eliminarProducto(int id) {
                Producto producto = productoRepository.findByIdAndActivoTrue(id).orElse(null);
                if (producto == null) {
                        return false;
                }
                producto.setActivo(false);
                productoRepository.save(producto);
                return true;
        }

        /* Vuelve a activar un producto eliminado */
        public boolean reactivarProducto(int id) {
                Producto producto = productoRepository.findById(id).orElse(null);
                if (producto == null || producto.isActivo()) {
                        return false;
                }
                producto.setActivo(true);
                productoRepository.save(producto);
                return true;
        }
}