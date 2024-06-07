package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.Observacao;
import com.challenge.blueguard.service.ObservacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/observacao")
@Tag(name = "Observações", description = "API para gerenciar observações")
public class ObservacaoController {

    @Autowired
    private ObservacaoService observacaoService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas as observações")
    public ResponseEntity<List<EntityModel<Observacao>>> listar() {
        List<EntityModel<Observacao>> observacoes = observacaoService.listarObservacoes().stream()
                .map(observacao -> EntityModel.of(observacao,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).consultarId(observacao.getIdObservacao()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).listar(), "observacoes")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(observacoes);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova observação")
    public ResponseEntity<EntityModel<Observacao>> criarObservacao(@RequestBody Observacao observacao) {
        Observacao novaObservacao = observacaoService.salvarObservacao(observacao);
        EntityModel<Observacao> resource = EntityModel.of(novaObservacao,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(ObservacaoController.class).consultarId(novaObservacao.getIdObservacao()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).listar(), "observacoes"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma observação existente")
    public ResponseEntity<EntityModel<Observacao>> atualizarObservacao(@PathVariable Long id, @RequestBody Observacao observacao){
        Observacao observacaoAtualizada = observacaoService.atualizarObservacao(observacao, id);
        EntityModel<Observacao> resource = EntityModel.of(observacaoAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(ObservacaoController.class).atualizarObservacao(id, observacao), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).listar(), "observacoes"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma observação por ID")
    public ResponseEntity<EntityModel<Observacao>> consultarId(@PathVariable Long id) {
        Observacao observacao = observacaoService.buscarObservacaoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Observação Não Encontrada"));
        EntityModel<Observacao> resource = EntityModel.of(observacao,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).consultarId(observacao.getIdObservacao()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ObservacaoController.class).listar(), "observacoes"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma observação por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        observacaoService.removerObservacao(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma observação por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean observacaoExistente = observacaoService.buscarObservacaoPorId(id).isPresent();
        if (observacaoExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente uma observação")
    public ResponseEntity<EntityModel<Observacao>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Observacao observacaoAtualizada = observacaoService.atualizarParcialmente(id, updates);

        EntityModel<Observacao> resource = EntityModel.of(observacaoAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObservacaoController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObservacaoController.class).listar()).withRel("observacoes"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObservacaoController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma observação por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<Observacao> observacao = observacaoService.buscarObservacaoPorId(id);
        if (!observacao.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    private Link createLinkWithMethod(String method, Object invocationValue, String rel) {
        return Link.of(WebMvcLinkBuilder.linkTo(invocationValue).toUri().toString(), rel)
                .withTitle(method + " method");
    }
}