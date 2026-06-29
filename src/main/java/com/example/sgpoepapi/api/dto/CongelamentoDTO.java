package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Congelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CongelamentoDTO {
    private Long id;
    private Long embriaoId;
    private Long metodoId;
    private String dataCong;
    private Float precoCongelamento;

    public static CongelamentoDTO create(Congelamento congelamento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(congelamento, CongelamentoDTO.class);
    }
}
