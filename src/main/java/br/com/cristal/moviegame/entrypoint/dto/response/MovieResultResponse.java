package br.com.cristal.moviegame.entrypoint.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@NoArgsConstructor
public class MovieResultResponse extends MovieResponse {

    private Double score;
    private Double rating;
    private Long amountVotes;
}
