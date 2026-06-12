package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Descongelamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Embriao embriao;
    @ManyToOne
    private MetodoCongelamento metodo;

    private LocalDateTime dataDescong;
    private Float precoDescongelamento;
}
