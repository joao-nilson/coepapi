package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Aspiracao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AspiracacaoDTO {
    private Long id;
    private Long projetoId;
    private LocalDateTime dataEtapa;
    private LocalDateTime horaInicio;
    private LocalDateTime horaTermino;
    private List<Long> doadorasIds;
    private List<Long> oocitosIds;
    private Long proprietarioClienteId;
    private Long proprietarioPrestadoraId;
    private List<Long> tecnicosOPUIds;
    private List<Long> meiosIds;
    private Float tempChupada;

    public static AspiracacaoDTO create(Aspiracao aspiracao) {
        AspiracacaoDTO dto = new AspiracacaoDTO();
        dto.setId(aspiracao.getId());
        if (aspiracao.getProjeto() != null) dto.setProjetoId(aspiracao.getProjeto().getId());
        dto.setDataEtapa(aspiracao.getDataEtapa());
        dto.setHoraInicio(aspiracao.getHoraInicio());
        dto.setHoraTermino(aspiracao.getHoraTermino());
        if (aspiracao.getDoadoras() != null)
            dto.setDoadorasIds(aspiracao.getDoadoras().stream().map(d -> d.getId()).collect(Collectors.toList()));
        if (aspiracao.getOocitos() != null)
            dto.setOocitosIds(aspiracao.getOocitos().stream().map(o -> o.getId()).collect(Collectors.toList()));
        if (aspiracao.getProprietarioCliente() != null)
            dto.setProprietarioClienteId(aspiracao.getProprietarioCliente().getId());
        if (aspiracao.getProprietarioPrestadora() != null)
            dto.setProprietarioPrestadoraId(aspiracao.getProprietarioPrestadora().getId());
        if (aspiracao.getTecnicosOPU() != null)
            dto.setTecnicosOPUIds(aspiracao.getTecnicosOPU().stream().map(f -> f.getId()).collect(Collectors.toList()));
        if (aspiracao.getMeios() != null)
            dto.setMeiosIds(aspiracao.getMeios().stream().map(m -> m.getId()).collect(Collectors.toList()));
        dto.setTempChupada(aspiracao.getTempChupada());
        return dto;
    }
}
