package com.challenge.blueguard.util;

import com.challenge.blueguard.dto.ComunidadeDTO;
import com.challenge.blueguard.entity.Comunidade;

public class ComunidadeConverter {

    public static ComunidadeDTO toDTO(Comunidade comunidade) {
        return new ComunidadeDTO(
                comunidade.getIdComunidade(),
                comunidade.getNomeUsuario(),
                comunidade.getDataHora(),
                comunidade.getLocal(),
                comunidade.getDescricao()
        );
    }

    public static Comunidade toEntity(ComunidadeDTO comunidadeDTO) {
        return new Comunidade(
                comunidadeDTO.getIdComunidade(),
                comunidadeDTO.getNomeUsuario(),
                comunidadeDTO.getDataHora(),
                comunidadeDTO.getLocal(),
                comunidadeDTO.getDescricao()
        );
    }
}
