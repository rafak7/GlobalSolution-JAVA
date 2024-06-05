package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.QualidadeAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualidadeAguaRepository extends JpaRepository<QualidadeAgua, Long> {
}
