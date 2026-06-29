package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Meio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeioDTO {
    private Long id;
    private String tipoDeMeio;
    private String descricao;
    private Float precoDeMeio;
    private Float quantidade;

    public static MeioDTO create(Meio meio) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(meio, MeioDTO.class);
    }
}
