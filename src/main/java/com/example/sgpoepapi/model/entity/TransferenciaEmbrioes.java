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
public class TransferenciaEmbrioes extends Etapa {

    @ToString.Exclude
    @ManyToMany
    private List<Embriao> embrioes;

    @ManyToOne
    private Cliente tecnicoCliente;
    @ManyToOne
    private PrestadoraServico tecnicoPrestadora;

    private String horaEntregaEmb;
}
