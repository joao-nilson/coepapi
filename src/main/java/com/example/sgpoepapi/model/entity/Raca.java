package com.example.sgpoepapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Raca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String raca;
    private Float fracao;

    public Raca calculaRaca(Raca mae, Raca pai) {
        Raca racaFilho = null;
        //REALIZAR CALCULO DE MESTIÇAGEM DE RAÇA
        return racaFilho;
    }
}
