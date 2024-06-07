package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.Comunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunidadeRepository  extends JpaRepository<Comunidade, Long> {
}
