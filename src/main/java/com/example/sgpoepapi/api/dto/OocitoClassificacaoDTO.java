package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.OocitoClassificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OocitoClassificacaoDTO {
    private Long id;
    private String classificacao;
    private String descricaoClass;

    public static OocitoClassificacaoDTO create(OocitoClassificacao oocitoClassificacao) {
        OocitoClassificacaoDTO dto = new OocitoClassificacaoDTO();
        dto.setId(oocitoClassificacao.getId());
        dto.setClassificacao(oocitoClassificacao.getClassificacao());
        dto.setDescricaoClass(oocitoClassificacao.getDescricaoClass());
        return dto;
    }
}
