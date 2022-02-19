package br.com.cristal.moviegame.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Builder.Default
    private LocalDateTime startGameDateTime = LocalDateTime.now();
    private LocalDateTime finishGameDateTime;

    @Builder.Default
    private Integer lifes = 3;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.STARTED;

    @Builder.Default
    private Double score = 0.0;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game", cascade = CascadeType.PERSIST)
    private List<Round> rounds = new ArrayList<>();

    public void takeLife() {
        this.lifes -= 1;
    }

    public void finish() {
        this.setGameStatus(GameStatus.FINISHED);
        this.setFinishGameDateTime(LocalDateTime.now());
        long amountCorrect = rounds.stream()
                .filter(r -> r.getRoundStatus().equals(RoundStatus.CORRERCT))
                .count();
        // TODO conversar com o time LC pois o calculo se anula
        Double calcSocre = rounds.size() * (Double.valueOf(amountCorrect) / rounds.size());
        this.setScore(calcSocre);
    }
}
