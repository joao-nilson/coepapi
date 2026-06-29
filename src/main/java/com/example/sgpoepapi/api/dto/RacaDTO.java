package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Raca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacaDTO {
    private Long id;
    private String raca;
    private Float fracao;

    public static RacaDTO create(Raca raca) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(raca, RacaDTO.class);
    }
}
