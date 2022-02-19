package br.com.cristal.moviegame.business.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseSearchOmdb {
    @JsonProperty("Search")
    private List<MovieOmdbBase> movies;
    @JsonProperty("totalResults")
    private Long totalResults;
    @JsonProperty("Response")
    private Boolean response;
}
