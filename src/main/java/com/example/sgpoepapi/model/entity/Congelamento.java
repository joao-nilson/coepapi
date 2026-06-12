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
public class Congelamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Embriao embriao;
    @ManyToOne
    private MetodoCongelamento metodo;

    private LocalDateTime dataCong;
    private Float precoCongelamento;
}
