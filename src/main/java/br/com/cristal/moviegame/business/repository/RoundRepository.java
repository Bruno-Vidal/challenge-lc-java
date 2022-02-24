package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query( "select r from Round r " +
            "where r.game.player = :player " +
            "and r.roundStatus = 'WAITING_REPONSE' " +
            "and r.game.gameStatus = 'STARTED' ")
    Optional<Round> currentRound(Player player);
}
