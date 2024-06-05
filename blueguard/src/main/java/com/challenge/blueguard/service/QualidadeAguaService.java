package com.challenge.blueguard.service;

import com.challenge.blueguard.entity.AreaMarinha;
import com.challenge.blueguard.entity.LocalizacaoNavio;
import com.challenge.blueguard.entity.QualidadeAgua;
import com.challenge.blueguard.repository.QualidadeAguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QualidadeAguaService {

    @Autowired
    private QualidadeAguaRepository qualidadeAguaRepository;

    public List<QualidadeAgua> listarQualidade(){
        return qualidadeAguaRepository.findAll();
    }

    public Optional<QualidadeAgua> buscarQualidadeAguaPorId(Long id){
        return qualidadeAguaRepository.findById(id);
    }

    public QualidadeAgua salvarQualidade (QualidadeAgua qualidadeAgua) {
        return qualidadeAguaRepository.save(qualidadeAgua);
    }

    public void removerQualidade(Long Id){
        qualidadeAguaRepository.deleteById(Id);
    }

    public QualidadeAgua atualizarQualidade (QualidadeAgua qualidadeAgua, Long id) {
        return qualidadeAguaRepository.findById(id)
                .map(qualidadeExistente -> {
                    qualidadeExistente.setTimestamp(qualidadeAgua.getTimestamp());
                    qualidadeExistente.setTemperatura(qualidadeAgua.getTemperatura());
                    qualidadeExistente.setPh(qualidadeAgua.getPh());
                    qualidadeExistente.setSalinidade(qualidadeAgua.getSalinidade());
                    qualidadeExistente.setOxigenio(qualidadeAgua.getOxigenio());
                    return qualidadeAguaRepository.save(qualidadeExistente);
                }).orElseThrow(() -> new IllegalStateException("Qualidade com ID " + id + " nao existe"));
    }

    public QualidadeAgua atualizarParcialmente(Long id, Map<String, Object> updates){
        return qualidadeAguaRepository.findById(id)
                .map(qualidadeExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(QualidadeAgua.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, qualidadeExistente, value );
                        }
                    });
                    return qualidadeAguaRepository.save(qualidadeExistente);
                }).orElseThrow(() -> new IllegalArgumentException("Qualidade com ID " + id + " nao existe."));
    }

}
