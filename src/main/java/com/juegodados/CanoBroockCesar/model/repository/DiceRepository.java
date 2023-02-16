package com.juegodados.CanoBroockCesar.model.repository;

import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiceRepository extends JpaRepository<DiceEntity, Integer> {
}
