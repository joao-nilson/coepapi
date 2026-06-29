package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.FIV;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FivDTO {
    private Long id;
    private Integer nOocitosFecundados;
    private Integer nEmbrioes;
    private String dataFIV;
    private Float precoFIV;
    private Long maeId;
    private Long paiId;
    private List<Long> embrioesIds;

    public static FivDTO create(FIV fiv) {
        FivDTO dto = new FivDTO();
        dto.setId(fiv.getId());
        dto.setNOocitosFecundados(fiv.getNOocitosFecundados());
        dto.setNEmbrioes(fiv.getNEmbrioes());
        dto.setDataFIV(fiv.getDataFIV());
        dto.setPrecoFIV(fiv.getPrecoFIV());
        if (fiv.getMae() != null) dto.setMaeId(fiv.getMae().getId());
        if (fiv.getPai() != null) dto.setPaiId(fiv.getPai().getId());
        if (fiv.getEmbrioes() != null)
            dto.setEmbrioesIds(fiv.getEmbrioes().stream().map(e -> e.getId()).collect(Collectors.toList()));
        return dto;
    }
}
