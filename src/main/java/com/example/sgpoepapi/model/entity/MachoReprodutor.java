package com.example.sgpoepapi.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MachoReprodutor extends Animal{
    private Boolean semenSexado;
    private Float precoDose;
}
