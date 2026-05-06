package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.PrestadoraServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestadoraServicoDTO {
    private Long id;
    private String nome;
    private String CNPJ;
    private String endereco;
    private String numRegistroMapa;
    private String telefone;
    private String email;
    private String instagram;

    public static PrestadoraServicoDTO create(PrestadoraServico prestadora) {
        PrestadoraServicoDTO dto = new PrestadoraServicoDTO();
        dto.setId(prestadora.getId());
        dto.setNome(prestadora.getNome());
        dto.setCNPJ(prestadora.getCNPJ());
        dto.setEndereco(prestadora.getEndereco());
        dto.setNumRegistroMapa(prestadora.getNum_registro_mapa());
        dto.setTelefone(prestadora.getTelefone());
        dto.setEmail(prestadora.getEmail());
        dto.setInstagram(prestadora.getInstagram());
        return dto;
    }
}
