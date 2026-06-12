package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String cargo;

    public static FuncionarioDTO create(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCargo(funcionario.getCargo());
        return dto;
    }
}
