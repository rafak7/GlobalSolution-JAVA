package com.challenge.blueguard.repository;

import com.challenge.blueguard.entity.AreaMarinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaMarinhaRepository extends JpaRepository<AreaMarinha, Long> {
}
