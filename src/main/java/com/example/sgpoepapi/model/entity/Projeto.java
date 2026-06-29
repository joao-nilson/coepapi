package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private PrestadoraServico prestadora;
    private String dataInicioProjeto;
    private String dataTerminoProjeto;
    private String nAspiracoesProjeto;
    private String nPremiacoesProjeto;
    private String racaEmbrioes;
}
