package br.com.cristal.moviegame.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Year;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String externalId;

    private String linkPoster;

    private Year year;

    private Double rating;

    private Long amountVotes;

    public Double getScore(){
        return rating * amountVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) && externalId.equals(movie.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, externalId);
    }

    public Boolean isComplete() {
        return rating != null && amountVotes != null;
    }
}
