package com.juegodados.CanoBroockCesar.controller;

import com.juegodados.CanoBroockCesar.model.DTO.PlayerDTO;
import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import com.juegodados.CanoBroockCesar.model.services.GameServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class GameController {

    @Autowired
    private GameServiceImplement gameServiceImplement;

    @PostMapping("/add")
    public ResponseEntity<?> addPlayer(@RequestBody PlayerEntity playerEntity) {
        ResponseEntity<?> responseEntity = null;

        PlayerEntity savePlayer = gameServiceImplement.savePlayer(playerEntity);
        if (savePlayer != null) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(savePlayer);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Player already exist");
        }
        return responseEntity;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPlayers() {
        List<PlayerDTO> playerDTOS = gameServiceImplement.getAllPlayers();
        return new ResponseEntity<>(playerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<?> getOnePlayer(@PathVariable(value = "id") Integer playerId) {
        List<DiceEntity> players = gameServiceImplement.getOneDiceRoll(playerId);

        if (players == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(players);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@RequestBody PlayerEntity playerDetails, @PathVariable(value = "id") Integer playerId) {
        Optional<PlayerEntity> player = gameServiceImplement.getOnePlayer(playerId);

        if (!player.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        player.get().setUserName(playerDetails.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(gameServiceImplement.savePlayer(player.get()));
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteDices(@PathVariable(value = "id") Integer playerId) {
        if (!gameServiceImplement.getOnePlayer(playerId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        gameServiceImplement.deleteDices(playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<?> diceRoll(@PathVariable(value = "id") Integer playerId) {
        DiceEntity diceEntity = gameServiceImplement.rollDice(playerId);
        return ResponseEntity.status(HttpStatus.OK).body(diceEntity);
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking() {
        String diceEntity = gameServiceImplement.getRanking();
        return ResponseEntity.status(HttpStatus.OK).body(diceEntity);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLoser() {
        PlayerDTO playerDTO = gameServiceImplement.getLoser();
        return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner() {
        PlayerDTO playerDTO = gameServiceImplement.getWinner();
        return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
    }


}
