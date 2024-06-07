package com.challenge.blueguard.util;

import com.challenge.blueguard.dto.MarcacoesDTO;
import com.challenge.blueguard.entity.Marcacoes;

public class MarcacoesConverter {

    public static MarcacoesDTO toDTO(Marcacoes marcacoes) {
        return new MarcacoesDTO(
                marcacoes.getIdMarcacoes(),
                marcacoes.getLocal(),
                marcacoes.getNivelSujeira(),
                marcacoes.getPh(),
                marcacoes.getTemperatura(),
                marcacoes.getPotavel(),
                marcacoes.getObservacoes()
        );
    }

    public static Marcacoes toEntity(MarcacoesDTO marcacoesDTO) {
        return new Marcacoes(
                marcacoesDTO.getIdMarcacoes(),
                marcacoesDTO.getLocal(),
                marcacoesDTO.getNivelSujeira(),
                marcacoesDTO.getPh(),
                marcacoesDTO.getTemperatura(),
                marcacoesDTO.getPotavel(),
                marcacoesDTO.getObservacoes()
        );
    }
}
