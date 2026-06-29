package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MatrizReceptora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrizReceptoraDTO {
    private Long id;
    private String nome;
    private String RGD;
    private Long racaId;
    private Long proprietarioId;
    private Integer numero;
    private Boolean prenha;
    private String dataNascimento;
    private Integer nOocitos;

    public static MatrizReceptoraDTO create(MatrizReceptora receptora) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(receptora, MatrizReceptoraDTO.class);
    }
}
