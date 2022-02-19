package br.com.cristal.moviegame.entrypoint.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GameResponse {

    private Long id;

    private LocalDateTime startGameDateTime;

    private Integer lifes;

    private Double score;

    private String status;

    private Integer amountRound;

    private Long amountCorrect;

    private Long amountWrong;

    private RoundResponse currentRound;
}
