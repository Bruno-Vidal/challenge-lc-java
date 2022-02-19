package br.com.cristal.moviegame.entrypoint.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoundResponse {

    private MovieResponse firstMovie;
    private MovieResponse secundMovie;
}
