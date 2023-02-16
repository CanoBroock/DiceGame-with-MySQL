package com.juegodados.CanoBroockCesar.model.services;

import com.juegodados.CanoBroockCesar.model.DTO.PlayerDTO;
import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GameService {
    List<PlayerDTO> getAllPlayers();

    Optional<PlayerEntity> getOnePlayer(int id);

    PlayerEntity savePlayer(PlayerEntity playerEntity);

    void deleteDices(int id);

    DiceEntity rollDice(int id);

    List<DiceEntity> getOneDiceRoll(int id);

    String getRanking();

    PlayerDTO getLoser();

    PlayerDTO getWinner();

}
