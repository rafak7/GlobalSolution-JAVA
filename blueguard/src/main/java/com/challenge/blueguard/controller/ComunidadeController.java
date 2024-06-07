package com.challenge.blueguard.controller;

import com.challenge.blueguard.entity.Comunidade;
import com.challenge.blueguard.service.ComunidadeService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comunidade")
@Tag(name = "Comunidade", description = "API para gerenciar a Comunidade")
public class ComunidadeController {

    @Autowired
    private ComunidadeService comunidadeService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todas os Posts")
    public ResponseEntity<List<EntityModel<Comunidade>>> listar() {
        List<EntityModel<Comunidade>> comunidade = comunidadeService.listarComunidade().stream()
                .map(comunidades -> EntityModel.of(comunidades,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(comunidades.getIdComunidade()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(comunidade);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma novo Post na comunidade")
    public ResponseEntity<EntityModel<Comunidade>> criarComunidade(@RequestBody Comunidade comuinade) {
        Comunidade novaComunidade = comunidadeService.salvarComunidade(comuinade);
        EntityModel<Comunidade> resource = EntityModel.of(novaComunidade,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(novaComunidade.getIdComunidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Post existente")
    public ResponseEntity<EntityModel<Comunidade>> atualizarComunidade(@PathVariable Long id, @RequestBody Comunidade comuinade){
        Comunidade comuidadeAtualizada = comunidadeService.atualizarComunidade(comuinade, id);
        EntityModel<Comunidade> resource = EntityModel.of(comuidadeAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(ComunidadeController.class).atualizarComunidade(id, comuinade), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar um Post por ID")
    public ResponseEntity<EntityModel<Comunidade>> consultarId(@PathVariable Long id) {
        Comunidade comunidade = comunidadeService.buscarComunidadePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Post Não Encontrada"));
        EntityModel<Comunidade> resource = EntityModel.of(comunidade,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(comunidade.getIdComunidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma Post por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        comunidadeService.removerComunidade(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para uma Comunidade por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean comunidadeExistente = comunidadeService.buscarComunidadePorId(id).isPresent();
        if (comunidadeExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um Post")
    public ResponseEntity<EntityModel<Comunidade>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Comunidade comunidadeAtualizada = comunidadeService.atualizarParcialmente(id, updates);

        EntityModel<Comunidade> resource = EntityModel.of(comunidadeAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar()).withRel("comunidade"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    private Link createLinkWithMethod(String method, Object invocationValue, String rel) {
        return Link.of(WebMvcLinkBuilder.linkTo(invocationValue).toUri().toString(), rel)
                .withTitle(method + " method");
    }

}
