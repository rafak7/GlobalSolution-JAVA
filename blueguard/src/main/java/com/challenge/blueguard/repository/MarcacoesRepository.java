package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.Marcacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcacoesRepository extends JpaRepository<Marcacoes, Long> {
}
