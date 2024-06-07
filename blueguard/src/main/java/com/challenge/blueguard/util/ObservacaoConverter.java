package com.challenge.blueguard.util;

import com.challenge.blueguard.dto.ObservacaoDTO;
import com.challenge.blueguard.entity.Observacao;

public class ObservacaoConverter {

    public static ObservacaoDTO toDTO(Observacao observacao) {
        return new ObservacaoDTO(
                observacao.getIdObservacao(),
                observacao.getTimestampObservacao(),
                observacao.getDadosObservacao()
        );
    }

    public static Observacao toEntity(ObservacaoDTO observacaoDTO) {
        return new Observacao(
                observacaoDTO.getIdObservacao(),
                observacaoDTO.getTimestampObservacao(),
                observacaoDTO.getDadosObservacao()
        );
    }
}
