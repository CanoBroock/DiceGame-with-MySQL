package com.juegodados.CanoBroockCesar.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "dices")
public class DiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollID;

    @Column(name = "dice1")
    private int roll1;

    @Column(name = "dice2")
    private int roll2;

    @Column(name = "matchResult")
    private int matchResult;

    @Column(name = "winner_looser")
    private String winner_looser;

    public DiceEntity(PlayerEntity playerEntity) {
        this.roll1 = aleatoryNumber();
        this.roll2 = aleatoryNumber();
        this.matchResult = this.roll1 + this.roll2;
        this.winner_looser = winnerORlooser(this.matchResult);
        this.playerEntity = playerEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private PlayerEntity playerEntity;

    public int aleatoryNumber() {
        return (int) (Math.random() * 6 + 1);
    }

    public String winnerORlooser(int matchResult) {
        String status;

        if (matchResult == 7) {
            status = "Winner";
        } else {
            status = "Looser";
        }
        return status;
    }
}
