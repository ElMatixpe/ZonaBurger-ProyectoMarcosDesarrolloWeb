package com.example.zonaburger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zonaburger.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByOrderByIdAsc();
}