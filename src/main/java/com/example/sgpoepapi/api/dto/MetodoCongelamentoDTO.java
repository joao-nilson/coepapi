package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoCongelamentoDTO {
    private Long id;
    private String nomeMetodo;
    private String descricao;

    public static MetodoCongelamentoDTO create(MetodoCongelamento metodo) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(metodo, MetodoCongelamentoDTO.class);
    }
}
