package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends BaseRepository<Movie, Long> {


    @Query("select m.id from Movie m where m.id not in(:ids)")
    List<Long> findAllIdsExceptList(List<Long> ids);
}
