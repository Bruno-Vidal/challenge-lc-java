package br.com.cristal.moviegame.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T, ID> {

    default T forceFindById(ID id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }

}
