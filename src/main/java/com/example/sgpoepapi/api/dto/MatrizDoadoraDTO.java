package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MatrizDoadora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrizDoadoraDTO {
    private Long id;
    private String nome;
    private String RGD;
    private Long racaId;
    private Long proprietarioId;
    private String apelido;
    private String dataNascimento;
    private Integer numOocitos;
    private Boolean viavel;
    private Float precoOocito;

    public static MatrizDoadoraDTO create(MatrizDoadora doadora) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(doadora, MatrizDoadoraDTO.class);
    }
}
