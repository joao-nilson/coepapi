package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MetodoCongelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoCongelamentoDTO {
    private Long id;
    private String nomeMetodo;
    private String descricao;

    public static MetodoCongelamentoDTO create(MetodoCongelamento metodo) {
        MetodoCongelamentoDTO dto = new MetodoCongelamentoDTO();
        dto.setId(metodo.getId());
        dto.setNomeMetodo(metodo.getNomeMetodo());
        dto.setDescricao(metodo.getDescricao());
        return dto;
    }
}
