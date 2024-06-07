package com.challenge.blueguard.service;

import com.challenge.blueguard.dto.ComunidadeDTO;
import com.challenge.blueguard.entity.Comunidade;
import com.challenge.blueguard.repository.ComunidadeRepository;
import com.challenge.blueguard.util.ComunidadeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComunidadeService {

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    public List<ComunidadeDTO> listarComunidade() {
        return comunidadeRepository.findAll()
                .stream()
                .map(ComunidadeConverter::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComunidadeDTO> buscarComunidadePorId(Long id) {
        return comunidadeRepository.findById(id)
                .map(ComunidadeConverter::toDTO);
    }

    public ComunidadeDTO salvarComunidade(ComunidadeDTO comunidadeDTO) {
        Comunidade comunidade = ComunidadeConverter.toEntity(comunidadeDTO);
        Comunidade savedComunidade = comunidadeRepository.save(comunidade);
        return ComunidadeConverter.toDTO(savedComunidade);
    }

    public void removerComunidade(Long id) {
        comunidadeRepository.deleteById(id);
    }

    public ComunidadeDTO atualizarComunidade(ComunidadeDTO comunidadeDTO, Long id) {
        return comunidadeRepository.findById(id)
                .map(comunidadeExistente -> {
                    comunidadeExistente.setNomeUsuario(comunidadeDTO.getNomeUsuario());
                    comunidadeExistente.setDataHora(comunidadeDTO.getDataHora());
                    comunidadeExistente.setLocal(comunidadeDTO.getLocal());
                    comunidadeExistente.setDescricao(comunidadeDTO.getDescricao());
                    Comunidade updatedComunidade = comunidadeRepository.save(comunidadeExistente);
                    return ComunidadeConverter.toDTO(updatedComunidade);
                }).orElseThrow(() -> new IllegalStateException("Comunidade com ID " + id + " não existe"));
    }

    public ComunidadeDTO atualizarParcialmente(Long id, Map<String, Object> updates) {
        return comunidadeRepository.findById(id)
                .map(comunidadeExistente -> {
                    updates.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Comunidade.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, comunidadeExistente, value);
                        }
                    });
                    Comunidade updatedComunidade = comunidadeRepository.save(comunidadeExistente);
                    return ComunidadeConverter.toDTO(updatedComunidade);
                }).orElseThrow(() -> new IllegalArgumentException("Comunidade com ID " + id + " não existe."));
    }
}
