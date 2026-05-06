package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {
    private Long id;
    private String nome;
    private String RGD;
    private Long racaId;
    private Long proprietarioId;

    public static AnimalDTO create(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setNome(animal.getNome());
        dto.setRGD(animal.getRGD());
        if (animal.getRaca() != null) dto.setRacaId(animal.getRaca().getId());
        if (animal.getProprietario() != null) dto.setProprietarioId(animal.getProprietario().getId());
        return dto;
    }
}
