package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Oocito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OocitoDTO {
    private Long id;
    private Long maeId;
    private Boolean viavel;
    private Float precoOocito;
    private Long classificacaoId;

    public static OocitoDTO create(Oocito oocito) {
        OocitoDTO dto = new OocitoDTO();
        dto.setId(oocito.getId());
        if (oocito.getMae() != null) dto.setMaeId(oocito.getMae().getId());
        dto.setViavel(oocito.getViavel());
        dto.setPrecoOocito(oocito.getPrecoOocito());
        if (oocito.getClassificacao() != null) dto.setClassificacaoId(oocito.getClassificacao().getId());
        return dto;
    }
}
