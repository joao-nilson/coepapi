package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MachoReprodutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        MachoReprodutorDTO dto = new MachoReprodutorDTO();
        dto.setId(macho.getId());
        dto.setNome(macho.getNome());
        dto.setRGD(macho.getRGD());
        if (macho.getRaca() != null) dto.setRacaId(macho.getRaca().getId());
        if (macho.getProprietario() != null) dto.setProprietarioId(macho.getProprietario().getId());
        dto.setSemenSexado(macho.getSemenSexado());
        dto.setPrecoDose(macho.getPrecoDose());
        return dto;
    }
}
