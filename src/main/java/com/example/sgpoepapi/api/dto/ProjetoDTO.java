package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO {
    private Long id;
    private Long prestadoraId;
    private Long clienteId;
    private String dataInicioProjeto;
    private String dataTerminoProjeto;
    private String nAspiracoesProjeto;
    private String nPremiacoesProjeto;
    private String racaEmbrioes;

    public static ProjetoDTO create(Projeto projeto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(projeto, ProjetoDTO.class);
    }
}
