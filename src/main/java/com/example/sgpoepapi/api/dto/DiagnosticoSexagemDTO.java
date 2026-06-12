package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.DiagnosticoSexagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticoSexagemDTO {
    private Long id;
    private List<Long> machosIds;
    private List<Long> femeasIds;

    public static DiagnosticoSexagemDTO create(DiagnosticoSexagem diagnostico) {
        DiagnosticoSexagemDTO dto = new DiagnosticoSexagemDTO();
        dto.setId(diagnostico.getId());
        if (diagnostico.getMachos() != null)
            dto.setMachosIds(diagnostico.getMachos().stream().map(e -> e.getId()).collect(Collectors.toList()));
        if (diagnostico.getFemeas() != null)
            dto.setFemeasIds(diagnostico.getFemeas().stream().map(e -> e.getId()).collect(Collectors.toList()));
        return dto;
    }
}
