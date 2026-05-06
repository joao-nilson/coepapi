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
public class MatrizDoadora extends Animal {
    private String apelido;
    private LocalDateTime dataNascimento;
    private Integer numOocitos;
    private Boolean viavel;
    private Float precoOocito;
}
