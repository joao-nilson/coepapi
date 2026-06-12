package com.example.sgpoepapi.model.repository;

import com.example.sgpoepapi.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepositorio extends JpaRepository<Animal, Long> {
}
