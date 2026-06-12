package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.DosesSemen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DosesSemenDTO {
    private Long id;
    private Boolean semenSexado;
    private Float nDoses;
    private Float precoDose;
    private Long machoReprodutorId;

    public static DosesSemenDTO create(DosesSemen doses) {
        DosesSemenDTO dto = new DosesSemenDTO();
        dto.setId(doses.getId());
        dto.setSemenSexado(doses.getSemenSexado());
        dto.setNDoses(doses.getNDoses());
        dto.setPrecoDose(doses.getPrecoDose());
        if (doses.getMachoReprodutor() != null) dto.setMachoReprodutorId(doses.getMachoReprodutor().getId());
        return dto;
    }
}
