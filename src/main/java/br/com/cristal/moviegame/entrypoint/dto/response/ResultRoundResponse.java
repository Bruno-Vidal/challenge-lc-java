package br.com.cristal.moviegame.entrypoint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultRoundResponse {

    private Boolean correct;
    private MovieResultResponse firstMovie;
    private MovieResultResponse secundMovie;
    private String gameStatus;

}
