package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.DosesSemen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(doses, DosesSemenDTO.class);
    }
}
