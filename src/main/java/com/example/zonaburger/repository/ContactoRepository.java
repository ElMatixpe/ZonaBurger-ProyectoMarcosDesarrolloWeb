package com.example.zonaburger.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zonaburger.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
}