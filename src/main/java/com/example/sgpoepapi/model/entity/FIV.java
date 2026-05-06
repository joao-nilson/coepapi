package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FIV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nOocitosFecundados;
    private Integer nEmbrioes;
    private LocalDateTime dataFIV;
    private Float precoFIV;

    @ManyToOne
    private MatrizDoadora mae;
    @ManyToOne
    private MachoReprodutor pai;

    @ToString.Exclude
    @OneToMany
    private List<Embriao> embrioes;
}
