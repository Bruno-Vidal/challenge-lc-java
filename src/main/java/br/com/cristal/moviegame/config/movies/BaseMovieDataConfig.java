package br.com.cristal.moviegame.config.movies;

import br.com.cristal.moviegame.business.client.OmdbClient;
import br.com.cristal.moviegame.business.client.dto.ResponseSearchOmdb;
import br.com.cristal.moviegame.business.mapper.MovieMapper;
import br.com.cristal.moviegame.business.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BaseMovieDataConfig {
    private final OmdbClient omdbClient;
    private final OmdbApiProperties omdbApiProperties;
    private final MovieRepository moviesRepository;
    private final MovieMapper movieMapper;


    @Bean
    public void initBaseMovies() {

        omdbApiProperties.getSearch()
                .forEach(this::stepToSeach);


    }

    private void stepToSeach(String search) {

        for (int page = 1; page <= omdbApiProperties.getLimitPage(); page++) {
            ResponseSearchOmdb responseSearchOmdb = getOmdbClientList(search, page);

            mapAndSave(responseSearchOmdb);

            if (!hasNext(page, responseSearchOmdb)) {
                break;
            }
        }

    }

    private void mapAndSave(ResponseSearchOmdb responseSearchOmdb) {

        responseSearchOmdb
                .getMovies()
                .stream()
                .map(movieMapper::toEntity)
                .forEach(moviesRepository::save);

    }

    private ResponseSearchOmdb getOmdbClientList(String search, int page) {
        return omdbClient.findList(
                omdbApiProperties.getApiKey(),
                search,
                page,
                omdbApiProperties.getType()
        );
    }

    private boolean hasNext(int page, ResponseSearchOmdb responseSearchOmdb) {

        return responseSearchOmdb.getMovies().stream().count() * page < responseSearchOmdb.getTotalResults();
    }
}
