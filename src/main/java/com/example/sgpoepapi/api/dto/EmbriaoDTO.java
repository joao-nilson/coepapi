package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Embriao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(embriao, EmbriaoDTO.class);
    }
}
