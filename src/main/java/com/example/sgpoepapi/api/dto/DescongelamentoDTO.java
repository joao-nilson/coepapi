package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Descongelamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescongelamentoDTO {
    private Long id;
    private Long embriaoId;
    private Long metodoId;
    private String dataDescong;
    private Float precoDescongelamento;

    public static DescongelamentoDTO create(Descongelamento descongelamento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(descongelamento, DescongelamentoDTO.class);
    }
}
