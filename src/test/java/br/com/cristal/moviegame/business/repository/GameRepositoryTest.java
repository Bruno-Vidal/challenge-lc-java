package br.com.cristal.moviegame.business.repository;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.factory.GameFactory;
import br.com.cristal.moviegame.factory.PlayerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@DisplayName("Testes no repositorio de jogos")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("busca de jogo atraves do jogador e status do jogo")
    void findByGameStatusAndPlayer_Sucess() {

        Player player = PlayerFactory.any();
        player.setEmail("findByGameStatusAndPlayer@test.com");
        playerRepository.save(player);


        Game game = GameFactory.any();
        game.setPlayer(player);
        gameRepository.save(game);

        Game findGame = gameRepository.findByGameStatusAndPlayer(game.getGameStatus(), player)
                .orElse(null);

        Assertions.assertThat(findGame).isNotNull();
        Assertions.assertThat(findGame.getPlayer().getId()).isEqualTo(player.getId());
        Assertions.assertThat(findGame.getGameStatus()).isEqualTo(game.getGameStatus());
    }
    @Test
    @DisplayName("busca de jogo atraves de jogador: resultado vazio")
    void findByGameStatusAndPlayer_Empty() {

        Player player = PlayerFactory.any();
        player.setEmail("findByGameStatusAndPlayerEmpty@test.com");
        playerRepository.save(player);

        Player player2 = PlayerFactory.any();
        player.setEmail("findByGameStatusAndPlayerEmpty2@test.com");
        playerRepository.save(player2);

        Game game = GameFactory.any();
        game.setPlayer(player2);
        gameRepository.saveAndFlush(game);

        Game findGame = gameRepository.findByGameStatusAndPlayer(game.getGameStatus(), player)
                .orElse(null);

        Assertions.assertThat(findGame).isNull();
    }

    @Test
    @DisplayName("busca de todos os jogos de um jogador")
    void findAllByPlayer_Sucess() {

        Player player = PlayerFactory.any();
        player.setEmail("findAllByPlayer@test.com");
        playerRepository.save(player);

        Game game = GameFactory.any();
        Game game2 = GameFactory.any();
        game.setPlayer(player);
        game2.setPlayer(player);
        gameRepository.save(game);
        gameRepository.save(game2);

        List<Game> games = gameRepository.findAllByPlayer(player);


        Assertions.assertThat(games).isNotEmpty();
        Assertions.assertThat(games.get(0)).isNotNull();
        Assertions.assertThat(games.get(1)).isNotNull();

        Assertions.assertThat(games.get(0).getPlayer()).isEqualTo(player);
        Assertions.assertThat(games.get(1).getPlayer()).isEqualTo(player);

        Assertions.assertThat(games.get(0)).isNotEqualTo(games.get(1));
    }

    @Test
    @DisplayName("busca do jogo atual de um jogador")
    void findCurrentGameByPlayer_Sucess() {

        Player player = PlayerFactory.any();
        player.setEmail("findCurrentGameByPlayer@test.com");
        playerRepository.save(player);

        Game game = GameFactory.any();
        Game game2 = GameFactory.any();

        game.setPlayer(player);
        game2.setPlayer(player);

        gameRepository.save(game);
        gameRepository.save(game2);

        game.finish();

        gameRepository.save(game);

        Game currentGame = gameRepository.currentGame(player)
                .orElse(null);


        Assertions.assertThat(currentGame).isNotNull();
        Assertions.assertThat(currentGame).isNotEqualTo(game);
        Assertions.assertThat(currentGame).isEqualTo(game2);

    }

    @Test
    @DisplayName("busca do jogo atual de um jogador")
    void getToRanking_Sucess() {

        Player player = PlayerFactory.any();
        player.setEmail("goToRanking@test.com");
        playerRepository.save(player);

        Game game1 = GameFactory.withSocre(5.8);
        Game game2 = GameFactory.withSocre(10.0);
        Game game3 = GameFactory.withSocre(2.0);
        Game game4 = GameFactory.any();

        game1.setPlayer(player);
        game2.setPlayer(player);
        game3.setPlayer(player);
        game4.setPlayer(player);

        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);



        List<Game> games = gameRepository.getToRanking();


        Assertions.assertThat(games).isNotEmpty();
        Assertions.assertThat(games.size()).isEqualTo(3);
        Assertions.assertThat(games.get(0)).isEqualTo(game2);
        Assertions.assertThat(games.get(1)).isEqualTo(game1);
        Assertions.assertThat(games.get(2)).isEqualTo(game3);
        Assertions.assertThat(games.contains(game4)).isFalse();

    }

}