package com.challenge.blueguard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObservacaoDTO {
    private Long idObservacao;
    private LocalDateTime timestampObservacao;
    private String dadosObservacao;
}
