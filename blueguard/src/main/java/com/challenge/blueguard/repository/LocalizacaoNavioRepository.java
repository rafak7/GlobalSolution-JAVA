package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.LocalizacaoNavio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoNavioRepository extends JpaRepository<LocalizacaoNavio, Long> {
}
