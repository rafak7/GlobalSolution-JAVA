package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.Observacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservacaoRepository extends JpaRepository<Observacao, Long> {
}
