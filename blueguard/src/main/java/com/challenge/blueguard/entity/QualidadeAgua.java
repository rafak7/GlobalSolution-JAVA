package com.challenge.blueguard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_QUALIDADE_AGUA", allocationSize = 1)
@Table(name = "QUALIDADE_AGUA")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class QualidadeAgua {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "invs")
    @Column(name = "ID", nullable = false)
    private Long idQualidade;

    @Column (name = "ID_SENSOR", nullable = false)
    private Long idSensor;

    @Column (name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;

    @Column (name = "TEMPERATURA", nullable = false)
    private Double temperatura;

    @Column (name = "PH", nullable = false)
    private Double ph;

    @Column (name = "SALINIDADE", nullable = false)
    private Double salinidade;

    @Column (name = "NIVEL_OXIGENIO", nullable = false)
    private Double oxigenio;

}
