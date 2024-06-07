package com.challenge.blueguard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "observacao_seq", sequenceName = "SQ_OBSERVACOES", allocationSize = 1)
@Table(name = "TB_OBSERVACAO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Observacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observacao_seq")
    @Column(name = "ID")
    private Long idObservacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "TIMESTAMP", length = 255, nullable = false)
    private LocalDateTime timestampObservacao;

    @Lob
    @Column(name = "DADOS")
    private String dadosObservacao;
}
