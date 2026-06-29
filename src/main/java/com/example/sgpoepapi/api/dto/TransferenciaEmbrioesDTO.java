package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.TransferenciaEmbrioes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaEmbrioesDTO {
    private Long id;
    private Long projetoId;
    private String dataEtapa;
    private String horaInicio;
    private String horaTermino;
    private List<Long> embrioesIds;
    private Long tecnicoClienteId;
    private Long tecnicoPrestadoraId;
    private String horaEntregaEmb;

    public static TransferenciaEmbrioesDTO create(TransferenciaEmbrioes transferencia) {
        TransferenciaEmbrioesDTO dto = new TransferenciaEmbrioesDTO();
        dto.setId(transferencia.getId());
        if (transferencia.getProjeto() != null) dto.setProjetoId(transferencia.getProjeto().getId());
        dto.setDataEtapa(transferencia.getDataEtapa());
        dto.setHoraInicio(transferencia.getHoraInicio());
        dto.setHoraTermino(transferencia.getHoraTermino());
        if (transferencia.getEmbrioes() != null)
            dto.setEmbrioesIds(transferencia.getEmbrioes().stream().map(e -> e.getId()).collect(Collectors.toList()));
        if (transferencia.getTecnicoCliente() != null)
            dto.setTecnicoClienteId(transferencia.getTecnicoCliente().getId());
        if (transferencia.getTecnicoPrestadora() != null)
            dto.setTecnicoPrestadoraId(transferencia.getTecnicoPrestadora().getId());
        dto.setHoraEntregaEmb(transferencia.getHoraEntregaEmb());
        return dto;
    }
}
