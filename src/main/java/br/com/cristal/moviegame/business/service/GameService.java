package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.GameStatus;
import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.entity.Round;
import br.com.cristal.moviegame.business.entity.RoundStatus;
import br.com.cristal.moviegame.business.mapper.GameMapper;
import br.com.cristal.moviegame.business.mapper.RoundMapper;
import br.com.cristal.moviegame.business.repository.GameRepository;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRoundRequest;
import br.com.cristal.moviegame.entrypoint.dto.response.GameResponse;
import br.com.cristal.moviegame.entrypoint.dto.response.RankingResponse;
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

    public GameResponse createGame() {

        Player player = customUserDetailsService.loadUser();

        if (existCurrentGame(player)) {

            throw new RuntimeException("Já existe um jogo iniciado");
        }

        Game game = initGame(player);
        game = newRoundToGame(game);

        return gameMapper.toResponse(game);

    }

    private Game newRoundToGame(Game game) {
        Round round = initRound(game);
        game.getRounds().add(round);
        game = gameRepository.saveAndFlush(game);
        return game;
    }

    private Round initRound(Game game) {

        return roundService.creteToGame(game);
    }

    private Game initGame(Player player) {
        return gameRepository.saveAndFlush(
                Game
                        .builder()
                        .player(player)
                        .build()
        );
    }

    private boolean existCurrentGame(Player player) {
        return gameRepository.findByGameStatusAndPlayer(GameStatus.STARTED, player).isPresent();
    }

    public ResultRoundResponse choiceToCurrentRound(ChoiceRoundRequest choiceRoundRequest) {


        Round currentRound = getCurrentRound();
        Boolean rigth = roundService.answerRight(choiceRoundRequest, currentRound);

        confirmRound(currentRound, rigth);

        return roundMapper.toResult(currentRound, rigth);
    }

    public Round getCurrentRound() {
        Player player = customUserDetailsService.loadUser();
        return roundService.currentRound(player);
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
            this.newRoundToGame(game);
        }

        Round currentRound = getCurrentRound();

        return roundMapper.toResponse(currentRound);
    }

    private Game currentGame() {
        Player player = customUserDetailsService.loadUser();
        return gameRepository.currentGame(player)
                .orElseThrow(() -> new RuntimeException("Não existe game ativo"));
    }

    public List<GameResponse> getAll() {
        Player player = customUserDetailsService.loadUser();
        return gameRepository
                .findAllByPlayer(player)
                .stream()
                .map(gameMapper::toResponse)
                .collect(Collectors.toList())
                ;
    }


}
