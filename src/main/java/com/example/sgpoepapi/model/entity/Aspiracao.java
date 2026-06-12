package com.example.sgpoepapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aspiracao extends Etapa {

    @ToString.Exclude
    @ManyToMany
    private List<MatrizDoadora> doadoras;

    @ToString.Exclude
    @OneToMany
    private List<Oocito> oocitos;

    @ManyToOne
    private Cliente proprietarioCliente;
    @ManyToOne
    private PrestadoraServico proprietarioPrestadora;

    @ToString.Exclude
    @ManyToMany
    private List<Funcionario> tecnicosOPU;

    @ToString.Exclude
    @ManyToMany
    private List<Meio> meios;

    private Float tempChupada;
}
