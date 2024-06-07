package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.Observacao;
import com.challenge.blueguard.repository.ObservacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ObservacaoService {

    @Autowired
    private ObservacaoRepository observacaoRepository;

    public List<Observacao> listarObservacoes() {
        return observacaoRepository.findAll();
    }

    public Optional<Observacao> buscarObservacaoPorId(Long Id) {
        return observacaoRepository.findById(Id);
    }

    public Observacao salvarObservacao(Observacao observacao) {
        return observacaoRepository.save(observacao);
    }

    public void removerObservacao(Long Id){
        observacaoRepository.deleteById(Id);
    }

    public Observacao atualizarObservacao (Observacao observacao, Long id) {
        return observacaoRepository.findById(id)
                .map(observacaoExistente -> {
                    observacaoExistente.setDadosObservacao(observacao.getDadosObservacao());
                    observacaoExistente.setTimestampObservacao(observacao.getTimestampObservacao());
                    return observacaoRepository.save(observacaoExistente);
                }).orElseThrow(() -> new IllegalStateException("Observacao com ID " + id + " nao existe"));
    }

    public Observacao atualizarParcialmente(Long id, Map<String, Object> updates){
        return observacaoRepository.findById(id)
                .map(observacaoExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Observacao.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, observacaoExistente, value );
                        }
                    });
                    return observacaoRepository.save(observacaoExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Observacao com ID " + id + " nao existe."));
    }

}
