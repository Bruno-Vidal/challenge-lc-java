package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.client.OmdbClient;
import br.com.cristal.moviegame.business.client.dto.MovieOmdbBase;
import br.com.cristal.moviegame.business.client.dto.ResponseSearchOmdb;
import br.com.cristal.moviegame.business.entity.Movie;
import br.com.cristal.moviegame.business.mapper.MovieMapper;
import br.com.cristal.moviegame.business.repository.MovieRepository;
import br.com.cristal.moviegame.config.movies.OmdbApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbClient omdbClient;
    private final OmdbApiProperties omdbApiProperties;
    private final MovieMapper movieMapper;

    public Movie getRandom() {
        return getRandomExceptInList(new ArrayList<>());
    }

    public Movie getRandomExceptInList(List<Long> movieIds) {


        List<Long> ids  = movieRepository.findAllIdsExceptList(movieIds);
        int indexRandom = new Random().nextInt(ids.size());
        Long idRandom = ids.get(indexRandom);

        return movieRepository.forceFindById(idRandom);
    }


    public void complete(Movie movie) {

        if ( ! movie.isComplete()) {
            MovieOmdbBase movieOmdb = omdbClient.findById(
                    omdbApiProperties.getApiKey(),
                    movie.getExternalId()
            );

            movieMapper.completeEntity(movie , movieOmdb);
            movieRepository.save(movie);

        }

    }

    public Movie better(Movie firstMovie, Movie secundMovie) {
        return firstMovie.getScore() > secundMovie.getScore() ? firstMovie : secundMovie;
    }
}
