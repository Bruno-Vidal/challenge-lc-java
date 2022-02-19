package br.com.cristal.moviegame.entrypoint.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private Integer rank;
    private String player;
    private Integer amountRound;
    private Double score;
}
