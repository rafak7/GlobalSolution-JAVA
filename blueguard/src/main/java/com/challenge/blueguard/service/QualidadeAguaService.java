package com.challenge.blueguard.service;

import com.challenge.blueguard.dto.QualidadeAguaDTO;
import com.challenge.blueguard.entity.QualidadeAgua;
import com.challenge.blueguard.repository.QualidadeAguaRepository;
import com.challenge.blueguard.util.QualidadeAguaConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QualidadeAguaService {

    @Autowired
    private QualidadeAguaRepository qualidadeAguaRepository;

    public List<QualidadeAguaDTO> listarQualidadeAgua() {
        return qualidadeAguaRepository.findAll()
                .stream()
                .map(QualidadeAguaConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<QualidadeAguaDTO> buscarQualidadeAguaPorId(Long id) {
        return qualidadeAguaRepository.findById(id)
                .map(QualidadeAguaConverter::toDTO);
    }

    public QualidadeAguaDTO salvarQualidadeAgua(QualidadeAguaDTO qualidadeAguaDTO) {
        QualidadeAgua qualidadeAgua = QualidadeAguaConverter.toEntity(qualidadeAguaDTO);
        QualidadeAgua savedQualidadeAgua = qualidadeAguaRepository.save(qualidadeAgua);
        return QualidadeAguaConverter.toDTO(savedQualidadeAgua);
    }

    public void removerQualidadeAgua(Long id) {
        qualidadeAguaRepository.deleteById(id);
    }

    public QualidadeAguaDTO atualizarQualidadeAgua(QualidadeAguaDTO qualidadeAguaDTO, Long id) {
        return qualidadeAguaRepository.findById(id)
                .map(qualidadeExistente -> {
                    qualidadeExistente.setTimestamp(qualidadeAguaDTO.getTimestamp());
                    qualidadeExistente.setTemperatura(qualidadeAguaDTO.getTemperatura());
                    qualidadeExistente.setPh(qualidadeAguaDTO.getPh());
                    qualidadeExistente.setSalinidade(qualidadeAguaDTO.getSalinidade());
                    qualidadeExistente.setOxigenio(qualidadeAguaDTO.getOxigenio());
                    QualidadeAgua updatedQualidadeAgua = qualidadeAguaRepository.save(qualidadeExistente);
                    return QualidadeAguaConverter.toDTO(updatedQualidadeAgua);
                }).orElseThrow(() -> new IllegalStateException("QualidadeAgua com ID " + id + " nao existe"));
    }

    public QualidadeAguaDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        return qualidadeAguaRepository.findById(id)
                .map(qualidadeExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(QualidadeAgua.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, qualidadeExistente, value);
                        }
                    });
                    QualidadeAgua updatedQualidadeAgua = qualidadeExistente;
                    updatedQualidadeAgua = qualidadeAguaRepository.save(qualidadeExistente);
                    return QualidadeAguaConverter.toDTO(updatedQualidadeAgua);
                }).orElseThrow(() -> new IllegalArgumentException("QualidadeAgua com ID " + id + " nao existe."));
    }
}
