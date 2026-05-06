package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.QualidadeEmbriao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualidadeEmbriaoDTO {
    private Long id;
    private String qualidade;

    public static QualidadeEmbriaoDTO create(QualidadeEmbriao qualidade) {
        QualidadeEmbriaoDTO dto = new QualidadeEmbriaoDTO();
        dto.setId(qualidade.getId());
        dto.setQualidade(qualidade.getQualidade());
        return dto;
    }
}
