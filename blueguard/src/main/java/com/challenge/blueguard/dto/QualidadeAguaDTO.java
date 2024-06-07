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
public class QualidadeAguaDTO {
    private Long idQualidade;
    private LocalDateTime timestamp;
    private Double temperatura;
    private Double ph;
    private Double salinidade;
    private Double oxigenio;
}
