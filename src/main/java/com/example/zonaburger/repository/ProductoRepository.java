package com.example.zonaburger.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zonaburger.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrueOrderByIdAsc();

    List<Producto> findByActivoFalseOrderByIdAsc();

    Optional<Producto> findByIdAndActivoTrue(Integer id);

    Optional<Producto> findByNombreIgnoreCase(String nombre);
}