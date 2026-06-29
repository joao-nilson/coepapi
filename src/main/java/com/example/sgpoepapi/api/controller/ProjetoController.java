package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.ProjetoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.Cliente;
import com.example.sgpoepapi.model.entity.PrestadoraServico;
import com.example.sgpoepapi.model.entity.Projeto;
import com.example.sgpoepapi.service.ClienteService;
import com.example.sgpoepapi.service.PrestadoraServicoService;
import com.example.sgpoepapi.service.ProjetoService;
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
@RequestMapping("/api/v1/projetos")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Projetos", description = "API de gerenciamento de projetos")
public class ProjetoController {

    private final ProjetoService service;
    private final PrestadoraServicoService prestadoraServicoService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Projeto> projetos = service.getProjetos();
        return ResponseEntity.ok(projetos.stream().map(ProjetoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de projeto pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity get(@Parameter(description = "Id do projeto") @PathVariable("id") Long id) {
        Optional<Projeto> projeto = service.getProjetoById(id);
        if (!projeto.isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(projeto.map(ProjetoDTO::create));
    }

    @PostMapping()
    @Operation(summary = "Salvar um novo registro de projeto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Projeto salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity post(@RequestBody ProjetoDTO dto) {
        try {
            Projeto projeto = converter(dto);
            projeto = service.salvar(projeto);
            return new ResponseEntity(projeto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um registro de projeto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity atualizar(@Parameter(description = "Id do projeto") @PathVariable("id") Long id, @RequestBody ProjetoDTO dto) {
        if (!service.getProjetoById(id).isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Projeto projeto = converter(dto);
            projeto.setId(id);
            service.salvar(projeto);
            return ResponseEntity.ok(projeto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir um registro de projeto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Projeto excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity excluir(@Parameter(description = "Id do projeto") @PathVariable("id") Long id) {
        Optional<Projeto> projeto = service.getProjetoById(id);
        if (!projeto.isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(projeto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Projeto converter(ProjetoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Projeto projeto = modelMapper.map(dto, Projeto.class);
        if (dto.getPrestadoraId() != null) {
            Optional<PrestadoraServico> prestadora = prestadoraServicoService.getPrestadoraServicoById(dto.getPrestadoraId());
            projeto.setPrestadora(prestadora.orElse(null));
        }
        if (dto.getClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getClienteId());
            projeto.setCliente(cliente.orElse(null));
        }
        return projeto;
    }
}
