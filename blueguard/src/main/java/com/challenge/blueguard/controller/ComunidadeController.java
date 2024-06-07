package com.challenge.blueguard.controller;

import com.challenge.blueguard.dto.ComunidadeDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comunidade")
@Tag(name = "Comunidade", description = "API para gerenciar a Comunidade")
public class ComunidadeController {

    @Autowired
    private ComunidadeService comunidadeService;

    // GET
    @GetMapping
    @Operation(summary = "Listar todos os posts")
    public ResponseEntity<List<EntityModel<ComunidadeDTO>>> listar() {
        List<EntityModel<ComunidadeDTO>> comunidades = comunidadeService.listarComunidade().stream()
                .map(comunidade -> EntityModel.of(comunidade,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(comunidade.getIdComunidade()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(comunidades);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar um novo post na comunidade")
    public ResponseEntity<EntityModel<ComunidadeDTO>> criarComunidade(@RequestBody ComunidadeDTO comunidadeDTO) {
        ComunidadeDTO novaComunidade = comunidadeService.salvarComunidade(comunidadeDTO);
        EntityModel<ComunidadeDTO> resource = EntityModel.of(novaComunidade,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(novaComunidade.getIdComunidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um post existente")
    public ResponseEntity<EntityModel<ComunidadeDTO>> atualizarComunidade(@PathVariable Long id, @RequestBody ComunidadeDTO comunidadeDTO) {
        ComunidadeDTO comunidadeAtualizada = comunidadeService.atualizarComunidade(comunidadeDTO, id);
        EntityModel<ComunidadeDTO> resource = EntityModel.of(comunidadeAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(ComunidadeController.class).atualizarComunidade(id, comunidadeDTO), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar um post por ID")
    public ResponseEntity<EntityModel<ComunidadeDTO>> consultarId(@PathVariable Long id) {
        ComunidadeDTO comunidade = comunidadeService.buscarComunidadePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado"));
        EntityModel<ComunidadeDTO> resource = EntityModel.of(comunidade,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(comunidade.getIdComunidade()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar(), "comunidade"));
        return ResponseEntity.ok(resource);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um post por ID")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        comunidadeService.removerComunidade(id);
        return ResponseEntity.ok().build();
    }

    // OPTIONS
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    @Operation(summary = "Ver opções disponíveis para um post por ID")
    public ResponseEntity<?> options(@PathVariable Long id) {
        boolean comunidadeExistente = comunidadeService.buscarComunidadePorId(id).isPresent();
        if (comunidadeExistente) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD).build();
    }

    // PATCH
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um post")
    public ResponseEntity<EntityModel<ComunidadeDTO>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ComunidadeDTO comunidadeAtualizada = comunidadeService.atualizarParcialmente(id, updates);
        EntityModel<ComunidadeDTO> resource = EntityModel.of(comunidadeAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).listar()).withRel("comunidade"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComunidadeController.class).atualizarParcialmente(id, updates)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de um post por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<ComunidadeDTO> comunidade = comunidadeService.buscarComunidadePorId(id);
        if (!comunidade.isPresent()) {
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
