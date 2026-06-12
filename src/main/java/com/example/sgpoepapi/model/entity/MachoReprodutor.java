package com.example.sgpoepapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MachoReprodutor extends Animal{
    private Boolean semenSexado;
    private Float precoDose;

    @ToString.Exclude
    @OneToMany(mappedBy = "machoReprodutor")
    private List<DosesSemen> doses;
}
