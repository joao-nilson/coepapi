package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MatrizReceptora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime dataNascimento;
    private Integer nOocitos;

    public static MatrizReceptoraDTO create(MatrizReceptora receptora) {
        MatrizReceptoraDTO dto = new MatrizReceptoraDTO();
        dto.setId(receptora.getId());
        dto.setNome(receptora.getNome());
        dto.setRGD(receptora.getRGD());
        if (receptora.getRaca() != null) dto.setRacaId(receptora.getRaca().getId());
        if (receptora.getProprietario() != null) dto.setProprietarioId(receptora.getProprietario().getId());
        dto.setNumero(receptora.getNumero());
        dto.setPrenha(receptora.getPrenha());
        dto.setDataNascimento(receptora.getDataNascimento());
        dto.setNOocitos(receptora.getNOocitos());
        return dto;
    }
}
