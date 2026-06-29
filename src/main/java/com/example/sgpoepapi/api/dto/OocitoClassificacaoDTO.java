package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.OocitoClassificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OocitoClassificacaoDTO {
    private Long id;
    private String classificacao;
    private String descricaoClass;

    public static OocitoClassificacaoDTO create(OocitoClassificacao oocitoClassificacao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(oocitoClassificacao, OocitoClassificacaoDTO.class);
    }
}
