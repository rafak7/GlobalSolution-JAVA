package com.challenge.blueguard.controller;

import com.challenge.blueguard.dto.MarcacoesDTO;
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
    public ResponseEntity<List<EntityModel<MarcacoesDTO>>> listar() {
        List<EntityModel<MarcacoesDTO>> marcacoes = marcacoesService.listarMarcacoes().stream()
                .map(marcacao -> EntityModel.of(marcacao,
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(marcacao.getIdMarcacoes()), "self"),
                        createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(marcacoes);
    }

    // POST
    @PostMapping
    @Operation(summary = "Criar uma nova Marcação")
    public ResponseEntity<EntityModel<MarcacoesDTO>> criarMarcacao(@RequestBody MarcacoesDTO marcacoesDTO) {
        MarcacoesDTO novaMarcacao = marcacoesService.salvarMarcacoes(marcacoesDTO);
        EntityModel<MarcacoesDTO> resource = EntityModel.of(novaMarcacao,
                createLinkWithMethod("POST", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(novaMarcacao.getIdMarcacoes()), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // PUT
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma Marcação existente")
    public ResponseEntity<EntityModel<MarcacoesDTO>> atualizarMarcacao(@PathVariable Long id, @RequestBody MarcacoesDTO marcacoesDTO) {
        MarcacoesDTO marcacaoAtualizada = marcacoesService.atualizarMarcacoes(marcacoesDTO, id);
        EntityModel<MarcacoesDTO> resource = EntityModel.of(marcacaoAtualizada,
                createLinkWithMethod("PUT", WebMvcLinkBuilder.methodOn(MarcacoesController.class).atualizarMarcacao(id, marcacoesDTO), "self"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(id), "consultar"),
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar(), "marcações"));
        return ResponseEntity.ok(resource);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma Marcação por ID")
    public ResponseEntity<EntityModel<MarcacoesDTO>> consultarId(@PathVariable Long id) {
        MarcacoesDTO marcacao = marcacoesService.buscarMarcacoesPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Marcação Não Encontrada"));
        EntityModel<MarcacoesDTO> resource = EntityModel.of(marcacao,
                createLinkWithMethod("GET", WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(marcacao.getIdMarcacoes()), "self"),
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
    public ResponseEntity<EntityModel<MarcacoesDTO>> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        MarcacoesDTO marcacaoAtualizada = marcacoesService.atualizarParcialmente(id, updates);
        EntityModel<MarcacoesDTO> resource = EntityModel.of(marcacaoAtualizada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).consultarId(id)).withRel("consultar"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).listar()).withRel("marcações"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarcacoesController.class).atualizarParcialmente(id, updates)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    // HEAD
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(summary = "Verificar a existência de uma Marcação por ID")
    public ResponseEntity<?> consultarHead(@PathVariable Long id) {
        Optional<MarcacoesDTO> marcacoes = marcacoesService.buscarMarcacoesPorId(id);
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
