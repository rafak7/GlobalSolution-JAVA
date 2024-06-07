package com.challenge.blueguard.controller;



import com.challenge.blueguard.entity.Marcacoes;
import com.challenge.blueguard.service.MarcacoesService;
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
@RequestMapping("/marcacoes")
@Tag(name = "Marcações", description = "API para gerenciar Marcações")
public class MarcacoesController {

    @Autowired
    private MarcacoesService marcacoesService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas as Marcações")
    public ResponseEntity<List<EntityModel<Marcacoes>>> listar() {
        List<EntityModel<Marcacoes>> marcacao = marcacoesService.listarMarcacoes().stream()
                .map(marcacoes -> EntityModel.of(marcacoes,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(marcacoes.getIdMarcacoes()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(marcacao);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova Marcação")
    public ResponseEntity<EntityModel<Marcacoes>> criarMarcacao(@RequestBody Marcacoes marcacoes) {
        Marcacoes novaMarcacao = marcacoesService.salvarMarcacoes(marcacoes);
        EntityModel<Marcacoes> resource = EntityModel.of(novaMarcacao,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(novaMarcacao.getIdMarcacoes()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma Marcações existente")
    public ResponseEntity<EntityModel<Marcacoes>> atualizarMarcacao(@PathVariable Long id, @RequestBody Marcacoes marcacoes){
        Marcacoes marcacaoAtualizada = marcacoesService.atualizarMarcacoes(marcacoes, id);
        EntityModel<Marcacoes> resource = EntityModel.of(marcacaoAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(MarcacoesController.class).atualizarMarcacao(id, marcacoes), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma Marcação por ID")
    public ResponseEntity<EntityModel<Marcacoes>> consultarId(@PathVariable Long id) {
        Marcacoes marcacoes = marcacoesService.buscarMarcacoesPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Marcação Não Encontrada"));
        EntityModel<Marcacoes> resource = EntityModel.of(marcacoes,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(marcacoes.getIdMarcacoes()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma Marcação por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        marcacoesService.removerMarcacoes(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma Marcação por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean marcacaoExistente = marcacoesService.buscarMarcacoesPorId(id).isPresent();
        if (marcacaoExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente uma Marcação")
    public ResponseEntity<EntityModel<Marcacoes>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Marcacoes marcacaoAtualizada = marcacoesService.atualizarParcialmente(id, updates);

        EntityModel<Marcacoes> resource = EntityModel.of(marcacaoAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar()).withRel("marcações"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma Marcação por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<Marcacoes> marcacoes = marcacoesService.buscarMarcacoesPorId(id);
        if (!marcacoes.isPresent()) {
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
