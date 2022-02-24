package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.GameStatus;
import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.entity.Round;
import br.com.cristal.moviegame.business.entity.RoundStatus;
import br.com.cristal.moviegame.business.mapper.GameMapper;
import br.com.cristal.moviegame.business.mapper.RoundMapper;
import br.com.cristal.moviegame.business.repository.GameRepository;
import br.com.cristal.moviegame.config.handler.exceptions.GameInitException;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRoundRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.ResultRoundResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RoundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final CustomUserDetailsService customUserDetailsService;
    private final GameRepository gameRepository;
    private final RoundService roundService;
    private final GameMapper gameMapper;
    private final RoundMapper roundMapper;

    public List<GameResponse> getAll() {
        Player player = customUserDetailsService.loadUser();
        return gameRepository
                .findAllByPlayer(player)
                .stream()
                .map(gameMapper::toResponse)
                .collect(Collectors.toList())
                ;
    }

    public GameResponse createGame() {

        Player player = customUserDetailsService.loadUser();

        Boolean existCurrentGame = gameRepository
                .findByGameStatusAndPlayer(GameStatus.STARTED, player)
                .isPresent();

        if (existCurrentGame) {
            throw new GameInitException("Já existe um jogo iniciado");
        }

        Game game = Game
                .builder()
                .player(player)
                .build();

        game = gameRepository.save(game);

        return gameMapper.toResponse(game);

    }

    public ResultRoundResponse choiceToCurrentRound(ChoiceRoundRequest choiceRoundRequest) {


        Round currentRound = currentRound();
        Boolean rigth = roundService.answerRight(choiceRoundRequest, currentRound);

        confirmRound(currentRound, rigth);

        return roundMapper.toResult(currentRound, rigth);
    }

    public GameResponse finishGame() {

        Game game = currentGame();

        game
                .getRounds()
                .stream().filter(r -> r.getRoundStatus().equals(RoundStatus.WAITING_REPONSE))
                .forEach(r -> r.setRoundStatus(RoundStatus.WRONG));

        game.finish();

        gameRepository.saveAndFlush(game);

        return gameMapper.toResponse(game);
    }

    public RoundResponse currentRoundResponse() {

        Game game = currentGame();

        if (!roundService.existsCurrentRound(game)) {
            Round round = roundService.creteToGame(game);
            game.getRounds().add(round);
            gameRepository.saveAndFlush(game);
        }

        Round currentRound = currentRound();

        return roundMapper.toResponse(currentRound);
    }



    private void confirmRound(Round currentRound, Boolean rigth) {
        RoundStatus status = rigth ? RoundStatus.CORRERCT : RoundStatus.WRONG;

        Game game = currentRound.getGame();

        currentRound.setRoundStatus(status);

        roundService.save(currentRound);

        if (!rigth) {
            game.takeLife();
        }

        if (game.getLifes() <= 0) {
            this.finishGame();
        }

        gameRepository.save(game);

    }

    private Game currentGame() {
        Player player = customUserDetailsService.loadUser();
        return gameRepository.currentGame(player)
                .orElseThrow(() -> new GameInitException("Não existe jogo ativo"));
    }

    private Round currentRound() {
        Player player = customUserDetailsService.loadUser();
        return roundService.currentRound(player);
    }


}
