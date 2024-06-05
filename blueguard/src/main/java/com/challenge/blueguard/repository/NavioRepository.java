package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.Navio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavioRepository extends JpaRepository<Navio, Long>{
}
