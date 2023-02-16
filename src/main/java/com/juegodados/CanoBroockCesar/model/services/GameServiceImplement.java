package com.juegodados.CanoBroockCesar.model.services;

import com.juegodados.CanoBroockCesar.model.DTO.PlayerDTO;
import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import com.juegodados.CanoBroockCesar.model.repository.DiceRepository;
import com.juegodados.CanoBroockCesar.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameServiceImplement implements GameService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DiceRepository diceRepository;

    private PlayerDTO convertEntityToDTO(PlayerEntity playerEntity) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUserID(playerEntity.getUserID());
        playerDTO.setUserName(playerEntity.getUserName());
        playerDTO.setDate(playerEntity.getDate());
        return playerDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayers() {

        List<PlayerEntity> players = playerRepository.findAll();
        List<PlayerDTO> playersDTO = new ArrayList<>();

        for (PlayerEntity playerEntity : players) {
            PlayerDTO playerDTO = convertEntityToDTO(playerEntity);
            List<DiceEntity> diceRolls = getOneDiceRoll(playerEntity.getUserID());
            float winn = 0.00F;
            int timesWon;
            for (DiceEntity rollDice : diceRolls) {
                if (rollDice.getWinner_looser().equalsIgnoreCase("Winner")) {
                    winn++;
                }
            }

            timesWon = (int) ((winn * 100) / diceRolls.size());
            playerDTO.setSuccessRate(timesWon);
            playersDTO.add(playerDTO);
        }
        return playersDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerEntity> getOnePlayer(int id) {
        return Optional.ofNullable(playerRepository.findById(id));
    }

    @Override
    @Transactional
    public PlayerEntity savePlayer(PlayerEntity playerEntity) {
        if (playerEntity.getUserName().equals("ANONYMOUS") || !playerRepository.existsByUserName(playerEntity.getUserName())) {
            playerRepository.save(playerEntity);
        } else {
            playerEntity = null;
        }
        return playerEntity;
    }


    @Override
    @Transactional
    public void deleteDices(int id) {
        List<DiceEntity> listOfDiceRoll = diceRepository.findAll();
        for (DiceEntity diceEntity : listOfDiceRoll) {
            if (diceEntity.getPlayerEntity().getUserID() == id) {
                diceRepository.delete(diceEntity);
            }
        }
    }

    @Override
    @Transactional
    public DiceEntity rollDice(int id) {
        DiceEntity diceEntity = null;
        if (playerRepository.existsById(id)) {
            PlayerEntity playerEntity = playerRepository.findById(id);
            diceEntity = new DiceEntity(playerEntity);
        }
        return diceRepository.save(diceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiceEntity> getOneDiceRoll(int id) {
        List<DiceEntity> diceRolls = new ArrayList<>();
        List<DiceEntity> diceRepositoryAll = diceRepository.findAll();

        for (DiceEntity diceEntity : diceRepositoryAll) {
            if (diceEntity.getPlayerEntity().getUserID() == id) {
                DiceEntity newDiceEntity = new DiceEntity();
                newDiceEntity.setRollID(diceEntity.getRollID());
                newDiceEntity.setRoll1(diceEntity.getRoll1());
                newDiceEntity.setRoll2(diceEntity.getRoll2());
                newDiceEntity.setMatchResult(diceEntity.getMatchResult());
                newDiceEntity.setWinner_looser(diceEntity.getWinner_looser());
                diceRolls.add(newDiceEntity);
            }
        }
        return diceRolls;
    }

    @Override
    public String getRanking() {
        int winners = 0;
        float percentage = 0.0F;
        List<DiceEntity> dices = diceRepository.findAll();

        for (DiceEntity diceEntity : dices) {
            if (diceEntity.getWinner_looser().equalsIgnoreCase("Winner")) {
                winners++;
            }
        }
        percentage = (winners * 100) / dices.size();
        return "The percentage of winners is " + percentage + " %";
    }

    @Override
    public PlayerDTO getLoser() {
        PlayerDTO playerLoser = null;
        List<PlayerDTO> playersDTO = getAllPlayers();

        Collections.sort(playersDTO);
        return playersDTO.get(0);
    }

    @Override
    public PlayerDTO getWinner() {
        PlayerDTO playerWinner = null;
        List<PlayerDTO> playersDTO = getAllPlayers();

        Collections.reverse(playersDTO);
        return playersDTO.get(1);
    }

}

