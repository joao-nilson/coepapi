package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.DiagnosticoGestacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoGestacaoDTO {
    private Long id;
    private List<Long> prenhasIds;
    private List<Long> vaziasIds;

    public static DiagnosticoGestacaoDTO create(DiagnosticoGestacao diagnostico) {
        DiagnosticoGestacaoDTO dto = new DiagnosticoGestacaoDTO();
        dto.setId(diagnostico.getId());
        if (diagnostico.getPrenhas() != null)
            dto.setPrenhasIds(diagnostico.getPrenhas().stream().map(r -> r.getId()).collect(Collectors.toList()));
        if (diagnostico.getVazias() != null)
            dto.setVaziasIds(diagnostico.getVazias().stream().map(r -> r.getId()).collect(Collectors.toList()));
        return dto;
    }
}
