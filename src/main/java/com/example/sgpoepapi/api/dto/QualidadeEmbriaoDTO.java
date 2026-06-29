package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.QualidadeEmbriao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualidadeEmbriaoDTO {
    private Long id;
    private String qualidade;

    public static QualidadeEmbriaoDTO create(QualidadeEmbriao qualidade) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(qualidade, QualidadeEmbriaoDTO.class);
    }
}
