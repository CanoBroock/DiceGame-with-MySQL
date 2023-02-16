package com.juegodados.CanoBroockCesar.model.repository;

import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

    public PlayerEntity findById(int userID);

    public boolean findByUserName(String userName);
    boolean existsByUserName(String userName);
}
