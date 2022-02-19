package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends BaseRepository<Player, Long> {

    Optional<Player> findByEmail(String email);
}
