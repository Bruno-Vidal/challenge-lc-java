package br.com.cristal.moviegame.business.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Optional;


@Data
@NoArgsConstructor
public class MovieOmdbBase {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private Year year;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("imdbRating")
    private Double rating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    public Long getAmountVotes() {
        return Optional.ofNullable(imdbVotes)
                .map(votes -> Long.parseLong(votes.replaceAll("[^\\d.]", "")))
                .orElse(null)
                ;
    }


}
