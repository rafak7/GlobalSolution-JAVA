package com.challenge.blueguard.entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "invs", sequenceName = "SQ_NAVIO", allocationSize = 1)
@Table (name = "navio")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Navio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY, generator = "invs")
    @Column(name = "ID")
    private Long idNavio;

    @Column(name = "NOME", length = 255, nullable = false)
    private String nomeNavio;

    @Column (name = "IMO", length = 255, nullable = false)
    private String imoNavio;

    @Column (name = "MMSI", length = 255, nullable = false)
    private String mmsiNavio;

    @Column (name = "TIPO", length = 255, nullable = false )
    private String tipoNavio;

}
