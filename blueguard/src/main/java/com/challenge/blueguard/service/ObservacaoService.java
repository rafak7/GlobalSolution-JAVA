package com.challenge.blueguard.service;

import com.challenge.blueguard.dto.ObservacaoDTO;
import com.challenge.blueguard.entity.Observacao;
import com.challenge.blueguard.repository.ObservacaoRepository;
import com.challenge.blueguard.util.ObservacaoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObservacaoService {

    @Autowired
    private ObservacaoRepository observacaoRepository;

    public List<ObservacaoDTO> listarObservacoes() {
        return observacaoRepository.findAll()
                .stream()
                .map(ObservacaoConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ObservacaoDTO> buscarObservacaoPorId(Long id) {
        return observacaoRepository.findById(id)
                .map(ObservacaoConverter::toDTO);
    }

    public ObservacaoDTO salvarObservacao(ObservacaoDTO observacaoDTO) {
        Observacao observacao = ObservacaoConverter.toEntity(observacaoDTO);
        Observacao savedObservacao = observacaoRepository.save(observacao);
        return ObservacaoConverter.toDTO(savedObservacao);
    }

    public void removerObservacao(Long id) {
        observacaoRepository.deleteById(id);
    }

    public ObservacaoDTO atualizarObservacao(ObservacaoDTO observacaoDTO, Long id) {
        return observacaoRepository.findById(id)
                .map(observacaoExistente -> {
                    observacaoExistente.setTimestampObservacao(observacaoDTO.getTimestampObservacao());
                    observacaoExistente.setDadosObservacao(observacaoDTO.getDadosObservacao());
                    Observacao updatedObservacao = observacaoRepository.save(observacaoExistente);
                    return ObservacaoConverter.toDTO(updatedObservacao);
                }).orElseThrow(() -> new IllegalStateException("Observacao com ID " + id + " nao existe"));
    }

    public ObservacaoDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        return observacaoRepository.findById(id)
                .map(observacaoExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Observacao.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, observacaoExistente, value);
                        }
                    });
                    Observacao updatedObservacao = observacaoRepository.save(observacaoExistente);
                    return ObservacaoConverter.toDTO(updatedObservacao);
                }).orElseThrow(() -> new IllegalArgumentException("Observacao com ID " + id + " nao existe."));
    }
}
