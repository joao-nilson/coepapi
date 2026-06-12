package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO {
    private Long id;
    private Long prestadoraId;
    private Long clienteId;
    private LocalDateTime dataInicioProjeto;
    private LocalDateTime dataTerminoProjeto;
    private Integer nAspiracoesProjeto;
    private Integer nPremiacoesProjeto;
    private String racaEmbrioes;

    public static ProjetoDTO create(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        if (projeto.getPrestadora() != null) dto.setPrestadoraId(projeto.getPrestadora().getId());
        if (projeto.getCliente() != null) dto.setClienteId(projeto.getCliente().getId());
        dto.setDataInicioProjeto(projeto.getDataInicioProjeto());
        dto.setDataTerminoProjeto(projeto.getDataTerminoProjeto());
        dto.setNAspiracoesProjeto(projeto.getNAspiracoesProjeto());
        dto.setNPremiacoesProjeto(projeto.getNPremiacoesProjeto());
        dto.setRacaEmbrioes(projeto.getRacaEmbrioes());
        return dto;
    }
}
