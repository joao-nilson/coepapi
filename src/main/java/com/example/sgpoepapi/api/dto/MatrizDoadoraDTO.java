package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.MatrizDoadora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime dataNascimento;
    private Integer numOocitos;
    private Boolean viavel;
    private Float precoOocito;

    public static MatrizDoadoraDTO create(MatrizDoadora doadora) {
        MatrizDoadoraDTO dto = new MatrizDoadoraDTO();
        dto.setId(doadora.getId());
        dto.setNome(doadora.getNome());
        dto.setRGD(doadora.getRGD());
        if (doadora.getRaca() != null) dto.setRacaId(doadora.getRaca().getId());
        if (doadora.getProprietario() != null) dto.setProprietarioId(doadora.getProprietario().getId());
        dto.setApelido(doadora.getApelido());
        dto.setDataNascimento(doadora.getDataNascimento());
        dto.setNumOocitos(doadora.getNumOocitos());
        dto.setViavel(doadora.getViavel());
        dto.setPrecoOocito(doadora.getPrecoOocito());
        return dto;
    }
}
