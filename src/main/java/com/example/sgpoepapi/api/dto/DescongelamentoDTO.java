package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Descongelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescongelamentoDTO {
    private Long id;
    private Long embriaoId;
    private Long metodoId;
    private LocalDateTime dataDescong;
    private Float precoDescongelamento;

    public static DescongelamentoDTO create(Descongelamento descongelamento) {
        DescongelamentoDTO dto = new DescongelamentoDTO();
        dto.setId(descongelamento.getId());
        if (descongelamento.getEmbriao() != null) dto.setEmbriaoId(descongelamento.getEmbriao().getId());
        if (descongelamento.getMetodo() != null) dto.setMetodoId(descongelamento.getMetodo().getId());
        dto.setDataDescong(descongelamento.getDataDescong());
        dto.setPrecoDescongelamento(descongelamento.getPrecoDescongelamento());
        return dto;
    }
}
