package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Embriao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Raca raca;
    private String grauDesenvolvimento;
    private String sexo;
    private Float precoEmbriao;

    @ManyToOne
    private QualidadeEmbriao qualidade;
}
