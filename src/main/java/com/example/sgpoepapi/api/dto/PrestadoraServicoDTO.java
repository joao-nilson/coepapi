package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.PrestadoraServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(prestadora, PrestadoraServicoDTO.class);
    }
}
