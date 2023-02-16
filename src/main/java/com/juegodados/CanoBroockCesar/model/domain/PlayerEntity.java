package com.juegodados.CanoBroockCesar.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class PlayerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @Column(name = "userName")
    private String userName;

    @Column(name = "date")
    private LocalDate date;

    public void setDate(LocalDate date) {
        this.date = LocalDate.now();
    }

    public void setUserName(String userName) {
        if (userName.isEmpty()) {
            this.userName = "ANONYMOUS";
        } else {
            this.userName = userName;
        }
    }
}
