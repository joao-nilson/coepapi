package com.example.sgpoepapi.api.controller;

import com.example.sgpoepapi.api.dto.AspiracacaoDTO;
import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.model.entity.*;
import com.example.sgpoepapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/aspiracoes")
@RequiredArgsConstructor
@CrossOrigin
public class AspiracaoController {

    private final AspiracaoService service;
    private final ProjetoService projetoService;
    private final ClienteService clienteService;
    private final PrestadoraServicoService prestadoraServicoService;
    private final MatrizDoadoraService matrizDoadoraService;
    private final OocitoService oocitoService;
    private final FuncionarioService funcionarioService;
    private final MeioService meioService;

    @GetMapping()
    public ResponseEntity<?> get() {
        List<Aspiracao> aspiracoes = service.getAspiracoes();
        return ResponseEntity.ok(aspiracoes.stream().map(AspiracacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Aspiracao> aspiracao = service.getAspiracaoById(id);
        if (aspiracao.isEmpty()) {
            return new ResponseEntity<>("Aspiração não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(aspiracao.map(AspiracacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody AspiracacaoDTO dto) {
        try {
            Aspiracao aspiracao = converter(dto);
            aspiracao = service.salvar(aspiracao);
            return new ResponseEntity<>(aspiracao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody AspiracacaoDTO dto) {
        if (service.getAspiracaoById(id).isEmpty()) {
            return new ResponseEntity<>("Aspiração não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Aspiracao aspiracao = converter(dto);
            aspiracao.setId(id);
            service.salvar(aspiracao);
            return ResponseEntity.ok(aspiracao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        Optional<Aspiracao> aspiracao = service.getAspiracaoById(id);
        if (aspiracao.isEmpty()) {
            return new ResponseEntity<>("Aspiração não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(aspiracao.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Aspiracao converter(AspiracacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Aspiracao aspiracao = modelMapper.map(dto, Aspiracao.class);
        if (dto.getProjetoId() != null) {
            Optional<Projeto> projeto = projetoService.getProjetoById(dto.getProjetoId());
            aspiracao.setProjeto(projeto.orElse(null));
        }
        if (dto.getProprietarioClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getProprietarioClienteId());
            aspiracao.setProprietarioCliente(cliente.orElse(null));
        }
        if (dto.getProprietarioPrestadoraId() != null) {
            Optional<PrestadoraServico> prestadora = prestadoraServicoService.getPrestadoraServicoById(dto.getProprietarioPrestadoraId());
            aspiracao.setProprietarioPrestadora(prestadora.orElse(null));
        }
        if (dto.getDoadorasIds() != null) {
            List<MatrizDoadora> doadoras = dto.getDoadorasIds().stream()
                    .map(id -> matrizDoadoraService.getMatrizDoadoraById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            aspiracao.setDoadoras(doadoras);
        }
        if (dto.getOocitosIds() != null) {
            List<Oocito> oocitos = dto.getOocitosIds().stream()
                    .map(id -> oocitoService.getOocitoById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            aspiracao.setOocitos(oocitos);
        }
        if (dto.getTecnicosOPUIds() != null) {
            List<Funcionario> tecnicos = dto.getTecnicosOPUIds().stream()
                    .map(id -> funcionarioService.getFuncionarioById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            aspiracao.setTecnicosOPU(tecnicos);
        }
        if (dto.getMeiosIds() != null) {
            List<Meio> meios = dto.getMeiosIds().stream()
                    .map(id -> meioService.getMeioById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            aspiracao.setMeios(meios);
        }
        return aspiracao;
    }
}
