package com.example.sgpoepapi.api.dto;

import com.example.sgpoepapi.model.entity.Oocito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OocitoDTO {
    private Long id;
    private Long maeId;
    private Boolean viavel;
    private Float precoOocito;
    private Long classificacaoId;

    public static OocitoDTO create(Oocito oocito) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(oocito, OocitoDTO.class);
    }
}
