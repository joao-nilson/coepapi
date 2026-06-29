package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Sincronizacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SincronizacaoDTO {
    private Long id;
    private Long projetoId;
    private String dataEtapa;
    private String horaInicio;
    private String horaTermino;
    private List<Long> receptorasIds;
    private Long proprietarioClienteId;
    private Long proprietarioPrestadoraId;
    private List<Long> receptorasSincronizadasIds;

    public static SincronizacaoDTO create(Sincronizacao sincronizacao) {
        SincronizacaoDTO dto = new SincronizacaoDTO();
        dto.setId(sincronizacao.getId());
        if (sincronizacao.getProjeto() != null) dto.setProjetoId(sincronizacao.getProjeto().getId());
        dto.setDataEtapa(sincronizacao.getDataEtapa());
        dto.setHoraInicio(sincronizacao.getHoraInicio());
        dto.setHoraTermino(sincronizacao.getHoraTermino());
        if (sincronizacao.getReceptoras() != null)
            dto.setReceptorasIds(sincronizacao.getReceptoras().stream().map(r -> r.getId()).collect(Collectors.toList()));
        if (sincronizacao.getProprietarioCliente() != null)
            dto.setProprietarioClienteId(sincronizacao.getProprietarioCliente().getId());
        if (sincronizacao.getProprietarioPrestadora() != null)
            dto.setProprietarioPrestadoraId(sincronizacao.getProprietarioPrestadora().getId());
        if (sincronizacao.getReceptorasSincronizadas() != null)
            dto.setReceptorasSincronizadasIds(sincronizacao.getReceptorasSincronizadas().stream().map(r -> r.getId()).collect(Collectors.toList()));
        return dto;
    }
}
