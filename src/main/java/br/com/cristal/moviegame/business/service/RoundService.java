package br.com.cristal.moviegame.business.service;

import br.com.cristal.moviegame.business.entity.Game;
import br.com.cristal.moviegame.business.entity.Movie;
import br.com.cristal.moviegame.business.entity.Player;
import br.com.cristal.moviegame.business.entity.Round;
import br.com.cristal.moviegame.business.repository.RoundRepository;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRound;
import br.com.cristal.moviegame.entrypoint.dto.request.ChoiceRoundRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final MovieService movieService;
    private final RoundRepository roundRepository;

    public Round creteToGame(Game game) {

        Round round = initRound(game);

        Movie first = movieService.getRandom();

        List<Long> idsPair = findPairIds(first,game);

        Movie secund = movieService.getRandomExceptInList(idsPair);

        round.setFirstMovie(first);
        round.setSecundMovie(secund);

        return roundRepository.saveAndFlush(round);

    }

    private List<Long> findPairIds(Movie movie, Game game) {

        List<Long> idsPair = game
                .getRounds()
                .stream()
                .filter(r -> r.getFirstMovie().equals(movie))
                .map(Round::getSecundMovie)
                .map(Movie::getId)
                .collect(Collectors.toList());

        idsPair.addAll( game
                .getRounds()
                .stream()
                .filter(r -> r.getSecundMovie().equals(movie))
                .map(Round::getFirstMovie)
                .map(Movie::getId)
                .collect(Collectors.toList())
        );

        idsPair.add(movie.getId());

        return idsPair;
    }

    private Round initRound(Game game) {

        Integer order = 1 + game.getRounds().size();

        return Round
                .builder()
                .game(game)
                .order(order)
                .build();
    }

    public Round currentRound(Player player) {
        return roundRepository.currentRound(player)
                .orElseThrow(() -> new RuntimeException("Não existe roud ativo"));
    }

    public Boolean answerRight(ChoiceRoundRequest choiceRoundRequest, Round round) {

        completeMovies(round);

        Map<ChoiceRound,Movie> mapMovie = new HashMap<>();

        mapMovie.put(ChoiceRound.FIRST,round.getFirstMovie());
        mapMovie.put(ChoiceRound.SECUND,round.getSecundMovie());

        Movie better = movieService.better(round.getFirstMovie(), round.getSecundMovie());


        return mapMovie.get(choiceRoundRequest.getChoice()).equals(better);
    }

    private void completeMovies(Round round) {
        movieService.complete(round.getFirstMovie());
        movieService.complete(round.getSecundMovie());
    }

    public void save(Round round) {
        roundRepository.save(round);
    }

    public Boolean existsCurrentRound(Game game) {
        return roundRepository.currentRound(game.getPlayer()).isPresent();
    }
}
