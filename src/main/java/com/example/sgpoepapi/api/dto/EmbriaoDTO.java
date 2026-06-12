package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Embriao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmbriaoDTO {
    private Long id;
    private Long racaId;
    private String grauDesenvolvimento;
    private String sexo;
    private Float precoEmbriao;
    private Long qualidadeId;

    public static EmbriaoDTO create(Embriao embriao) {
        EmbriaoDTO dto = new EmbriaoDTO();
        dto.setId(embriao.getId());
        if (embriao.getRaca() != null) dto.setRacaId(embriao.getRaca().getId());
        dto.setGrauDesenvolvimento(embriao.getGrauDesenvolvimento());
        dto.setSexo(embriao.getSexo());
        dto.setPrecoEmbriao(embriao.getPrecoEmbriao());
        if (embriao.getQualidade() != null) dto.setQualidadeId(embriao.getQualidade().getId());
        return dto;
    }
}
