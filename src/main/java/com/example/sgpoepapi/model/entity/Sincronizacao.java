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
public class Sincronizacao extends Etapa {

    @ToString.Exclude
    @ManyToMany
    private List<MatrizReceptora> receptoras;

    @ManyToOne
    private Cliente proprietarioCliente;
    @ManyToOne
    private PrestadoraServico proprietarioPrestadora;

    @ToString.Exclude
    @ManyToMany
    private List<MatrizReceptora> receptorasSincronizadas;
}
