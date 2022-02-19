package br.com.cristal.moviegame.entrypoint.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@NoArgsConstructor
public class MovieResponse {

    private String title;
    private String linkPoster;
    private Year year;
}
