package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.GameStatus;
import br.com.cristal.moviegame.business.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {


    Optional<Game> findByGameStatusAndPlayer(GameStatus gameStatus, Player player);

    List<Game> findAllByPlayer(Player player);


    @Query("select g from Game g " +
            "where g.player = :player " +
            "and g.gameStatus = 'STARTED' ")
    Optional<Game> currentGame(Player player);

    @Query( "select g from Game g " +
            "where g.gameStatus = 'FINISHED' " +
            "order by g.score desc ")
    List<Game> getToRanking();

}
