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
public class ComunidadeDTO {
    private Long idComunidade;
    private String nomeUsuario;
    private LocalDateTime dataHora;
    private String local;
    private String descricao;
}
