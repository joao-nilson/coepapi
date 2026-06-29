package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.DosesSemenDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.DosesSemen;
import com.example.sgpoepapi.model.entity.MachoReprodutor;
import com.example.sgpoepapi.service.DosesSemenService;
import com.example.sgpoepapi.service.MachoReprodutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dosessemen")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Doses de Sêmen", description = "API de gerenciamento de doses de sêmen")
public class DosesSemenController {

    private final DosesSemenService service;
    private final MachoReprodutorService machoReprodutorService;

    @GetMapping()
    public ResponseEntity get() {
        List<DosesSemen> doses = service.getDosesSemen();
        return ResponseEntity.ok(doses.stream().map(DosesSemenDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de dose de sêmen pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dose de sêmen encontrada"),
            @ApiResponse(responseCode = "404", description = "Dose de sêmen não encontrada")
    })
    public ResponseEntity get(@Parameter(description = "Id da dose de sêmen") @PathVariable("id") Long id) {
        Optional<DosesSemen> doses = service.getDosesSemenById(id);
        if (!doses.isPresent()) {
            return new ResponseEntity("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(doses.map(DosesSemenDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de dose de sêmen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dose de sêmen salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody DosesSemenDTO dto) {
        try {
            DosesSemen doses = converter(dto);
            doses = service.salvar(doses);
            return new ResponseEntity(doses, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de dose de sêmen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dose de sêmen atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Dose de sêmen não encontrada")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id da dose de sêmen") @PathVariable("id") Long id, @RequestBody DosesSemenDTO dto) {
        if (!service.getDosesSemenById(id).isPresent()) {
            return new ResponseEntity("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        try {
            DosesSemen doses = converter(dto);
            doses.setId(id);
            service.salvar(doses);
            return ResponseEntity.ok(doses);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de dose de sêmen")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dose de sêmen excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Dose de sêmen não encontrada")
    })
    public ResponseEntity excluir(@Parameter(description = "Id da dose de sêmen") @PathVariable("id") Long id) {
        Optional<DosesSemen> doses = service.getDosesSemenById(id);
        if (!doses.isPresent()) {
            return new ResponseEntity("Doses de sêmen não encontradas", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(doses.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public DosesSemen converter(DosesSemenDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        DosesSemen doses = modelMapper.map(dto, DosesSemen.class);
        if (dto.getMachoReprodutorId() != null) {
            Optional<MachoReprodutor> macho = machoReprodutorService.getMachoReprodutorById(dto.getMachoReprodutorId());
            doses.setMachoReprodutor(macho.orElse(null));
        }
        return doses;
    }
}
