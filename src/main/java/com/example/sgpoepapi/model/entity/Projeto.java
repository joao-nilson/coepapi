package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PrestadoraServico prestadora;
    @ManyToOne
    private Cliente cliente;

    private LocalDateTime dataInicioProjeto;
    private LocalDateTime dataTerminoProjeto;
    private Integer nAspiracoesProjeto;
    private Integer nPremiacoesProjeto;
    private String racaEmbrioes;
}
