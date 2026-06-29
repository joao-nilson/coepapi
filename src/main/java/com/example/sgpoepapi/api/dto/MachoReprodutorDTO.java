package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MachoReprodutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachoReprodutorDTO {
    private Long id;
    private String nome;
    private String RGD;
    private Long racaId;
    private Long proprietarioId;
    private Boolean semenSexado;
    private Float precoDose;

    public static MachoReprodutorDTO create(MachoReprodutor macho) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(macho, MachoReprodutorDTO.class);
    }
}
