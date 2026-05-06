package com.example.sgpoepapi.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrizReceptora extends Animal {
    private Integer numero;
    private Boolean prenha;
    private LocalDateTime dataNascimento;
    private Integer nOocitos;
}
