package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Movie;
import br.com.cristal.moviegame.config.handler.exceptions.MovieNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query("select m.id from Movie m where m.id not in(:ids)")
    List<Long> findAllIdsExceptList(List<Long> ids);

    default Movie forceFindById(Long id) {
        return this.findById(id)
                .orElseThrow(MovieNotFoundException::new);
    }
}
