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
    private Cliente cliente;
    @ManyToOne
    private PrestadoraServico prestadora;
    private String hora_inicio;
    private String hora_termino;
    private String num_embrioes;
    private String num_prenhezes;
    private String raca_embrioes;
}
