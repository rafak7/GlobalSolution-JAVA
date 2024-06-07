package com.challenge.blueguard.service;

import com.challenge.blueguard.dto.MarcacoesDTO;
import com.challenge.blueguard.entity.Marcacoes;
import com.challenge.blueguard.repository.MarcacoesRepository;
import com.challenge.blueguard.util.MarcacoesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarcacoesService {

    @Autowired
    private MarcacoesRepository marcacoesRepository;

    public List<MarcacoesDTO> listarMarcacoes() {
        return marcacoesRepository.findAll()
                .stream()
                .map(MarcacoesConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MarcacoesDTO> buscarMarcacoesPorId(Long id) {
        return marcacoesRepository.findById(id)
                .map(MarcacoesConverter::toDTO);
    }

    public MarcacoesDTO salvarMarcacoes(MarcacoesDTO marcacoesDTO) {
        Marcacoes marcacoes = MarcacoesConverter.toEntity(marcacoesDTO);
        Marcacoes savedMarcacoes = marcacoesRepository.save(marcacoes);
        return MarcacoesConverter.toDTO(savedMarcacoes);
    }

    public void removerMarcacoes(Long id) {
        marcacoesRepository.deleteById(id);
    }

    public MarcacoesDTO atualizarMarcacoes(MarcacoesDTO marcacoesDTO, Long id) {
        return marcacoesRepository.findById(id)
                .map(marcacaoExistente -> {
                    marcacaoExistente.setPh(marcacoesDTO.getPh());
                    marcacaoExistente.setPotavel(marcacoesDTO.getPotavel());
                    marcacaoExistente.setLocal(marcacoesDTO.getLocal());
                    marcacaoExistente.setObservacoes(marcacoesDTO.getObservacoes());
                    marcacaoExistente.setTemperatura(marcacoesDTO.getTemperatura());
                    marcacaoExistente.setNivelSujeira(marcacoesDTO.getNivelSujeira());
                    Marcacoes updatedMarcacoes = marcacoesRepository.save(marcacaoExistente);
                    return MarcacoesConverter.toDTO(updatedMarcacoes);
                }).orElseThrow(() -> new IllegalStateException("Marcacao com ID " + id + " nao existe"));
    }

    public MarcacoesDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        return marcacoesRepository.findById(id)
                .map(marcacaoExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Marcacoes.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, marcacaoExistente, value);
                        }
                    });
                    Marcacoes updatedMarcacoes = marcacoesRepository.save(marcacaoExistente);
                    return MarcacoesConverter.toDTO(updatedMarcacoes);
                }).orElseThrow(() -> new IllegalArgumentException("Marcacao com ID " + id + " nao existe."));
    }
}
