package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String CPF;
    private String inscricaoEstadual;
    private String endereco;
    private String telefone;
    private String email;

    public static ClienteDTO create(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCPF(cliente.getCPF());
        dto.setInscricaoEstadual(cliente.getInscriaao_estadual());
        dto.setEndereco(cliente.getEndereco());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());
        return dto;
    }
}
