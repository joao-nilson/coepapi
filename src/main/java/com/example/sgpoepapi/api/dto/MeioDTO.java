package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Meio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeioDTO {
    private Long id;
    private String tipoDeMeio;
    private String descricao;
    private Float precoDeMeio;
    private Float quantidade;

    public static MeioDTO create(Meio meio) {
        MeioDTO dto = new MeioDTO();
        dto.setId(meio.getId());
        dto.setTipoDeMeio(meio.getTipoDeMeio());
        dto.setDescricao(meio.getDescricao());
        dto.setPrecoDeMeio(meio.getPrecoDeMeio());
        dto.setQuantidade(meio.getQuantidade());
        return dto;
    }
}
