package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Congelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CongelamentoDTO {
    private Long id;
    private Long embriaoId;
    private Long metodoId;
    private LocalDateTime dataCong;
    private Float precoCongelamento;

    public static CongelamentoDTO create(Congelamento congelamento) {
        CongelamentoDTO dto = new CongelamentoDTO();
        dto.setId(congelamento.getId());
        if (congelamento.getEmbriao() != null) dto.setEmbriaoId(congelamento.getEmbriao().getId());
        if (congelamento.getMetodo() != null) dto.setMetodoId(congelamento.getMetodo().getId());
        dto.setDataCong(congelamento.getDataCong());
        dto.setPrecoCongelamento(congelamento.getPrecoCongelamento());
        return dto;
    }
}
