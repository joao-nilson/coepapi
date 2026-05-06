package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Raca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacaDTO {
    private Long id;
    private String raca;
    private Float fracao;

    public static RacaDTO create(Raca raca) {
        RacaDTO dto = new RacaDTO();
        dto.setId(raca.getId());
        dto.setRaca(raca.getRaca());
        dto.setFracao(raca.getFracao());
        return dto;
    }
}
